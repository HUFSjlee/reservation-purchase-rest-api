package com.sns.platform.api.module.newsfeed.infrastructure;

import com.sns.platform.api.module.newsfeed.domain.entity.Newsfeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsfeedRepository extends JpaRepository<Newsfeed, Long> {
    @Query("SELECT n FROM Newsfeed n WHERE n.contentProvider = :userId OR n.userId IN :followList")
    List<Newsfeed> findByUserIdOrContentProvider(@Param("userId") Long userId, @Param("followList") List<Long> followList);

    List<Newsfeed> findByUserIdOrderByCreatedAtDesc(Long userId);
}



