package com.sns.platform.api.module.user.infrastructure;

import com.sns.platform.api.module.user.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenHash(String tokenHash);
    void deleteByTokenHash(String tokenHash);
    void deleteByUser_Id(Long userId);
}
