package com.zerobase.recruitment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zerobase.recruitment.entity.CompanyMemberEntity;
import com.zerobase.recruitment.entity.RecruitmentEntity;
import com.zerobase.recruitment.enums.RecruitmentStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


public class RecruitmentDto {

    public record Request (
        String title,
        String description,
        Integer recruiterCount,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        LocalDateTime closingDate,
        String companyMemberId,
        RecruitmentStatus status
    ) {
        public RecruitmentEntity toEntity() {
            return RecruitmentEntity.builder()
                    .title(title)
                    .description(description)
                    .recruiterCount(recruiterCount)
                    .closingDate(closingDate)
                    .build();
        }
    }

    @Builder
    @Getter
    @Setter
    public static class Response {
        private Long recruitmentId;
        private String title;
        private String description;
        private Integer recruiterCount;
        private LocalDateTime closingDate;
        private RecruitmentStatus status;
        private LocalDateTime modifyDate;
        private LocalDateTime postingDate;
        private Long companyMemberId;
        private String companyName;

    }

}
