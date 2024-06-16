package com.zerobase.recruitment.repository;

import com.zerobase.recruitment.entity.RecruitmentEntity;
import com.zerobase.recruitment.enums.RecruitmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruitmentRepository extends JpaRepository<RecruitmentEntity, Long> {
    List<RecruitmentEntity> findAllByStatus(RecruitmentStatus status);
}
