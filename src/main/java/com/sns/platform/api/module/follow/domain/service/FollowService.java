package com.sns.platform.api.module.follow.domain.service;

import com.sns.platform.api.module.follow.domain.entity.Follow;
import com.sns.platform.api.module.follow.infrastructure.FollowRepository;
import com.sns.platform.api.module.follow.presentation.dto.FollowDTO;
import com.sns.platform.api.module.user.domain.entity.User;
import com.sns.platform.api.module.user.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class
FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    @Transactional
    public FollowDTO.CreateResponse newCreateFollow(FollowDTO.CreateRequest request) {
        if (request.getFollower().equals(request.getFollowing())) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        User follower = userRepository.findById(request.getFollower())
                .orElseThrow(() -> new IllegalArgumentException("팔로워 사용자를 찾을 수 없습니다."));
        User following = userRepository.findById(request.getFollowing())
                .orElseThrow(() -> new IllegalArgumentException("팔로잉 사용자를 찾을 수 없습니다."));

        if (followRepository.existsByFollower_IdAndFollowing_Id(follower.getId(), following.getId())) {
            throw new IllegalStateException("이미 팔로우한 사용자입니다.");
        }

        Follow followEntity = Follow.builder()
                .following(following)
                .follower(follower)
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



