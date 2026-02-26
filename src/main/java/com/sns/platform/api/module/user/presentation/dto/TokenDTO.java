package com.sns.platform.api.module.user.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TokenDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse {
        private String accessToken;
        private String refreshToken;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RefreshRequest {
        private String refreshToken;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RefreshResponse {
        private String accessToken;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LogoutAllRequest {
        private Long userId;
    }
}
