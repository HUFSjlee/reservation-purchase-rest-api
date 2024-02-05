package com.reservationpurchase.reservationpurchase.module.follow.infrastructure;

import com.reservationpurchase.reservationpurchase.module.follow.domain.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

}
