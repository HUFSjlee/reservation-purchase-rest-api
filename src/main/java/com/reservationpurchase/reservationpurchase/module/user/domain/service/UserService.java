package com.reservationpurchase.reservationpurchase.module.user.domain.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.reservationpurchase.reservationpurchase.common.exception.LoginException;
import com.reservationpurchase.reservationpurchase.common.exception.UsernameNotFoundException;
import com.reservationpurchase.reservationpurchase.config.jwt.JwtTokenProvider;
import com.reservationpurchase.reservationpurchase.module.user.domain.entity.User;
import com.reservationpurchase.reservationpurchase.module.user.domain.mapper.UserMapper;
import com.reservationpurchase.reservationpurchase.module.user.infrastructure.UserRepository;
import com.reservationpurchase.reservationpurchase.module.user.presentation.dto.UserDTO;
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
            throw new Exception("?´ë? ê°€?…í•œ ?´ë©”?¼ìž…?ˆë‹¤.");
        }

        // ?¬ê¸°?œëŠ” encodePassword ?¸ì¶œ ?œê±°
        // user.updatePassword(request.getUserPassword(), passwordEncoder);

        var savedMember = userRepository.save(user);

        return UserDTO.CreateResponse.builder()
                .id(savedMember.getId())
                .build();
    }

    public UserDTO.FindResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("ë©¤ë²„ê°€ ì¡´ìž¬?˜ì? ?ŠìŠµ?ˆë‹¤. id = " + id));
        return userMapper.toFindResponse(user);
    }

    @Transactional
    public String login(UserDTO.CreateRequest request) {
        //ë©¤ë²„ ?´ë©”??ì²´í¬
        User user = userRepository.findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new LoginException("ë©¤ë²„ë¥?ì¡°íšŒ?????†ìŠµ?ˆë‹¤."));

        //ë©¤ë²„ ë¹„ë?ë²ˆí˜¸ ì²´í¬
        if (!passwordEncoder.matches(request.getUserPassword(), user.getUserPassword())) {
            throw new LoginException("ë¹„ë?ë²ˆí˜¸ê°€ ?¼ì¹˜?˜ì? ?ŠìŠµ?ˆë‹¤.");
        }

        return "ë¡œê·¸???±ê³µ";
    }

    @Transactional
    public String uploadAndSaveProfileImage(MultipartFile file, String nameFile, UserDTO.CreateRequest request) throws IOException, FirebaseAuthException {
        String imageUrl = firebaseService.uploadFiles(file, nameFile);

        // ?´ë?ì§€ URL??Member ?”í‹°?°ì— ?€??
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
                .userProfileImage(imageUrl)  // ?…ë°?´íŠ¸???´ë?ì§€ URL ?¤ì •
                .userGreetings(user.getUserGreetings())
                .build();

        userRepository.save(updatedUser);
    }

    @Transactional
    public UserDTO.UpdateResponse update(Long id, UserDTO.UpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("? ì? ?•ë³´ê°€ ?†ìŠµ?ˆë‹¤. id = " + id));

        user.update(request.getUserName(), request.getUserProfileImage(), request.getUserGreetings());
        userRepository.save(user);
        return userMapper.toUpdateResponse(user);
    }

    @Transactional
    public UserDTO.UpdatePasswordResponse updatePassword(Long id, UserDTO.UpdatePasswordRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("? ì? ?•ë³´ê°€ ?†ìŠµ?ˆë‹¤. id " + id));

        user.updatePassword(request.getUserPassword(), passwordEncoder);
        userRepository.save(user);
        return userMapper.toUpdatePasswordResponse(user);
    }

}




