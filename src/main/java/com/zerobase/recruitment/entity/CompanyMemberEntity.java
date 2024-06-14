package com.zerobase.recruitment.entity;

import com.zerobase.recruitment.enums.RecruitmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "company_member")
@Getter
@Setter //추후 값지정, 변동이 필요 없는/없어야 하는 값에 따로 붙여주
public class CompanyMemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_member_id")
    private Long id;

    private String name;
    private String loginId;
    private String password;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private LocalDateTime joinDate;
    private RecruitmentStatus status;
}
