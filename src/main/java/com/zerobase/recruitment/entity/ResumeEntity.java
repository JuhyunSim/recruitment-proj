package com.zerobase.recruitment.entity;

import com.zerobase.recruitment.enums.Degree;
import com.zerobase.recruitment.enums.ResumeStatus;
import com.zerobase.recruitment.util.EducationListJsonConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "resume")
@Getter
@Setter
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
    private Integer recruiterCount;
    private LocalDateTime closingDate;
    @Enumerated(EnumType.STRING)
    private ResumeStatus status;
    @UpdateTimestamp
    private LocalDateTime modifyDate;
    @CreationTimestamp
    private LocalDateTime postingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

}
