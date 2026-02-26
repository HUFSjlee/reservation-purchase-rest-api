package com.sns.platform.api.module.user.presentation.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.sns.platform.api.common.response.BaseResponse;
import com.sns.platform.api.config.jwt.JwtTokenProvider;
import com.sns.platform.api.module.user.domain.service.EmailService;
import com.sns.platform.api.module.user.domain.service.FirebaseService;
import com.sns.platform.api.module.user.domain.service.UserService;
import com.sns.platform.api.module.user.presentation.dto.EmailAuthDTO;
import com.sns.platform.api.module.user.presentation.dto.SignInDto;
import com.sns.platform.api.module.user.presentation.dto.UserDTO;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final EmailService emailService;

    private final FirebaseService firebaseService;
    private final JwtTokenProvider jwtTokenProvider;

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
    public ResponseEntity<String> login(@Valid @RequestBody UserDTO.CreateRequest request) {

        return ResponseEntity.ok(userService.login(request));
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

    @PostMapping("/sign-in")
    public String signIn(@RequestBody SignInDto signInDto) {
        String userName = signInDto.getUserName();
        String userEmail = signInDto.getUserEmail();

        signInDto.setUserName(userName);
        String jwtToken = jwtTokenProvider.createToken(signInDto);

        log.info("request username = {}, password = {}", userName, userEmail);
        return jwtToken;
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

