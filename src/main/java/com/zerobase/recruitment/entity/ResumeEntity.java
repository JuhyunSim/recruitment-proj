package com.zerobase.recruitment.entity;

import com.zerobase.recruitment.dto.EducationDto;
import com.zerobase.recruitment.dto.ResumeDto;
import com.zerobase.recruitment.enums.Degree;
import com.zerobase.recruitment.enums.ResumeStatus;
import com.zerobase.recruitment.util.EducationListJsonConverter;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "resume")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long id;
    private String title;
    private String description;
    @Column(columnDefinition = "TEXT")
    @Convert(converter = EducationListJsonConverter.class)
    private List<Education> educationList;
    @Enumerated(EnumType.STRING)
    private ResumeStatus status;
    @UpdateTimestamp
    private LocalDateTime modifyDate;
    @CreationTimestamp
    private LocalDateTime postingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Builder
    public ResumeEntity(
            String title,
            String description,
            List<Education> educationList,
            ResumeStatus status
    ) {
        this.title = title;
        this.description = description;
        this.educationList = educationList;
        this.status = status;
    }

    public void open() {
        this.status = ResumeStatus.OPEN;
    }

    public ResumeDto.Response toDto() {
        return ResumeDto.Response.builder()
                .id(this.id)
                .title(this.title)
                .description(this.description)
                .educationList(this.educationList.stream().map(Education::toDto).toList())
                .status(this.status)
                .modifyDate(this.modifyDate)
                .postingDate(this.postingDate)
                .memberId(this.id)
                .build();
    }

    public ResumeEntity update(ResumeDto.Request request) {
        this.setTitle(request.title());
        this.setDescription(request.description());
        this.setEducationList(request.educationDtoList().stream()
                .map(EducationDto::toEntity).toList());
        this.setStatus(this.status);
        return this;
    }
}
