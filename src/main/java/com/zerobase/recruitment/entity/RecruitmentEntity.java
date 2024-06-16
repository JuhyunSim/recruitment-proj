package com.zerobase.recruitment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zerobase.recruitment.dto.RecruitmentDto;
import com.zerobase.recruitment.enums.RecruitmentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity(name = "recruitment")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id")
    private Long id;
    private String title;
    private String description;
    private Integer recruiterCount;
    private LocalDateTime closingDate;
    @Enumerated(EnumType.STRING)
    private RecruitmentStatus status;
    @UpdateTimestamp
    private LocalDateTime modifyDate;
    @CreationTimestamp
    private LocalDateTime postingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_member_id")
    private CompanyMemberEntity companyMember;

    @Builder
    public RecruitmentEntity(
            String title,
            String description,
            Integer recruiterCount,
            LocalDateTime closingDate
    ) {
        this.title = title;
        this.description = description;
        this.recruiterCount = recruiterCount;
        this.closingDate = closingDate;
    }

    public void opening() {
        this.status = RecruitmentStatus.OPEN;
    }

    public RecruitmentDto.Response toDto() {
        return RecruitmentDto.Response.builder()
                .recruitmentId(id)
                .title(title)
                .description(description)
                .recruiterCount(recruiterCount)
                .closingDate(closingDate)
                .modifyDate(modifyDate)
                .postingDate(postingDate)
                .status(status)
                .companyName(companyMember.getName())
                .companyMemberId(companyMember.getId())
                .build();
    }

    public RecruitmentEntity update(RecruitmentDto.Request request) {
        this.title = request.title();
        this.description = request.description();
        this.recruiterCount = request.recruiterCount();
        this.closingDate = request.closingDate();
        this.status = request.status();
        return this;
    }

}
