package com.reservationpurchase.reservationpurchase.module.comment.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentPutRequestDTO {

    private Long commentId;
    private String commentText;
}
