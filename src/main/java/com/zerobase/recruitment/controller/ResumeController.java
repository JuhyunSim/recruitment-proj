package com.zerobase.recruitment.controller;

import com.zerobase.recruitment.dto.RecruitmentDto;
import com.zerobase.recruitment.dto.ResumeDto;
import com.zerobase.recruitment.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping("/resumes")
    public void postResume(@RequestBody ResumeDto.Request request) {
        log.info(request.toString());
        log.info(request.educationDtoList().toString());
        resumeService.postResume(request);
    }

    @GetMapping("/resumes")
    public List<ResumeDto.Response> getResumeList() {
        return resumeService.getResumeList();
    }

    @GetMapping("/resumes/{id}")
    public ResumeDto.Response getResume(@PathVariable(name = "id") Long resumeId) {
        return resumeService.getResume(resumeId);
    }

    @PutMapping("/resumes/{id}")
    public ResumeDto.Response updateResume(
            @PathVariable(name = "id") Long resumeId,
            @RequestBody ResumeDto.Request request
    ) {
        return resumeService.updateResume(resumeId, request);
    }

    @DeleteMapping("/resumes/{id}")
    public void deleteResume(@PathVariable(name = "id") Long resumeId,
                                  @RequestBody ResumeDto.Request request
    ){
        resumeService.deleteResume(resumeId, request);
    }
}
