package com.sns.platform.api.module.like.domain.service;

import com.sns.platform.api.common.exception.NotFoundPostException;
import com.sns.platform.api.common.exception.NotFoundUserException;
import com.sns.platform.api.module.comment.infrasturcture.CommentRepository;
import com.sns.platform.api.module.like.domain.entity.Like;
import com.sns.platform.api.module.like.infrastructure.LikeRepository;
import com.sns.platform.api.module.like.presentation.dto.LikeDTO;
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

    public LikeDTO.CreateResponse likesReaction(Long userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow(()->new NotFoundUserException("?좎?媛 ?놁뒿?덈떎."));
        Post post = postRepository.findById(postId).orElseThrow(()->new NotFoundPostException("寃뚯떆湲???놁뒿?덈떎."));

        LikeDTO.CreateResponse response;
        if(user.getLikes().stream().anyMatch(like -> like.getPost().equals(post))) {
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
        }

        return response;
    }
}

