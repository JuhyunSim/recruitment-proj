package com.zerobase.recruitment.dto;

import com.zerobase.recruitment.entity.Education;
import com.zerobase.recruitment.enums.Degree;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Builder
@Setter
@Getter
public class EducationDto {

    @Enumerated(EnumType.STRING)
    private Degree degree;
    private String graduateSchoolNames;

    public Education toEntity() {
        return Education.builder()
                .degree(this.degree)
                .graduateSchoolNames(this.graduateSchoolNames)
                .build();
    }
}
