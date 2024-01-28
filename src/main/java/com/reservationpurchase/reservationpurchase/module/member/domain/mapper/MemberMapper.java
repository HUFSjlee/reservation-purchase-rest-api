package com.reservationpurchase.reservationpurchase.module.member.domain.mapper;

import com.reservationpurchase.reservationpurchase.module.member.domain.entity.Member;
import com.reservationpurchase.reservationpurchase.module.member.presentation.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class MemberMapper {
    private final PasswordEncoder passwordEncoder;
    public Member toEntity(MemberDTO.CreateRequest request) {
        return Member.builder()
                .memberName(request.getMemberName())
                .memberEmail(request.getMemberEmail())
                .memberPassword(passwordEncoder.encode(request.getMemberPassword())) // 비밀번호를 암호화하여 설정
                .memberProfileImage(request.getMemberProfileImage())
                .memberGreetings(request.getMemberGreetings())
                .createdAt(LocalDateTime.now())
                .createdBy(request.getMemberName())
                .updatedAt(LocalDateTime.now())
                .updatedBy(request.getMemberName())
                .build();
    }

    public MemberDTO.FindResponse toFindResponse(Member entity) {
        return MemberDTO.FindResponse.builder()
                .id(entity.getId())
                .build();
    }
}
