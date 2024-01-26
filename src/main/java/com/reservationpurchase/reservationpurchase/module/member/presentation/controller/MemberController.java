package com.reservationpurchase.reservationpurchase.module.member.presentation.controller;

import com.reservationpurchase.reservationpurchase.common.response.BaseResponse;
import com.reservationpurchase.reservationpurchase.module.member.domain.entity.Member;
import com.reservationpurchase.reservationpurchase.module.member.domain.service.MemberService;
import com.reservationpurchase.reservationpurchase.module.member.presentation.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity create(@RequestBody @Validated MemberDTO.CreateRequest request) {
        var response = memberService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Member member) {
        System.out.println(member);
        String ok = "로그인 하셨습니다.";
        return ResponseEntity.ok(ok);
    }

    @GetMapping("/{id}")
    public BaseResponse<MemberDTO.FindResponse> findById(@PathVariable Long id) {
        return BaseResponse.success(memberService.findById(id));
    }
}
