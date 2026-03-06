package com.sns.platform.api.module.newsfeed.presentation.dto;

import com.sns.platform.api.module.newsfeed.domain.entity.NewsfeedType;
import lombok.Getter;

@Getter
public class NewsfeedReadItemDto {
    private final Long userId;
    private final Long contentProvider;
    private final String message;
    private final NewsfeedType newsfeedType;

    public NewsfeedReadItemDto(Long userId, Long contentProvider, String message, NewsfeedType newsfeedType) {
        this.userId = userId;
        this.contentProvider = contentProvider;
        this.message = message;
        this.newsfeedType = newsfeedType;
    }

    public NewsfeedDTO.Item toItem() {
        return NewsfeedDTO.Item.builder()
                .userId(userId)
                .contentProvider(contentProvider)
                .message(message)
                .newsfeedType(newsfeedType)
                .build();
    }
}
