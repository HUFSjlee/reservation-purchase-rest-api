package com.reservationpurchase.reservationpurchase.module.like.presentation.controller;

import com.reservationpurchase.reservationpurchase.common.response.BaseResponse;
import com.reservationpurchase.reservationpurchase.module.like.domain.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/likes")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{userId}/like")
    public ResponseEntity likesReaction(@PathVariable Long userId, @RequestBody Map<String, Long> requestBody){
        Long postId = requestBody.get("postId");
        var response = likeService.likesReaction(userId, postId);

        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }
}
