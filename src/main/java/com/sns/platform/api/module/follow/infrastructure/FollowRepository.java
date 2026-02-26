package com.sns.platform.api.module.follow.infrastructure;

import com.sns.platform.api.module.follow.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollower_IdAndFollowing_Id(Long followerId, Long followingId);
}

