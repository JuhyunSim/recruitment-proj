package com.zerobase.recruitment.service;

import com.zerobase.recruitment.dto.RecruitmentDto;
import com.zerobase.recruitment.entity.CompanyMemberEntity;
import com.zerobase.recruitment.entity.RecruitmentEntity;
import com.zerobase.recruitment.enums.RecruitmentStatus;
import com.zerobase.recruitment.repository.CompanyMemberRepository;
import com.zerobase.recruitment.repository.MemberRepository;
import com.zerobase.recruitment.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecruitService {
    private final MemberRepository memberRepository;
    private final CompanyMemberRepository companyMemberRepository;
    private final RecruitmentRepository recruitmentRepository;

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
}
