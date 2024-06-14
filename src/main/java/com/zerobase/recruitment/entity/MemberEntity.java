package com.zerobase.recruitment.entity;

import com.zerobase.recruitment.enums.RecruitmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "member")
@Getter
@Setter
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String loginId;
    private String password;
    private String phoneNumber;
    private String email;
    private LocalDateTime joinDate;
    private RecruitmentStatus status;
}
