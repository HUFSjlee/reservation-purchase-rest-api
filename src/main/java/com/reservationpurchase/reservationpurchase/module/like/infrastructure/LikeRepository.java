package com.reservationpurchase.reservationpurchase.module.like.infrastructure;

import com.reservationpurchase.reservationpurchase.module.like.domain.entity.Like;
import com.reservationpurchase.reservationpurchase.module.post.domain.entity.Post;
import com.reservationpurchase.reservationpurchase.module.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    void deleteByUserAndPost(User user, Post post);
}
