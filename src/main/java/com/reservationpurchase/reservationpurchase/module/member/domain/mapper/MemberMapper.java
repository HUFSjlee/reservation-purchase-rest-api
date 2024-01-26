package com.reservationpurchase.reservationpurchase.module.domain.mapper;

import com.reservationpurchase.reservationpurchase.common.base.BaseEntity;
import com.reservationpurchase.reservationpurchase.module.domain.entity.Member;
import com.reservationpurchase.reservationpurchase.module.presentation.dto.MemberDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MemberMapper {
    public Member toEntity(MemberDTO.CreateRequest request) {
        return Member.builder()
                .memberName(request.getMemberName())
                .memberEmail(request.getMemberEmail())
                .memberPassword(request.getMemberPassword())
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
