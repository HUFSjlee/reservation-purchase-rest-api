package com.reservationpurchase.reservationpurchase.module.member.domain.service;

import com.reservationpurchase.reservationpurchase.module.member.domain.entity.Member;
import com.reservationpurchase.reservationpurchase.module.member.domain.mapper.MemberMapper;
import com.reservationpurchase.reservationpurchase.module.member.infrastructure.MemberRepository;
import com.reservationpurchase.reservationpurchase.module.member.presentation.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public MemberDTO.CreateResponse create(MemberDTO.CreateRequest request) {
        var member = memberMapper.toEntity(request);
        var savedEntity = memberRepository.save(member);
        return MemberDTO.CreateResponse.builder()
                .id(savedEntity.getId())
                .build();
    }

    public MemberDTO.FindResponse findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("멤버가 존재하지 않습니다. id = " + id));
        return memberMapper.toFindResponse(member);
    }
}
