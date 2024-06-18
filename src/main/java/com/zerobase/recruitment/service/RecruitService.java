package com.zerobase.recruitment.service;

import com.zerobase.recruitment.dto.ApplicantInfo;
import com.zerobase.recruitment.dto.ApplicationDto;
import com.zerobase.recruitment.dto.RecruitmentDto;
import com.zerobase.recruitment.entity.*;
import com.zerobase.recruitment.enums.ApplicationStatus;
import com.zerobase.recruitment.enums.EducationLevel;
import com.zerobase.recruitment.enums.RecruitmentStatus;
import com.zerobase.recruitment.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.constant.ConstantDesc;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecruitService {
    private final MemberRepository memberRepository;
    private final CompanyMemberRepository companyMemberRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final ResumeRepository resumeRepository;
    private final ApplicationRepository applicationRepository;

    @Transactional
    public void postingRecruitment(RecruitmentDto.Request request) {
        CompanyMemberEntity companyMemberEntity =
                companyMemberRepository.findByLoginId(request.companyMemberId()).orElseThrow(
                () -> new RuntimeException("해당 기업 회원이 없습니다.")
        );
        RecruitmentEntity recruitmentEntity = request.toEntity();
        recruitmentEntity.setCompanyMember(companyMemberEntity);
        recruitmentEntity.opening();
        log.info("RecruitmentEntity: {}", recruitmentEntity.toString());
        recruitmentRepository.save(recruitmentEntity);

    }

    @Transactional(readOnly = true)
    public List<RecruitmentDto.Response> getRecruitmentList() {
        List<RecruitmentEntity> recruitmentEntityList =
                recruitmentRepository.findAllByStatus(RecruitmentStatus.OPEN);

        return recruitmentEntityList.stream()
                        .map(RecruitmentEntity::toDto)
                        .toList();

    }

    @Transactional(readOnly = true)
    public RecruitmentDto.Response getRecruitment(Long recruitmentId) {
        return recruitmentRepository.findById(recruitmentId).orElseThrow(
                () -> new RuntimeException("해당 채용 공고가 존재하지 않습니다.")
        ).toDto();
    }

    @Transactional
    public RecruitmentDto.Response updateRecruitments(Long recruitmentId, RecruitmentDto.Request request) {
        RecruitmentEntity recruitmentEntity = recruitmentRepository.findById(recruitmentId).orElseThrow(
                () -> new RuntimeException("해당 채용 공고가 존재하지 않습니다.")
        );

        if(!Objects.equals(recruitmentEntity.getCompanyMember().getLoginId(),
                request.companyMemberId())) {
            throw new RuntimeException("잘못된 기업 회원 정보입니다.");
        }

        return recruitmentEntity.update(request).toDto();
    }

    @Transactional
    public void deleteRecruitment(Long recruitmentId,
                                  RecruitmentDto.Request request) {
        RecruitmentEntity recruitmentEntity =
                recruitmentRepository.findById(recruitmentId).orElseThrow(
                () -> new RuntimeException("해당 채용 공고가 존재하지 않습니다.")
        );

        if(!Objects.equals(recruitmentEntity.getCompanyMember().getLoginId(),
                request.companyMemberId())) {
            throw new RuntimeException("잘못된 기업 회원 정보입니다.");
        }

        recruitmentRepository.deleteById(recruitmentId);
    }

    @Transactional
    public void applyRecruitment(Long recruitmentId, ApplicationDto.Request request) {
        //이력서 찾기(지원 가능한 상태인 이력서)
        ResumeEntity resumeEntity = resumeRepository.findByIdAndMemberId(request.resumeId(), request.memberId()).orElseThrow(
                () -> new RuntimeException("해당 이력서가 존재하지 않습니다.")
        );
        //공고 찾기(지원하려는 공고)
        RecruitmentEntity recruitmentEntity = recruitmentRepository.findByIdAndStatus(recruitmentId, RecruitmentStatus.OPEN).orElseThrow(
                () -> new RuntimeException("지원 가능한 공고가 없습니다.")
        );

        ApplicationEntity applicationEntity = ApplicationEntity.builder()
                .resumeEntity(resumeEntity)
                .recruitmentEntity(recruitmentEntity)
                .memberId(request.memberId())
                .status(ApplicationStatus.APPLY_FINISHED)
                .build();

        applicationRepository.save(applicationEntity);
    }

    @Transactional(readOnly = true)
    public List<ApplicationDto.Response> getApplicationList(
            Long recruitmentId, Long companyMemberId
    ) {
        companyMemberRepository.findById(companyMemberId).orElseThrow(
                () -> new RuntimeException("조회 권한이 없습니다.")
        );

        List<ApplicationEntity> applicationEntityList =
                applicationRepository.findAllByRecruitmentId(recruitmentId);

        return applicationEntityList.stream().map(
                e -> ApplicationDto.Response.builder()
                        .id(e.getId())
                        .status(e.getStatus())
                        .appliedDate(e.getAppliedDate())
                        .recruitmentId(e.getRecruitment().getId())
                        .resumeTitle(e.getResume().getTitle())
                        .educationList(e.getResume().toDto().getEducationList())
                        .memberName(e.getResume().getMember().getName())
                        .build()).toList();
    }

    @Transactional
    public void finishRecruitment(ConstantDesc recruitmentId, Long companyMemberId) {
        //공고 존재여부 확인
        RecruitmentEntity recruitmentEntity = recruitmentRepository.findByIdAndStatusAndCompanyMemberId(
                recruitmentId, RecruitmentStatus.OPEN, companyMemberId).orElseThrow(
                () -> new RuntimeException("공고 정보가 없습니다.")
        );

        //공고 상태 변경(open -> close)
        recruitmentEntity.closing();

        //지원자 정보 가져오기
        List<ApplicationEntity> applicationEntityList =
                recruitmentEntity.getApplicationEntity();
        //순위 매기기(지원자별 각 가장 높은 학위를 기준으로)
        List<ApplicantInfo> applicantInfoList = applicationEntityList.stream()
                .map(ApplicationEntity::getResume)
                .map(r -> {
                    Education education =
                            r.getEducationList().stream()
                                    .max(Comparator.comparing(
                                            Education::getDegree)).orElse(Education.builder().build());
                    return new ApplicantInfo(r.getId(), EducationLevel.findByCode(education.getCode()).getScore(education.getDegree()));
                }).sorted(Comparator.comparing(ApplicantInfo::getScore).reversed()).toList();
        //성능 향상으 위해 map으로 데이터 정리
        Map<Long, Integer> applicantInfoMap =
                IntStream.range(0, applicantInfoList.size()).boxed()
                        .collect(Collectors.toMap(i -> applicantInfoList.get(i).getResumeId(), i -> i));
        //모집인원 안에 들면 합격
        applicationEntityList.forEach(a ->
        {
            if (applicantInfoMap.get(a.getResume().getId()) < recruitmentEntity.getRecruiterCount() ) {
                a.pass();
            } else {
                a.fail();
            }
        });
    }
}
