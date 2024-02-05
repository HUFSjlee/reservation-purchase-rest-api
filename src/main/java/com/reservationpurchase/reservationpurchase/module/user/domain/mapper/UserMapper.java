package com.reservationpurchase.reservationpurchase.module.user.domain.mapper;

import com.reservationpurchase.reservationpurchase.module.user.domain.entity.User;
import com.reservationpurchase.reservationpurchase.module.user.presentation.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    public User toEntity(UserDTO.CreateRequest request) {
        return User.builder()
                .userName(request.getUserName())
                .userEmail(request.getUserEmail())
                .userPassword(passwordEncoder.encode(request.getUserPassword())) // 비밀번호를 암호화하여 설정
                .userProfileImage(request.getUserProfileImage())
                .userGreetings(request.getUserGreetings())
                .createdAt(LocalDateTime.now())
                .createdBy(request.getUserName())
                .updatedAt(LocalDateTime.now())
                .updatedBy(request.getUserName())
                .build();
    }

    public UserDTO.FindResponse toFindResponse(User entity) {
        return UserDTO.FindResponse.builder()
                .id(entity.getId())
                .build();
    }

    public UserDTO.UpdateResponse toUpdateResponse(User entity) {
        return UserDTO.UpdateResponse.builder()
                .userName(entity.getUsername())
                .userProfileImage(entity.getUserProfileImage())
                .userGreetings(entity.getUserGreetings())
                .build();
    }

    public UserDTO.UpdatePasswordResponse toUpdatePasswordResponse(User entity) {
        return UserDTO.UpdatePasswordResponse.builder()
                .userPassword(entity.getUserPassword())
                .build();
    }
}

