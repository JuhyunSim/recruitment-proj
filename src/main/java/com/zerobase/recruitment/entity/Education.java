package com.zerobase.recruitment.entity;

import com.zerobase.recruitment.dto.EducationDto;
import com.zerobase.recruitment.enums.Degree;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class Education {
    @Enumerated(EnumType.STRING)
    private Degree degree;
    private String graduateSchoolNames;

    public EducationDto toDto()  {
        return EducationDto.builder()
                .degree(degree)
                .graduateSchoolNames(graduateSchoolNames)
                .build();
    }
}
