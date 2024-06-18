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
    // 고졸 0, 대졸 1, 석사 2, 박사 3
    private Integer degree;
    private String graduateSchoolNames;
    // 0: 서울 , 1: 연세, 2: 고려, 3: 카이스트, 4: 성균관, 5: 한양, 6: 서강, 10: 서울고등학교
    private Integer code;
    public EducationDto toDto()  {
        return EducationDto.builder()
                .degree(degree)
                .graduateSchoolNames(graduateSchoolNames)
                .code(code)
                .build();
    }
}
