package com.reservationpurchase.reservationpurchase.module.like.domain.entity;

import com.reservationpurchase.reservationpurchase.module.comment.domain.entity.Comment;
import com.reservationpurchase.reservationpurchase.module.post.domain.entity.Post;
import com.reservationpurchase.reservationpurchase.module.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "like_status")
    private boolean likeStatus;
}
