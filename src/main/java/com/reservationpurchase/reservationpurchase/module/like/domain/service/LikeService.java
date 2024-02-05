package com.reservationpurchase.reservationpurchase.module.like.domain.service;

import com.reservationpurchase.reservationpurchase.common.exception.NotFoundPostException;
import com.reservationpurchase.reservationpurchase.common.exception.NotFoundUserException;
import com.reservationpurchase.reservationpurchase.module.comment.infrasturcture.CommentRepository;
import com.reservationpurchase.reservationpurchase.module.like.domain.entity.Like;
import com.reservationpurchase.reservationpurchase.module.like.infrastructure.LikeRepository;
import com.reservationpurchase.reservationpurchase.module.like.presentation.dto.LikeDTO;
import com.reservationpurchase.reservationpurchase.module.post.domain.entity.Post;
import com.reservationpurchase.reservationpurchase.module.post.infrastructure.PostRepository;
import com.reservationpurchase.reservationpurchase.module.user.domain.entity.User;
import com.reservationpurchase.reservationpurchase.module.user.infrastructure.UserRepository;
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
        User user = userRepository.findById(userId).orElseThrow(()->new NotFoundUserException("유저가 없습니다."));
        Post post = postRepository.findById(postId).orElseThrow(()->new NotFoundPostException("게시글이 없습니다."));

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
