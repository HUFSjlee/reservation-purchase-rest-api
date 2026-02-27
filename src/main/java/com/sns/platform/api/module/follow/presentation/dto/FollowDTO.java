package com.sns.platform.api.module.follow.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sns.platform.api.module.user.domain.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

public class FollowDTO {
    @Getter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BaseResponse {
        @JsonProperty(value = "id")
        private Long id;
        @JsonProperty(value = "following")
        private User following;
        @JsonProperty(value = "follower")
        private User follower;
    }

    @Getter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRequest {
        @JsonProperty(value = "id")
        private Long id;
        @NotNull(message = "following은 필수입니다.")
        @JsonProperty(value = "following")
        private Long following;
        @NotNull(message = "follower는 필수입니다.")
        @JsonProperty(value = "follower")
        private Long follower;
    }

    @Getter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResponse {
        @JsonProperty(value = "id")
        private Long id;
        @JsonProperty(value = "following")
        private Long following;
        @JsonProperty(value = "follower")
        private Long follower;
    }
}

