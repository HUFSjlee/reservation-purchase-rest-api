package com.reservationpurchase.reservationpurchase.module.newsfeed.domain.entity;

import lombok.Getter;

@Getter
public enum NewsfeedType {
    COMMENT("댓글을 작성했습니다."),
    COMMENT_LIKE("댓글을 좋아합니다."),
    POST("게시글을 작성하였습니다."),
    POST_LIKE("게시글을 좋아합니다."),
    FOLLOW("팔로우하셨습니다.");
    String message;
    NewsfeedType(String message) {
        this.message = message;
    }
}
