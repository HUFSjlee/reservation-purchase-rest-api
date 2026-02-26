package com.reservationpurchase.reservationpurchase.module.user.infrastructure;

import com.reservationpurchase.reservationpurchase.module.post.domain.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {
    Optional<EmailAuth> findTopByEmailOrderByIdDesc(String email);
}
