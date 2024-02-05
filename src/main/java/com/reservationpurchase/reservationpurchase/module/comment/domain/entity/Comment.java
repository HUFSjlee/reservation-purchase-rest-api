package com.reservationpurchase.reservationpurchase.module.comment.domain.entity;

import com.reservationpurchase.reservationpurchase.module.comment.presentation.dto.CommentPutRequestDTO;
import com.reservationpurchase.reservationpurchase.module.follow.domain.entity.Follow;
import com.reservationpurchase.reservationpurchase.module.like.domain.entity.Like;
import com.reservationpurchase.reservationpurchase.module.post.domain.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor
@Getter
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "comment_text")
    private String commentText;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    private List<Like> likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = true)
    private Comment parentComment;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentComment")
    private List<Comment> childComments = new ArrayList<>();

    public void updateText(CommentPutRequestDTO dto) {
        this.commentText = dto.getCommentText();
    }
}
