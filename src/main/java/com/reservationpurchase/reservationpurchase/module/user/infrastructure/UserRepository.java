package com.reservationpurchase.reservationpurchase.module.user.infrastructure;

import com.reservationpurchase.reservationpurchase.module.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findByUserName(String userName);
}
