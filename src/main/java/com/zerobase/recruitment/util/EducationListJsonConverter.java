package com.zerobase.recruitment.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.recruitment.entity.Education;
import com.zerobase.recruitment.service.ResumeService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Converter
@RequiredArgsConstructor
public class EducationListJsonConverter implements AttributeConverter<List<Education>, String> {
    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(List<Education> educations) {
        if (Objects.isNull(educations)) {
            return Strings.EMPTY;
        }
        try {
            return objectMapper.writeValueAsString(educations);
        } catch (JsonProcessingException e) {
            //TODO 에러 핸들링 구현 예정
            throw new RuntimeException("error converting list to json", e);
        }
    }

    @Override
    public List<Education> convertToEntityAttribute(String s) {
        if (Strings.isBlank(s)) {
            return Collections.emptyList();
        }

        try {
            return objectMapper.readValue(s, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            //TODO 에러 핸들링
            throw new RuntimeException("error converting json to list", e);
        }
    }
}
