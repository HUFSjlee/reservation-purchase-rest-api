package com.sns.platform.api.module.post.presentation.controller;

import com.sns.platform.api.common.response.BaseResponse;
import com.sns.platform.api.module.post.domain.entity.Post;
import com.sns.platform.api.module.post.domain.service.PostService;
import com.sns.platform.api.module.post.presentation.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {
    private final PostService postService;

    /**
     * 寃뚯떆湲 ?곌린
     * */
    @PostMapping("/write")
    public ResponseEntity write(@Validated @RequestBody PostDTO.CreateRequest request) {
        var response = postService.write(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    /**
     * 寃뚯떆湲 ?섏젙
     * */
    @PutMapping("/{id}")
    public BaseResponse<PostDTO.UpdateResponse> update(@PathVariable Long id, @Validated @RequestBody PostDTO.UpdateRequest request) {
        return BaseResponse.success(postService.update(id,request));
    }

    /**
     * 寃뚯떆湲 ??젣
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        var deletedResponse = postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(deletedResponse));
    }

    /**
     * 寃뚯떆湲 議고쉶
     * */
    @GetMapping("/all")
    public Page<Post> read() {
        PageRequest pageRequest = PageRequest.of(0,5);
        return postService.findAll(pageRequest);
    }
}

