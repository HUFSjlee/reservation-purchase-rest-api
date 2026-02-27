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

    //?몄쬆 ????대찓??
    @Column(name = "email", nullable = false)
    private String email;

    //?몄쬆 肄붾뱶
    @Column(name = "code", nullable = false)
    private String code;

    //?몄쬆 ?꾨즺 ?щ?
    @Column(name = "verified", nullable = false)
    private boolean verified;

    //?몄쬆 留뚮즺 ?쒓컙
    @Column(name = "expires_at", nullable = false)
    private LocalDate expiresAt;

    //?몄쬆 ?꾨즺 泥섎━
    public void verity() {
        this.verified = true;
    }
}

