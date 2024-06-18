package com.zerobase.recruitment.repository;

import com.zerobase.recruitment.entity.ResumeEntity;
import com.zerobase.recruitment.enums.ResumeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<ResumeEntity, Long> {
    List<ResumeEntity> findAllByStatus(ResumeStatus status);

    Optional<ResumeEntity> findByIdAndMemberId(Long resumeId, Long memberId);
}
