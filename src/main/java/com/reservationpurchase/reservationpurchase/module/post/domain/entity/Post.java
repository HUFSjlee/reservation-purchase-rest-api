package com.reservationpurchase.reservationpurchase.module.post.domain.entity;

import com.reservationpurchase.reservationpurchase.common.base.BaseEntity;
import com.reservationpurchase.reservationpurchase.module.comment.domain.entity.Comment;
import com.reservationpurchase.reservationpurchase.module.like.domain.entity.Like;
import com.reservationpurchase.reservationpurchase.module.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    //@ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_content")
    private String postContent;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Like> likes;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments;

    public void update(Long userId, String postContent) {
        this.userId = userId;
        this.postContent = postContent;
    }
}
