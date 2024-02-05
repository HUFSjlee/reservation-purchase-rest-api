package com.reservationpurchase.reservationpurchase.module.like.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

public class LikeDTO {

    @Getter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BaseResponse {
        private Long id;

        @JsonProperty(value = "comment_id")
        private Long commentId;

        @JsonProperty(value = "post_id")
        private Long postId;

        @JsonProperty(value = "user_id")
        private Long userId;

        @JsonProperty(value = "like_status")
        private boolean likeStatus;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRequest {
        private Long id;

        @JsonProperty(value = "comment_id")
        private Long commentId;

        @JsonProperty(value = "post_id")
        private Long postId;

        @JsonProperty(value = "user_id")
        private Long userId;

        @JsonProperty(value = "like_status")
        private boolean likeStatus;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResponse {

        @JsonProperty(value = "comment_id")
        private Long commentId;

        @JsonProperty(value = "post_id")
        private Long postId;

        @JsonProperty(value = "user_id")
        private Long userId;

        @JsonProperty(value = "like_status")
        private boolean likeStatus;
    }
}
