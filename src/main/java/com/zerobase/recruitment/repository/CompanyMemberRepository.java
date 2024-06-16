package com.zerobase.recruitment.repository;

import com.zerobase.recruitment.entity.CompanyMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyMemberRepository extends JpaRepository<CompanyMemberEntity, Long> {
    Optional<CompanyMemberEntity> findByLoginId(String loginId);
}
