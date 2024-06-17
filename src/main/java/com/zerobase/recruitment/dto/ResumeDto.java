package com.zerobase.recruitment.dto;

import com.zerobase.recruitment.entity.Education;
import com.zerobase.recruitment.entity.MemberEntity;
import com.zerobase.recruitment.entity.ResumeEntity;
import com.zerobase.recruitment.enums.ResumeStatus;
import com.zerobase.recruitment.util.EducationListJsonConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

public class ResumeDto {
    private Long id;
    private String title;
    private String description;
    private List<EducationDto> educationList;
    private ResumeStatus status;
    private LocalDateTime modifyDate;
    private LocalDateTime postingDate;
    private String memberLoginId;

    public record Request(
            String title,
            String description,
            List<EducationDto> educationDtoList,
            String memberLoginId
    ) {
        public ResumeEntity toEntity() {
            return ResumeEntity.builder()
                    .title(this.title)
                    .description(this.description)
                    .educationList(this.educationDtoList.stream().map(EducationDto::toEntity).toList())
                    .build();
        }
    }

    @Builder
    @Getter
    @Setter
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private List<EducationDto> educationList;
        private ResumeStatus status;
        private LocalDateTime modifyDate;
        private LocalDateTime postingDate;
        private Long memberId;
    }







}
