package com.zerobase.recruitment.dto;

import com.zerobase.recruitment.entity.*;
import com.zerobase.recruitment.enums.ApplicationStatus;
import com.zerobase.recruitment.enums.RecruitmentStatus;
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

@Setter
@Getter
public class ApplicationDto {

    public record Request(
        Long memberId,
        Long resumeId
    ) {}

    @Builder
    @Getter
    @Setter
    public static class Response {
        private Long id;
        private ApplicationStatus status;
        private LocalDateTime appliedDate;
        private Long recruitmentId;
        private String recruitmentTitle;
        private String resumeTitle;
        private List<EducationDto> educationList;
        private String memberName;
    }




}
