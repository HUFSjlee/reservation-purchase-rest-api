package com.sns.platform.api.module.comment.presentation.dto;

import com.sns.platform.api.common.exception.NotFoundPostException;
import com.sns.platform.api.module.comment.domain.entity.Comment;
import com.sns.platform.api.module.comment.infrasturcture.CommentRepository;
import com.sns.platform.api.module.post.infrastructure.PostRepository;
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
