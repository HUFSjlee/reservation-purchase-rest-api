package com.sns.platform.api.module.newsfeed.infrastructure;

import com.sns.platform.api.module.newsfeed.domain.entity.Newsfeed;
import com.sns.platform.api.module.newsfeed.domain.entity.NewsfeedType;
import com.sns.platform.api.module.newsfeed.presentation.dto.NewsfeedReadItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsfeedRepository extends JpaRepository<Newsfeed, Long> {
    @Query("SELECT n FROM Newsfeed n WHERE n.contentProvider = :userId OR n.userId IN :followList")
    List<Newsfeed> findByUserIdOrContentProvider(@Param("userId") Long userId, @Param("followList") List<Long> followList);

    List<Newsfeed> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("""
            SELECT new com.sns.platform.api.module.newsfeed.presentation.dto.NewsfeedReadItemDto(
                n.userId,
                n.contentProvider,
                n.message,
                n.newsfeedType
            )
            FROM Newsfeed n
            WHERE n.userId = :userId
              AND n.createdAt >= :fromDate
              AND (:type IS NULL OR n.newsfeedType = :type)
              AND (
                    n.contentProvider = :userId
                    OR EXISTS (
                        SELECT 1
                        FROM Follow f
                        WHERE f.follower.id = :userId
                          AND f.following.id = n.contentProvider
                    )
              )
            ORDER BY n.createdAt DESC
            """)
    List<NewsfeedReadItemDto> findReadableNewsfeedItems(@Param("userId") Long userId,
                                                        @Param("type") NewsfeedType type,
                                                        @Param("fromDate") LocalDateTime fromDate);
}



