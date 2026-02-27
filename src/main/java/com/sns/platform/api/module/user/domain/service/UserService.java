package com.sns.platform.api.module.user.domain.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.sns.platform.api.common.exception.LoginException;
import com.sns.platform.api.common.exception.UsernameNotFoundException;
import com.sns.platform.api.config.jwt.JwtTokenProvider;
import com.sns.platform.api.module.user.domain.entity.RefreshToken;
import com.sns.platform.api.module.user.domain.entity.User;
import com.sns.platform.api.module.user.domain.mapper.UserMapper;
import com.sns.platform.api.module.user.infrastructure.RefreshTokenRepository;
import com.sns.platform.api.module.user.infrastructure.UserRepository;
import com.sns.platform.api.module.user.presentation.dto.EmailLoginRequestDto;
import com.sns.platform.api.module.user.presentation.dto.TokenDTO;
import com.sns.platform.api.module.user.presentation.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final FirebaseService firebaseService;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserDTO.CreateResponse signUp(UserDTO.CreateRequest request) throws Exception {
        var user = userMapper.toEntity(request);

        if (userRepository.findByUserEmail(user.getUserEmail()).isPresent()){
            throw new Exception("?대? 媛?낅맂 ?대찓?쇱엯?덈떎.");
        }

        // UserMapper?먯꽌 鍮꾨?踰덊샇瑜??뷀샇?뷀빀?덈떎.
        // user.updatePassword(request.getUserPassword(), passwordEncoder);

        var savedMember = userRepository.save(user);

        return UserDTO.CreateResponse.builder()
                .id(savedMember.getId())
                .build();
    }

    public UserDTO.FindResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("?ъ슜?먮? 李얠쓣 ???놁뒿?덈떎. id = " + id)
        );
        return userMapper.toFindResponse(user);
    }

    @Transactional
    public TokenDTO.LoginResponse login(EmailLoginRequestDto request) {
        User user = userRepository.findByUserEmail(request.getEmail())
                .orElseThrow(() -> new LoginException("?ъ슜?먮? 李얠쓣 ???놁뒿?덈떎."));

        if (!passwordEncoder.matches(request.getPassword(), user.getUserPassword())) {
            throw new LoginException("鍮꾨?踰덊샇媛 ?쇱튂?섏? ?딆뒿?덈떎.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user.getUserEmail(), user.getId(), user.getUserName());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserEmail(), user.getId());

        RefreshToken savedToken = RefreshToken.builder()
                .user(user)
                .tokenHash(hashToken(refreshToken))
                .expiresAt(LocalDateTime.now().plusDays(14))
                .build();
        refreshTokenRepository.save(savedToken);

        return TokenDTO.LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public String uploadAndSaveProfileImage(MultipartFile file, String nameFile, UserDTO.CreateRequest request) throws IOException, FirebaseAuthException {
        String imageUrl = firebaseService.uploadFiles(file, nameFile);

        // ?대?吏 URL???ъ슜???꾨줈?꾩뿉 ???        updateProfileImage(request.getUserEmail(), imageUrl);

        return imageUrl;
    }

    @Transactional
    public void updateProfileImage(String email, String imageUrl) {
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new LoginException("?대찓?쇰줈 ?ъ슜?먮? 李얠쓣 ???놁뒿?덈떎: " + email));

        user.updateProfileImage(imageUrl);
        userRepository.save(user);
    }

    @Transactional
    public UserDTO.UpdateResponse update(Long id, UserDTO.UpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("?ъ슜???뺣낫瑜?李얠쓣 ???놁뒿?덈떎. id = " + id)
        );

        user.update(request.getUserName(), request.getUserProfileImage(), request.getUserGreetings());
        userRepository.save(user);
        return userMapper.toUpdateResponse(user);
    }

    @Transactional
    public UserDTO.UpdatePasswordResponse updatePassword(Long id, UserDTO.UpdatePasswordRequest request) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("?ъ슜???뺣낫瑜?李얠쓣 ???놁뒿?덈떎. id = " + id)
        );

        user.updatePassword(request.getUserPassword(), passwordEncoder);
        userRepository.save(user);
        // 鍮꾨?踰덊샇 蹂寃???紐⑤뱺 湲곌린 濡쒓렇?꾩썐
        refreshTokenRepository.deleteByUser_Id(user.getId());
        return userMapper.toUpdatePasswordResponse(user);
    }

    @Transactional
    public void logout(TokenDTO.RefreshRequest request) {
        if (request == null || request.getRefreshToken() == null) {
            throw new LoginException("由ы봽?덉떆 ?좏겙???놁뒿?덈떎.");
        }

        if (!jwtTokenProvider.validateToken(request.getRefreshToken())) {
            throw new LoginException("?좏슚?섏? ?딆? 由ы봽?덉떆 ?좏겙?낅땲??");
        }

        refreshTokenRepository.deleteByTokenHash(hashToken(request.getRefreshToken()));
    }

    @Transactional
    public void logoutAllDevices(TokenDTO.LogoutAllRequest request) {
        if (request == null || request.getUserId() == null) {
            throw new LoginException("userId???꾩닔?낅땲??");
        }
        refreshTokenRepository.deleteByUser_Id(request.getUserId());
    }

    @Transactional(readOnly = true)
    public TokenDTO.RefreshResponse refreshAccessToken(TokenDTO.RefreshRequest request) {
        if (request == null || request.getRefreshToken() == null) {
            throw new LoginException("由ы봽?덉떆 ?좏겙???놁뒿?덈떎.");
        }

        if (!jwtTokenProvider.validateToken(request.getRefreshToken())) {
            throw new LoginException("?좏슚?섏? ?딆? 由ы봽?덉떆 ?좏겙?낅땲??");
        }

        String tokenHash = hashToken(request.getRefreshToken());
        RefreshToken saved = refreshTokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new LoginException("留뚮즺?섏뿀嫄곕굹 濡쒓렇?꾩썐???좏겙?낅땲??"));

        User user = saved.getUser();
        String accessToken = jwtTokenProvider.createAccessToken(user.getUserEmail(), user.getId(), user.getUserName());
        return TokenDTO.RefreshResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashed);
        } catch (Exception e) {
            throw new IllegalStateException("?좏겙 ?댁떆 ?앹꽦???ㅽ뙣?덉뒿?덈떎.");
        }
    }

}





