package com.sns.platform.api.module.post.domain.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Table(name = "email_auth")
public class EmailAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 인증 대상 이메일
    @Column(name = "email", nullable = false)
    private String email;

    // 인증 코드
    @Column(name = "code", nullable = false)
    private String code;

    // 인증 완료 여부
    @Column(name = "verified", nullable = false)
    private boolean verified;

    // 인증 만료 시간
    @Column(name = "expires_at", nullable = false)
    private LocalDate expiresAt;

    // 인증 완료 처리
    public void verity() {
        this.verified = true;
    }
}

