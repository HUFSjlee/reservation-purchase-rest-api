package com.reservationpurchase.reservationpurchase.module.comment.presentation.dto;

import com.reservationpurchase.reservationpurchase.common.exception.NotFoundPostException;
import com.reservationpurchase.reservationpurchase.module.comment.domain.entity.Comment;
import com.reservationpurchase.reservationpurchase.module.comment.infrasturcture.CommentRepository;
import com.reservationpurchase.reservationpurchase.module.post.infrastructure.PostRepository;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentPostRequestDTO {
    private Long postId;

    private String commentText;


    public Comment toEntity(PostRepository postRepository, CommentRepository commentRepository) {
        return Comment.builder()
                .post(postRepository.findById(this.postId).orElseThrow(NotFoundPostException::new))
                .commentText(this.commentText)
                .build();
    }
}