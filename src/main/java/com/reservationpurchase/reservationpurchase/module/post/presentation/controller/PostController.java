package com.reservationpurchase.reservationpurchase.module.post.presentation.controller;

import com.reservationpurchase.reservationpurchase.common.response.BaseResponse;
import com.reservationpurchase.reservationpurchase.common.response.PageResponse;
import com.reservationpurchase.reservationpurchase.module.post.domain.entity.Post;
import com.reservationpurchase.reservationpurchase.module.post.domain.service.PostService;
import com.reservationpurchase.reservationpurchase.module.post.presentation.dto.PostDTO;
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
     * 게시글 쓰기
     * */
    @PostMapping("/write")
    public ResponseEntity write(@Validated @RequestBody PostDTO.CreateRequest request) {
        var response = postService.write(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    /**
     * 게시글 수정
     * */
    @PutMapping("/{id}")
    public BaseResponse<PostDTO.UpdateResponse> update(@PathVariable Long id, @Validated @RequestBody PostDTO.UpdateRequest request) {
        return BaseResponse.success(postService.update(id,request));
    }

    /**
     * 게시글 삭제
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        var deletedResponse = postService.deletePost(id);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.success(deletedResponse));
    }

    /**
     * 게시글 조회
     * */
    @GetMapping("/all")
    public Page<Post> read() {
        PageRequest pageRequest = PageRequest.of(0,5);
        return postService.findAll(pageRequest);
    }
}
