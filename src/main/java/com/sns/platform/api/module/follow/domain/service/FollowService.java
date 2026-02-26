package com.sns.platform.api.module.follow.domain.service;

import com.sns.platform.api.module.follow.domain.entity.Follow;
import com.sns.platform.api.module.follow.infrastructure.FollowRepository;
import com.sns.platform.api.module.follow.presentation.dto.FollowDTO;
import com.sns.platform.api.module.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FollowService {
    private final FollowRepository followRepository;
    @Transactional
    public FollowDTO.CreateResponse newCreateFollow(FollowDTO.CreateRequest request) {

        Follow followEntity = Follow.builder()
                .following(User.builder().id(request.getFollowing()).build())
                .follower(User.builder().id(request.getFollower()).build())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("following")
                .updatedBy("following")
                .build();

        followEntity = followRepository.save(followEntity);

        return FollowDTO.CreateResponse.builder()
                .id(followEntity.getId())
                .following(request.getFollowing())
                .follower(request.getFollower())
                .build();
    }
}



