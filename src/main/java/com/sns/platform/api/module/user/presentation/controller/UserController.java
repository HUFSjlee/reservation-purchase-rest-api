package com.sns.platform.api.module.user.presentation.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.sns.platform.api.common.response.BaseResponse;
import com.sns.platform.api.module.user.domain.service.EmailService;
import com.sns.platform.api.module.user.domain.service.UserService;
import com.sns.platform.api.module.user.presentation.dto.EmailAuthDTO;
import com.sns.platform.api.module.user.presentation.dto.EmailLoginRequestDto;
import com.sns.platform.api.module.user.presentation.dto.TokenDTO;
import com.sns.platform.api.module.user.presentation.dto.UserDTO;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;


    @ResponseBody
    @PostMapping("/sign-up/emailauth")
    public BaseResponse<String> emailCheck(@RequestParam String emailRequest)
            throws MessagingException, UnsupportedEncodingException {

        // ?대찓???몄쬆 肄붾뱶 諛쒖넚
        emailService.sendEmail(emailRequest);
        return BaseResponse.success("?몄쬆 肄붾뱶媛 諛쒖넚?섏뿀?듬땲??");
    }

    @PostMapping("/sign-up/emailauth/verify")
    public BaseResponse<String> verifyEmail(@RequestBody EmailAuthDTO.VerifyRequest request) {
        // ?대찓???몄쬆 肄붾뱶 寃利?
        emailService.verifyCode(request.getEmail(), request.getCode());
        return BaseResponse.success("?대찓???몄쬆???꾨즺?섏뿀?듬땲??");
    }

    @PostMapping("/signup")
    public ResponseEntity join(@Valid @RequestBody UserDTO.CreateRequest request) throws Exception {
        // ?뚯썝媛???꾩뿉 ?대찓???몄쬆 ?щ? ?뺤씤
        emailService.validateVerifiedEmail(request.getUserEmail());
        var response = userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @GetMapping("/{id}")
    public BaseResponse<UserDTO.FindResponse> findById(@PathVariable Long id) {
        return BaseResponse.success(userService.findById(id));
    }

    @PostMapping("/login")
    public BaseResponse<TokenDTO.LoginResponse> login(@Valid @RequestBody EmailLoginRequestDto request) {
        return BaseResponse.success(userService.login(request));
    }

    @PostMapping("/logout")
    public BaseResponse<String> logout(@RequestBody TokenDTO.RefreshRequest request) {
        userService.logout(request);
        return BaseResponse.success("로그아웃되었습니다.");
    }

    @PostMapping("/logout-all")
    public BaseResponse<String> logoutAll(@RequestBody TokenDTO.LogoutAllRequest request) {
        userService.logoutAllDevices(request);
        return BaseResponse.success("모든 기기에서 로그아웃되었습니다.");
    }

    @PostMapping("/token/refresh")
    public BaseResponse<TokenDTO.RefreshResponse> refreshToken(@RequestBody TokenDTO.RefreshRequest request) {
        return BaseResponse.success(userService.refreshAccessToken(request));
    }

    @PutMapping("/set-profile-image")
    public ResponseEntity<String> setProfileImage(@RequestParam("file") MultipartFile file, @RequestParam("email") String email) throws IOException, FirebaseAuthException {
        String imageUrl = userService.uploadAndSaveProfileImage(file, "someName", createRequestWithProfileImage(email));
        return ResponseEntity.ok().body(imageUrl);
    }
    private UserDTO.CreateRequest createRequestWithProfileImage(String email) {
        return UserDTO.CreateRequest.builder()
                .userEmail(email)
                .build();
    }

    /**
     * ?좎? ?대쫫, ?좎? ?꾨줈???대?吏, ?좎? ?뚭컻留??섏젙
     * */
    @PutMapping("/update/{id}")
    public BaseResponse<UserDTO.UpdateResponse> update(@PathVariable Long id, @Validated @RequestBody UserDTO.UpdateRequest request) {
        return BaseResponse.success(userService.update(id, request));
    }

    /**
     * ?좎? 鍮꾨?踰덊샇 ?섏젙
     * */
    @PutMapping("/{id}")
    public BaseResponse<UserDTO.UpdatePasswordResponse> updatePassword(@PathVariable Long id, @Validated @RequestBody UserDTO.UpdatePasswordRequest request) {
        return BaseResponse.success(userService.updatePassword(id,request));
    }
}

