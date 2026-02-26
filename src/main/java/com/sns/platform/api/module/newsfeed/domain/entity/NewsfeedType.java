package com.sns.platform.api.module.newsfeed.domain.entity;

import lombok.Getter;

@Getter
public enum NewsfeedType {
    COMMENT("?볤????묒꽦?덉뒿?덈떎."),
    COMMENT_LIKE("?볤???醫뗭븘?⑸땲??"),
    POST("寃뚯떆湲???묒꽦?섏??듬땲??"),
    POST_LIKE("寃뚯떆湲??醫뗭븘?⑸땲??"),
    FOLLOW("?붾줈?고븯?⑥뒿?덈떎.");
    String message;
    NewsfeedType(String message) {
        this.message = message;
    }
}

