package com.sns.platform.api.module.newsfeed.domain.service;

import com.sns.platform.api.module.follow.infrastructure.FollowRepository;
import com.sns.platform.api.module.newsfeed.domain.entity.Newsfeed;
import com.sns.platform.api.module.newsfeed.domain.entity.NewsfeedType;
import com.sns.platform.api.module.newsfeed.infrastructure.NewsfeedRepository;
import com.sns.platform.api.module.newsfeed.presentation.dto.NewsfeedDTO;
import com.sns.platform.api.module.newsfeed.presentation.dto.NewsfeedReadItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsfeedService {

    private static final int DEFAULT_PERIOD_DAYS = 30;

    private final NewsfeedRepository newsfeedRepository;
    private final FollowRepository followRepository;

    public NewsfeedDTO.ReadResponse read(Long userId, NewsfeedType type, Integer periodDays) {
        int validatedPeriodDays = (periodDays == null || periodDays < 1) ? DEFAULT_PERIOD_DAYS : periodDays;
        LocalDateTime fromDate = LocalDateTime.now().minusDays(validatedPeriodDays);

        List<NewsfeedReadItemDto> all = newsfeedRepository.findReadableNewsfeedItems(userId, type, fromDate);
        List<NewsfeedDTO.Item> items = all.stream()
                .map(NewsfeedReadItemDto::toItem)
                .collect(Collectors.toList());
        return NewsfeedDTO.ReadResponse.builder().items(items).build();
    }

    @Transactional
    public void publishActivity(Long actorUserId, String message, NewsfeedType type) {
        // 작성자 본인에게도 활동이 보이도록 생성한다.
        createForUser(actorUserId, actorUserId, message, type);

        // 작성자의 팔로워들에게 활동을 전파한다.
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
