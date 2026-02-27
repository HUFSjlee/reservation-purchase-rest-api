package com.sns.platform.api.module.follow.infrastructure;

import com.sns.platform.api.module.follow.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollower_IdAndFollowing_Id(Long followerId, Long followingId);

    @Query("SELECT f.follower.id FROM Follow f WHERE f.following.id = :followingId")
    List<Long> findFollowerIdsByFollowingId(@Param("followingId") Long followingId);
}

