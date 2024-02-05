package com.reservationpurchase.reservationpurchase.module.post.domain.service;

import com.reservationpurchase.reservationpurchase.common.exception.NotFoundPostException;
import com.reservationpurchase.reservationpurchase.common.exception.NotFoundUserException;
import com.reservationpurchase.reservationpurchase.module.post.domain.entity.Post;
import com.reservationpurchase.reservationpurchase.module.post.infrastructure.PostRepository;
import com.reservationpurchase.reservationpurchase.module.post.presentation.dto.PostDTO;
import com.reservationpurchase.reservationpurchase.module.user.domain.entity.User;
import com.reservationpurchase.reservationpurchase.module.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostDTO.CreateResponse write(PostDTO.CreateRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new NotFoundUserException("Member not found"));
        Post post = Post.builder()
                .userId(request.getUserId())
                .postContent(request.getContent())
                .createdAt(LocalDateTime.now())
                .createdBy("USER")
                .updatedAt(LocalDateTime.now())
                .updatedBy("USER")
                .build();

        var savedPost = postRepository.save(post);

        return PostDTO.CreateResponse.builder()
                .userId(savedPost.getPostId())
                .build();
    }

    @Transactional
    public PostDTO.UpdateResponse update(Long id, PostDTO.UpdateRequest request) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundPostException("해당 포스트가 존재하지 않습니다. id= " + id));

        post.update(post.getUserId(),post.getPostContent());

        return PostDTO.UpdateResponse.builder()
                .content(post.getPostContent())
                .build();
    }

    @Transactional
    public PostDTO.DeleteResponse deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundPostException("해당 포스트가 존재하지 않습니다."));

        postRepository.delete(post);

        return PostDTO.DeleteResponse.builder()
                .postId(id)
                .build();
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Optional<Post> findById(Long id){
        return postRepository.findById(id);
    }
}
