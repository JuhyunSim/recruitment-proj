package com.zerobase.recruitment.repository;

import com.zerobase.recruitment.entity.RecruitmentEntity;
import com.zerobase.recruitment.enums.RecruitmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.constant.ConstantDesc;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecruitmentRepository extends JpaRepository<RecruitmentEntity, Long> {
    List<RecruitmentEntity> findAllByStatus(RecruitmentStatus status);

    Optional<RecruitmentEntity> findByIdAndStatus(Long recruitmentId, RecruitmentStatus recruitmentStatus);

    Optional<RecruitmentEntity> findByIdAndStatusAndCompanyMemberId(ConstantDesc recruitmentId, RecruitmentStatus recruitmentStatus, Long companyMemberId);
}
