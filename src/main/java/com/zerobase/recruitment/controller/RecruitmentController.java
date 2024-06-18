package com.zerobase.recruitment.controller;

import com.zerobase.recruitment.dto.ApplicationDto;
import com.zerobase.recruitment.dto.RecruitmentDto;
import com.zerobase.recruitment.service.RecruitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecruitmentController {
    private final RecruitService recruitService;

    @PostMapping("/recruitments")
    public void postingRecruitment(
            @RequestBody RecruitmentDto.Request request
    ) {
        log.info("Recruitment request: {}", request.toString());
        recruitService.postingRecruitment(request);
    }

    @GetMapping("/recruitments")
    public List<RecruitmentDto.Response> getRecruitments() {
        return recruitService.getRecruitmentList();
    }

    @GetMapping("/recruitments/{id}")
    public RecruitmentDto.Response getRecruitment(@PathVariable Long id) {
        return recruitService.getRecruitment(id);
    }

    @PutMapping("/recruitments/{id}")
    public RecruitmentDto.Response updateRecruitment(
            @PathVariable(name = "id") Long recruitmentId,
            @RequestBody RecruitmentDto.Request request
    ) {
        return recruitService.updateRecruitments(recruitmentId, request);
    }

    @DeleteMapping("/recruitments/{id}")
    public void deleteRecruitment(@PathVariable(name = "id") Long recruitmentId,
            @RequestBody RecruitmentDto.Request request
    ){
        recruitService.deleteRecruitment(recruitmentId, request);
    }

    @PostMapping("/recruitments/{id}/applications")
    public void applyRecruitment(@PathVariable(name = "id") Long recruitmentId,
                                 @RequestBody ApplicationDto.Request request
    ) {
        recruitService.applyRecruitment(recruitmentId, request);
    }

    @GetMapping("/recruitments/{id}/applications")
    public List<ApplicationDto.Response> applyRecruitment(
            @PathVariable(name = "id") Long recruitmentId,
            @RequestParam(name = "companyMemberId") Long companyMemberId
    ) {
        return recruitService.getApplicationList(recruitmentId, companyMemberId);
    }
}
