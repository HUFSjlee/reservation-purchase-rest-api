package com.reservationpurchase.reservationpurchase.module.newsfeed.domain.entity;

import com.reservationpurchase.reservationpurchase.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Table(name = "newsfeed")
public class Newsfeed extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "content_provider")
    private Long contentProvider;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "newsfeed_type")
    private NewsfeedType newsfeedType;

}
