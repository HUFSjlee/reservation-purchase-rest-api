package com.sns.platform.api.module.comment.infrasturcture;

import com.sns.platform.api.module.comment.domain.entity.Comment;
import com.sns.platform.api.module.post.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPost(Post post, Pageable pageable);
    Page<Comment> findAllByPostAndParentCommentIsNull(Post post, Pageable pageable);
}

