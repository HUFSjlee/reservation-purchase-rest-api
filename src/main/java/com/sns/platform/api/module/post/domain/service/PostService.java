package com.sns.platform.api.module.post.domain.service;

import com.sns.platform.api.common.exception.NotFoundPostException;
import com.sns.platform.api.common.exception.NotFoundUserException;
import com.sns.platform.api.module.newsfeed.domain.entity.NewsfeedType;
import com.sns.platform.api.module.newsfeed.domain.service.NewsfeedService;
import com.sns.platform.api.module.post.domain.entity.Post;
import com.sns.platform.api.module.post.infrastructure.PostRepository;
import com.sns.platform.api.module.post.presentation.dto.PostDTO;
import com.sns.platform.api.module.user.domain.entity.User;
import com.sns.platform.api.module.user.infrastructure.UserRepository;
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
    private final NewsfeedService newsfeedService;

    @Transactional
    public PostDTO.CreateResponse write(PostDTO.CreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundUserException("사용자를 찾을 수 없습니다."));
        Post post = Post.builder()
                .userId(request.getUserId())
                .postContent(request.getContent())
                .createdAt(LocalDateTime.now())
                .createdBy("USER")
                .updatedAt(LocalDateTime.now())
                .updatedBy("USER")
                .build();

        var savedPost = postRepository.save(post);
        String message = user.getUserName() + "님이 게시글을 작성했습니다.";
        newsfeedService.publishActivity(user.getId(), message, NewsfeedType.POST);

        return PostDTO.CreateResponse.builder()
                .userId(savedPost.getPostId())
                .build();
    }

    @Transactional
    public PostDTO.UpdateResponse update(Long id, PostDTO.UpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("해당 게시글을 찾을 수 없습니다. id = " + id));

        post.update(post.getUserId(),post.getPostContent());

        return PostDTO.UpdateResponse.builder()
                .content(post.getPostContent())
                .build();
    }

    @Transactional
    public PostDTO.DeleteResponse deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("해당 게시글을 찾을 수 없습니다."));

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

