package com.sns.platform.api.module.comment.presentation.dto;

import com.sns.platform.api.common.exception.NotFoundPostException;
import com.sns.platform.api.module.comment.domain.entity.Comment;
import com.sns.platform.api.module.comment.infrasturcture.CommentRepository;
import com.sns.platform.api.module.post.infrastructure.PostRepository;
import com.sns.platform.api.module.user.infrastructure.UserRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentPostRequestDTO {
    @NotNull(message = "postId는 필수입니다.")
    private Long postId;
    @NotNull(message = "userId는 필수입니다.")
    private Long userId;

    @NotBlank(message = "commentText는 필수입니다.")
    private String commentText;


    public Comment toEntity(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        return Comment.builder()
                .post(postRepository.findById(this.postId).orElseThrow(NotFoundPostException::new))
                .user(userRepository.findById(this.userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")))
                .commentText(this.commentText)
                .build();
    }
}
