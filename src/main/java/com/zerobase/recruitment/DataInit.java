//package com.zerobase.recruitment;
//
//import com.zerobase.recruitment.entity.CompanyMemberEntity;
//import com.zerobase.recruitment.entity.MemberEntity;
//import com.zerobase.recruitment.enums.MemberStatus;
//import com.zerobase.recruitment.enums.RecruitmentStatus;
//import com.zerobase.recruitment.repository.CompanyMemberRepository;
//import com.zerobase.recruitment.repository.MemberRepository;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.IntStream;
//
//@Component
//@RequiredArgsConstructor
//public class DataInit {
//    private final MemberRepository memberRepository;
//    private final CompanyMemberRepository companyMemberRepository;
//
//
//
//    @PostConstruct
//    public void init() {
//        LocalDateTime initialDate = LocalDateTime.now();
//        List<MemberEntity> memberEntities = new ArrayList<>();
//        List<CompanyMemberEntity> companyMemberEntities = new ArrayList<>();
//
//        IntStream.range(1, 101).forEach(i -> {
//                LocalDateTime joinDate = initialDate.plusDays(i - 1);
//                memberEntities.add(MemberEntity.builder()
//                        .name("개인회원" + i)
//                        .loginId("login" + i)
//                        .password("password" + i)
//                        .email("email" + i)
//                        .phoneNumber("phoneNumber" + i)
//                        .joinDate(joinDate)
//                        .status(MemberStatus.RED)
//                        .build());
//                }
//        );
//        IntStream.range(1, 101).forEach(i -> {
//                    LocalDateTime joinDate = initialDate.plusDays(i - 1);
//                    companyMemberEntities.add(CompanyMemberEntity.builder()
//                            .name("기업회원" + i)
//                            .loginId("login" + i)
//                            .password("password" + i)
//                            .contactName("contactName" + i)
//                            .contactEmail("contactEmail" + i)
//                            .contactPhone("contactPhone" + i)
//                            .joinDate(joinDate)
//                            .status(RecruitmentStatus.CLOSE)
//                            .build());
//                }
//        );
//
//        memberRepository.saveAll(memberEntities);
//        companyMemberRepository.saveAll(companyMemberEntities);
//    }
//}
