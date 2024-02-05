package com.reservationpurchase.reservationpurchase.module.post.infrastructure;

import com.reservationpurchase.reservationpurchase.module.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
