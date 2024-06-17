package com.zerobase.recruitment.service;

import com.zerobase.recruitment.dto.ResumeDto;
import com.zerobase.recruitment.entity.MemberEntity;
import com.zerobase.recruitment.entity.ResumeEntity;
import com.zerobase.recruitment.enums.ResumeStatus;
import com.zerobase.recruitment.repository.MemberRepository;
import com.zerobase.recruitment.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void postResume(ResumeDto.Request request) {
        MemberEntity memberEntity =
                memberRepository.findByLoginId(request.memberLoginId()).orElseThrow(
                () -> new RuntimeException("존재하지 않는 회원입니다.")
        );

        ResumeEntity resumeEntity = request.toEntity();
        resumeEntity.setMember(memberEntity);
        resumeEntity.open();
        resumeRepository.save(resumeEntity);
    }

    @Transactional(readOnly = true)
    public List<ResumeDto.Response> getResumeList() {
        List<ResumeEntity> resumeEntityList =
                resumeRepository.findAllByStatus(ResumeStatus.OPEN);

        return resumeEntityList.stream().map(ResumeEntity::toDto).toList();
    }

    @Transactional(readOnly = true)
    public ResumeDto.Response getResume(Long resumeId) {
        ResumeEntity resumeEntity =
                resumeRepository.findById(resumeId).orElseThrow(
                () -> new RuntimeException("해당 이력서가 존재하지 않습니다.")
        );

        return resumeEntity.toDto();
    }

    @Transactional
    public ResumeDto.Response updateResume(Long resumeId, ResumeDto.Request request) {
        ResumeEntity resumeEntity = resumeRepository.findById(resumeId).orElseThrow(
                () -> new RuntimeException("해당 이력서가 존재하지 않습니다.")
        );

        if (!Objects.equals(resumeEntity.getMember().getLoginId(),
                request.memberLoginId())) {
            throw new RuntimeException("잘못된 회원 정보입니다.");
        }

        return resumeEntity.update(request).toDto();
    }

    @Transactional
    public void deleteResume(Long resumeId, ResumeDto.Request request) {
        ResumeEntity resumeEntity = resumeRepository.findById(resumeId).orElseThrow(
                () -> new RuntimeException("해당 이력서가 존재하지 않습니다.")
        );
        if (!Objects.equals(resumeEntity.getMember().getLoginId(), request.memberLoginId())) {
            throw new RuntimeException("잘못된 회원 정보입니다.");
        }

        resumeRepository.deleteById(resumeId);
    }
}
