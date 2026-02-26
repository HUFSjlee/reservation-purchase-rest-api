package com.sns.platform.api.module.newsfeed.domain.service;

import com.sns.platform.api.module.newsfeed.domain.entity.Newsfeed;
import com.sns.platform.api.module.newsfeed.domain.entity.NewsfeedType;
import com.sns.platform.api.module.newsfeed.infrastructure.NewsfeedRepository;
import com.sns.platform.api.module.newsfeed.presentation.dto.NewsfeedDTO;
import com.sns.platform.api.module.follow.infrastructure.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsfeedService {

    private final NewsfeedRepository newsfeedRepository;
    private final FollowRepository followRepository;

    public NewsfeedDTO.ReadResponse read(Long userId) {
        List<Newsfeed> all = newsfeedRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<NewsfeedDTO.Item> items = all.stream()
                .map(n -> NewsfeedDTO.Item.builder()
                        .userId(n.getUserId())
                        .contentProvider(n.getContentProvider())
                        .message(n.getMessage())
                        .newsfeedType(n.getNewsfeedType())
                        .build())
                .collect(Collectors.toList());
        return NewsfeedDTO.ReadResponse.builder().items(items).build();
    }

    @Transactional
    public void publishToFollowers(Long actorUserId, String message, NewsfeedType type) {
        List<Long> followerIds = followRepository.findFollowerIdsByFollowingId(actorUserId);
        for (Long followerId : followerIds) {
            createForUser(followerId, actorUserId, message, type);
        }
    }

    @Transactional
    public void createForUser(Long userId, Long contentProvider, String message, NewsfeedType type) {
        Newsfeed newsfeed = Newsfeed.builder()
                .userId(userId)
                .contentProvider(contentProvider)
                .message(message)
                .newsfeedType(type)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        newsfeedRepository.save(newsfeed);
    }
}

