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

    private Integer degree;
    private Integer code;
    private String graduateSchoolNames;

    public Education toEntity() {
        return Education.builder()
                .degree(this.degree)
                .graduateSchoolNames(this.graduateSchoolNames)
                .code(code)
                .build();
    }
}
