package com.sns.platform.api.module.like.infrastructure;

import com.sns.platform.api.module.like.domain.entity.Like;
import com.sns.platform.api.module.post.domain.entity.Post;
import com.sns.platform.api.module.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    void deleteByUserAndPost(User user, Post post);
}

