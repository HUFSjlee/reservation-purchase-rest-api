package com.sns.platform.api.module.user.domain.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.sns.platform.api.common.exception.LoginException;
import com.sns.platform.api.common.exception.UsernameNotFoundException;
import com.sns.platform.api.config.jwt.JwtTokenProvider;
import com.sns.platform.api.module.user.domain.entity.User;
import com.sns.platform.api.module.user.domain.mapper.UserMapper;
import com.sns.platform.api.module.user.infrastructure.UserRepository;
import com.sns.platform.api.module.user.presentation.dto.EmailLoginRequestDto;
import com.sns.platform.api.module.user.presentation.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final FirebaseService firebaseService;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserDTO.CreateResponse signUp(UserDTO.CreateRequest request) throws Exception {
        var user = userMapper.toEntity(request);

        if (userRepository.findByUserEmail(user.getUserEmail()).isPresent()){
            throw new Exception("이미 가입된 이메일입니다.");
        }

        // UserMapper에서 비밀번호를 암호화합니다.
        // user.updatePassword(request.getUserPassword(), passwordEncoder);

        var savedMember = userRepository.save(user);

        return UserDTO.CreateResponse.builder()
                .id(savedMember.getId())
                .build();
    }

    public UserDTO.FindResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id = " + id)
        );
        return userMapper.toFindResponse(user);
    }

    @Transactional
    public String login(EmailLoginRequestDto request) {
        User user = userRepository.findByUserEmail(request.getEmail())
                .orElseThrow(() -> new LoginException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getUserPassword())) {
            throw new LoginException("비밀번호가 일치하지 않습니다.");
        }

        return jwtTokenProvider.createToken(user.getUserEmail(), user.getId(), user.getUsername());
    }

    @Transactional
    public String uploadAndSaveProfileImage(MultipartFile file, String nameFile, UserDTO.CreateRequest request) throws IOException, FirebaseAuthException {
        String imageUrl = firebaseService.uploadFiles(file, nameFile);

        // 이미지 URL을 사용자 프로필에 저장
        updateProfileImage(request.getUserEmail(), imageUrl);

        return imageUrl;
    }

    @Transactional
    public void updateProfileImage(String email, String imageUrl) {
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new LoginException("이메일로 사용자를 찾을 수 없습니다: " + email));

        User updatedUser = User.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .userEmail(user.getUserEmail())
                .userPassword(user.getUserPassword())
                .userProfileImage(imageUrl)
                .userGreetings(user.getUserGreetings())
                .build();

        userRepository.save(updatedUser);
    }

    @Transactional
    public UserDTO.UpdateResponse update(Long id, UserDTO.UpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다. id = " + id)
        );

        user.update(request.getUserName(), request.getUserProfileImage(), request.getUserGreetings());
        userRepository.save(user);
        return userMapper.toUpdateResponse(user);
    }

    @Transactional
    public UserDTO.UpdatePasswordResponse updatePassword(Long id, UserDTO.UpdatePasswordRequest request) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("사용자 정보를 찾을 수 없습니다. id = " + id)
        );

        user.updatePassword(request.getUserPassword(), passwordEncoder);
        userRepository.save(user);
        return userMapper.toUpdatePasswordResponse(user);
    }

}





