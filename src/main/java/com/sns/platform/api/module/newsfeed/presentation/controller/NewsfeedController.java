package com.sns.platform.api.module.newsfeed.presentation.controller;

import com.sns.platform.api.common.response.BaseResponse;
import com.sns.platform.api.module.newsfeed.domain.entity.NewsfeedType;
import com.sns.platform.api.module.newsfeed.domain.service.NewsfeedService;
import com.sns.platform.api.module.newsfeed.presentation.dto.NewsfeedDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/newsfeed")
public class NewsfeedController {
    private final NewsfeedService newsfeedService;

    @GetMapping
    public ResponseEntity read(@RequestParam Long userId,
                               @RequestParam(required = false) NewsfeedType type) {
        var response = newsfeedService.read(userId, type);
        return ResponseEntity.ok(BaseResponse.success(response));
    }
}

