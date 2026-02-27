package com.sns.platform.api.module.post.domain.entity;

import com.sns.platform.api.common.base.BaseEntity;
import com.sns.platform.api.module.comment.domain.entity.Comment;
import com.sns.platform.api.module.like.domain.entity.Like;
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

