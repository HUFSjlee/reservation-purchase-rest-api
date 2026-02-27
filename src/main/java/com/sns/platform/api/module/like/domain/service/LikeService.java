package com.sns.platform.api.module.like.domain.service;

import com.sns.platform.api.common.exception.NotFoundCommentException;
import com.sns.platform.api.common.exception.NotFoundPostException;
import com.sns.platform.api.common.exception.NotFoundUserException;
import com.sns.platform.api.module.comment.infrasturcture.CommentRepository;
import com.sns.platform.api.module.like.domain.entity.Like;
import com.sns.platform.api.module.like.infrastructure.LikeRepository;
import com.sns.platform.api.module.like.presentation.dto.LikeDTO;
import com.sns.platform.api.module.newsfeed.domain.entity.NewsfeedType;
import com.sns.platform.api.module.newsfeed.domain.service.NewsfeedService;
import com.sns.platform.api.module.comment.domain.entity.Comment;
import com.sns.platform.api.module.post.domain.entity.Post;
import com.sns.platform.api.module.post.infrastructure.PostRepository;
import com.sns.platform.api.module.user.domain.entity.User;
import com.sns.platform.api.module.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final NewsfeedService newsfeedService;

    public LikeDTO.CreateResponse likesReaction(Long userId, Long postId) {
        if (postId == null) {
            throw new IllegalArgumentException("postId는 필수입니다.");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("사용자를 찾을 수 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));

        LikeDTO.CreateResponse response;
        if(user.getLikes().stream().anyMatch(like -> like.getPost() != null && like.getPost().equals(post))) {
            likeRepository.deleteByUserAndPost(user,post);
            response = LikeDTO.CreateResponse.builder()
                    .userId(user.getId())
                    .postId(post.getPostId())
                    .likeStatus(false)
                    .build();
        } else {
            likeRepository.save(Like.builder().post(post).user(user).build());
            response = LikeDTO.CreateResponse.builder()
                    .userId(user.getId())
                    .postId(post.getPostId())
                    .likeStatus(true)
                    .build();

            User postOwner = userRepository.findById(post.getUserId())
                    .orElseThrow(() -> new NotFoundUserException("게시글 작성자를 찾을 수 없습니다."));
            String message = user.getUsername() + "님이 " + postOwner.getUsername() + "님의 글을 좋아합니다.";
            newsfeedService.publishActivity(user.getId(), message, NewsfeedType.POST_LIKE);
            // 게시글 작성자에게도 알림 노출
            newsfeedService.createForUser(postOwner.getId(), user.getId(), message, NewsfeedType.POST_LIKE);
        }

        return response;
    }

    public LikeDTO.CreateResponse commentLikesReaction(Long userId, Long commentId) {
        if (commentId == null) {
            throw new IllegalArgumentException("commentId는 필수입니다.");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("사용자를 찾을 수 없습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundCommentException("댓글을 찾을 수 없습니다."));

        LikeDTO.CreateResponse response;
        if (user.getLikes().stream().anyMatch(like -> like.getComment() != null && like.getComment().equals(comment))) {
            likeRepository.deleteByUserAndComment(user, comment);
            response = LikeDTO.CreateResponse.builder()
                    .userId(user.getId())
                    .commentId(comment.getId())
                    .likeStatus(false)
                    .build();
        } else {
            likeRepository.save(Like.builder().comment(comment).user(user).build());
            response = LikeDTO.CreateResponse.builder()
                    .userId(user.getId())
                    .commentId(comment.getId())
                    .likeStatus(true)
                    .build();

            User commentOwner = comment.getUser();
            if (commentOwner != null) {
                String message = user.getUsername() + "님이 " + commentOwner.getUsername() + "님의 댓글을 좋아합니다.";
                newsfeedService.publishActivity(user.getId(), message, NewsfeedType.COMMENT_LIKE);
                // 댓글 작성자에게도 알림 노출
                newsfeedService.createForUser(commentOwner.getId(), user.getId(), message, NewsfeedType.COMMENT_LIKE);
            }
        }

        return response;
    }
}

