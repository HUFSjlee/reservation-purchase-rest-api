package com.reservationpurchase.reservationpurchase.module.user.presentation.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.reservationpurchase.reservationpurchase.common.response.BaseResponse;
import com.reservationpurchase.reservationpurchase.config.jwt.JwtTokenProvider;
import com.reservationpurchase.reservationpurchase.module.user.domain.service.EmailService;
import com.reservationpurchase.reservationpurchase.module.user.domain.service.FirebaseService;
import com.reservationpurchase.reservationpurchase.module.user.domain.service.UserService;
import com.reservationpurchase.reservationpurchase.module.user.presentation.dto.SignInDto;
import com.reservationpurchase.reservationpurchase.module.user.presentation.dto.UserDTO;
import io.swagger.annotations.ApiOperation;
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
    public BaseResponse<String> EmailCheck(@RequestParam String emailRequest) throws MessagingException, UnsupportedEncodingException {
        var authCode = emailService.sendEmail(emailRequest);
        return BaseResponse.success(authCode);
    }

    @PostMapping("/signup")
    public ResponseEntity join(@Valid @RequestBody UserDTO.CreateRequest request) throws Exception {
        var response = userService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @GetMapping("/{id}")
    public BaseResponse<UserDTO.FindResponse> findById(@PathVariable Long id) {
        return BaseResponse.success(userService.findById(id));
    }

    @PostMapping("/login")
    @ApiOperation(value="로그인")
    public ResponseEntity<String> login(@Valid @RequestBody UserDTO.CreateRequest request) {

        return ResponseEntity.ok(userService.login(request));
    }

    @PutMapping("/set-profile-image")
    public ResponseEntity<String> setProfileImage(@RequestParam("file") MultipartFile file, @RequestParam("email") String email) throws IOException, FirebaseAuthException {
        //토큰 검증 먼저
        // USERDTO.RE response = jwtTokenProvider.getUserInfo(token);
        // response 들고 DB가서 정보를 가져오든 or
        // 이미 response가 원하는 정보를 가지고 있다면 imageurl 업데이트 실행 seq ...
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
     * 유저 이름, 유저 프로필 이미지, 유저 소개말 수정
     * */
    @PutMapping("/update/{id}")
    public BaseResponse<UserDTO.UpdateResponse> update(@PathVariable Long id, @Validated @RequestBody UserDTO.UpdateRequest request) {
        return BaseResponse.success(userService.update(id, request));
    }

    /**
     * 유저 비밀번호 수정
     * */
    @PutMapping("/{id}")
    public BaseResponse<UserDTO.UpdatePasswordResponse> updatePassword(@PathVariable Long id, @Validated @RequestBody UserDTO.UpdatePasswordRequest request) {
        return BaseResponse.success(userService.updatePassword(id,request));
    }
}
