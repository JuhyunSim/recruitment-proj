package com.zerobase.recruitment.entity;

import com.zerobase.recruitment.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "application")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long id;
    private Long memberId;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;
    @CreationTimestamp
    private LocalDateTime appliedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private RecruitmentEntity recruitment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private ResumeEntity resume;

    @Builder
    public ApplicationEntity(
            Long memberId,
            ApplicationStatus status,
            RecruitmentEntity recruitmentEntity,
            ResumeEntity resumeEntity
    ) {
        this.memberId = memberId;
        this.status = status;
        this.recruitment = recruitmentEntity;
        this.resume = resumeEntity;
    }

    public void pass() {
        this.status = ApplicationStatus.PASS;
    }

    public void fail() {
        this.status = ApplicationStatus.FAIL;
    }
}
