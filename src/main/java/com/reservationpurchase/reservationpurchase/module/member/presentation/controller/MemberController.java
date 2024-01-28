package com.reservationpurchase.reservationpurchase.module.member.presentation.controller;

import com.reservationpurchase.reservationpurchase.common.response.BaseResponse;
import com.reservationpurchase.reservationpurchase.module.member.domain.entity.Member;
import com.reservationpurchase.reservationpurchase.module.member.domain.service.EmailService;
import com.reservationpurchase.reservationpurchase.module.member.domain.service.MemberService;
import com.reservationpurchase.reservationpurchase.module.member.presentation.dto.MemberDTO;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final EmailService emailService;

    @ResponseBody
    @PostMapping("/sign-up/emailauth")
    public BaseResponse<String> EmailCheck(@RequestParam String emailRequest) throws MessagingException, UnsupportedEncodingException {
        var authCode = emailService.sendEmail(emailRequest);
        return BaseResponse.success(authCode);
    }

    @PostMapping("/signup")
    public ResponseEntity join(@Valid @RequestBody MemberDTO.CreateRequest request) throws Exception {
        var response = memberService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @GetMapping("/{id}")
    public BaseResponse<MemberDTO.FindResponse> findById(@PathVariable Long id) {
        return BaseResponse.success(memberService.findById(id));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Member member) {
        System.out.println(member);
        String ok = "로그인 하셨습니다.";
        return ResponseEntity.ok(ok);
    }

}
