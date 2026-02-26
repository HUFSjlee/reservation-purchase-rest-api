package com.sns.platform.api.module.follow.presentation.controller;

import com.sns.platform.api.common.response.BaseResponse;
import com.sns.platform.api.module.follow.domain.service.FollowService;
import com.sns.platform.api.module.follow.presentation.dto.FollowDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity newCreateFollow(@Validated @RequestBody FollowDTO.CreateRequest request){

        var response = followService.newCreateFollow(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

}

