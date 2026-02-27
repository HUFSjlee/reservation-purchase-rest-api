package com.sns.platform.api.module.comment.presentation.controller;

import com.sns.platform.api.common.PageableValidator;
import com.sns.platform.api.module.comment.domain.service.CommentService;
import com.sns.platform.api.module.comment.presentation.dto.CommentPostRequestDTO;
import com.sns.platform.api.module.comment.presentation.dto.CommentPutRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final PageableValidator pageableValidator;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity createComment(@RequestBody @Valid CommentPostRequestDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        return ResponseEntity.ok(commentService.saveComment(dto));
    }


    @GetMapping
    public ResponseEntity getComments(Long postId, Pageable pageable) {
        pageableValidator.validate(pageable);
        return ResponseEntity.ok(commentService.findAllByPostAndParentCommentIsNull(postId, pageable));
    }

    @PutMapping
    public ResponseEntity updateComment(@RequestBody @Valid CommentPutRequestDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        return ResponseEntity.ok(commentService.updateComment(dto));
    }
}

