package com.sns.platform.api.module.user.domain.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.sns.platform.api.common.exception.LoginException;
import com.sns.platform.api.common.exception.UsernameNotFoundException;
import com.sns.platform.api.config.jwt.JwtTokenProvider;
import com.sns.platform.api.module.user.domain.entity.User;
import com.sns.platform.api.module.user.domain.mapper.UserMapper;
import com.sns.platform.api.module.user.infrastructure.UserRepository;
import com.sns.platform.api.module.user.presentation.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserDTO.CreateResponse signUp(UserDTO.CreateRequest request) throws Exception {
        var user = userMapper.toEntity(request);

        if (userRepository.findByUserEmail(user.getUserEmail()).isPresent()){
            throw new Exception("?대? 媛?낇븳 ?대찓?쇱엯?덈떎.");
        }

        // ?ш린?쒕뒗 encodePassword ?몄텧 ?쒓굅
        // user.updatePassword(request.getUserPassword(), passwordEncoder);

        var savedMember = userRepository.save(user);

        return UserDTO.CreateResponse.builder()
                .id(savedMember.getId())
                .build();
    }

    public UserDTO.FindResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("硫ㅻ쾭媛 議댁옱?섏? ?딆뒿?덈떎. id = " + id));
        return userMapper.toFindResponse(user);
    }

    @Transactional
    public String login(UserDTO.CreateRequest request) {
        //硫ㅻ쾭 ?대찓??泥댄겕
        User user = userRepository.findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new LoginException("硫ㅻ쾭瑜?議고쉶?????놁뒿?덈떎."));

        //硫ㅻ쾭 鍮꾨?踰덊샇 泥댄겕
        if (!passwordEncoder.matches(request.getUserPassword(), user.getUserPassword())) {
            throw new LoginException("鍮꾨?踰덊샇媛 ?쇱튂?섏? ?딆뒿?덈떎.");
        }

        return "濡쒓렇???깃났";
    }

    @Transactional
    public String uploadAndSaveProfileImage(MultipartFile file, String nameFile, UserDTO.CreateRequest request) throws IOException, FirebaseAuthException {
        String imageUrl = firebaseService.uploadFiles(file, nameFile);

        // ?대?吏 URL??Member ?뷀떚?곗뿉 ???
        updateProfileImage(request.getUserEmail(), imageUrl);

        return imageUrl;
    }

    @Transactional
    public void updateProfileImage(String email, String imageUrl) {
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new LoginException("Member not found with email: " + email));

        User updatedUser = User.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .userEmail(user.getUserEmail())
                .userPassword(user.getUserPassword())
                .userProfileImage(imageUrl)  // ?낅뜲?댄듃???대?吏 URL ?ㅼ젙
                .userGreetings(user.getUserGreetings())
                .build();

        userRepository.save(updatedUser);
    }

    @Transactional
    public UserDTO.UpdateResponse update(Long id, UserDTO.UpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("?좎? ?뺣낫媛 ?놁뒿?덈떎. id = " + id));

        user.update(request.getUserName(), request.getUserProfileImage(), request.getUserGreetings());
        userRepository.save(user);
        return userMapper.toUpdateResponse(user);
    }

    @Transactional
    public UserDTO.UpdatePasswordResponse updatePassword(Long id, UserDTO.UpdatePasswordRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("?좎? ?뺣낫媛 ?놁뒿?덈떎. id " + id));

        user.updatePassword(request.getUserPassword(), passwordEncoder);
        userRepository.save(user);
        return userMapper.toUpdatePasswordResponse(user);
    }

}





