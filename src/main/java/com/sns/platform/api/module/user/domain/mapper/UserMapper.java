package com.sns.platform.api.module.user.domain.mapper;

import com.sns.platform.api.module.user.domain.entity.User;
import com.sns.platform.api.module.user.presentation.dto.UserDTO;
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
                .userPassword(passwordEncoder.encode(request.getUserPassword())) // 鍮꾨?踰덊샇瑜??뷀샇?뷀븯???ㅼ젙
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


