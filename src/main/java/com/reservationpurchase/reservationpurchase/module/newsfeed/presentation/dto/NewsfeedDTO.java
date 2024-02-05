package com.reservationpurchase.reservationpurchase.module.newsfeed.presentation.dto;

import com.reservationpurchase.reservationpurchase.module.newsfeed.domain.entity.NewsfeedType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

public class NewsfeedDTO {
    @Getter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRequest {
        private Long userId;
        private Long contentProvider;
        private String message;
    }

    @Getter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResponse {
        private Long userId;
        private Long contentProvider;
        private String message;
        private NewsfeedType newsfeedType;
        private List<Long> userIds;
    }
}
