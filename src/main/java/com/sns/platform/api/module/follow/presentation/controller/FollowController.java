package com.sns.platform.api.module.follow.presentation.controller;

import com.sns.platform.api.common.response.BaseResponse;
import com.sns.platform.api.module.follow.domain.service.FollowService;
import com.sns.platform.api.module.follow.presentation.dto.FollowDTO;
import com.sns.platform.api.module.newsfeed.domain.entity.NewsfeedType;
import com.sns.platform.api.module.newsfeed.domain.service.NewsfeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final NewsfeedService newsfeedService;

    @PostMapping("/follow")
    public ResponseEntity newCreateFollow(@RequestBody FollowDTO.CreateRequest request){

        var response = followService.newCreateFollow(request);
        String inputMessage = response.getFollower()+"?섏씠"+response.getFollowing()+"?섏쓣"+NewsfeedType.FOLLOW.getMessage();
        newsfeedService.createnewsfeed(response.getFollower(),response.getFollowing(),inputMessage);
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

}

