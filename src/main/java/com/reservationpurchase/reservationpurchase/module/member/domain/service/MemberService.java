package com.reservationpurchase.reservationpurchase.module.domain.service;

import com.reservationpurchase.reservationpurchase.module.domain.entity.Member;
import com.reservationpurchase.reservationpurchase.module.domain.mapper.MemberMapper;
import com.reservationpurchase.reservationpurchase.module.infrastructure.MemberRepository;
import com.reservationpurchase.reservationpurchase.module.presentation.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
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
