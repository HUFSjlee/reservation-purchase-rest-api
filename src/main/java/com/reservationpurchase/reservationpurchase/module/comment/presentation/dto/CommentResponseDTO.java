package com.reservationpurchase.reservationpurchase.module.comment.presentation.dto;

import com.reservationpurchase.reservationpurchase.module.comment.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private Long postId;
    private String commentText;


    public CommentResponseDTO(Comment comment){
        this.id = comment.getId();
        this.commentText = comment.getCommentText();
        this.postId = comment.getPost().getPostId();
    }
}