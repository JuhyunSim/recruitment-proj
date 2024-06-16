package com.zerobase.recruitment.entity;

import com.zerobase.recruitment.enums.MemberStatus;
import com.zerobase.recruitment.enums.RecruitmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "member")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    MemberEntity(
            String name,
            String loginId,
            String password,
            String phoneNumber,
            String email,
            LocalDateTime joinDate,
            MemberStatus status
    ) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.joinDate = joinDate;
        this.status = status;
    }
}
