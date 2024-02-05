package com.reservationpurchase.reservationpurchase.module.comment.domain.service;

import com.reservationpurchase.reservationpurchase.common.exception.NotFoundCommentException;
import com.reservationpurchase.reservationpurchase.common.exception.NotFoundPostException;
import com.reservationpurchase.reservationpurchase.module.comment.domain.entity.Comment;
import com.reservationpurchase.reservationpurchase.module.comment.infrasturcture.CommentRepository;
import com.reservationpurchase.reservationpurchase.module.comment.presentation.dto.CommentPostRequestDTO;
import com.reservationpurchase.reservationpurchase.module.comment.presentation.dto.CommentPutRequestDTO;
import com.reservationpurchase.reservationpurchase.module.comment.presentation.dto.CommentResponseDTO;
import com.reservationpurchase.reservationpurchase.module.post.domain.entity.Post;
import com.reservationpurchase.reservationpurchase.module.post.domain.service.PostService;
import com.reservationpurchase.reservationpurchase.module.post.infrastructure.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final PostRepository postRepository;

    public CommentResponseDTO saveComment(CommentPostRequestDTO dto) {
        Comment savedComment = commentRepository.save(dto.toEntity(postRepository, commentRepository));

        return new CommentResponseDTO(savedComment);
    }

    public Page<CommentResponseDTO> findByPostId(Long postId, Pageable pageable) {
        Post post = postService.findById(postId).orElseThrow(NotFoundPostException::new);
        return commentRepository.findAllByPost(post, pageable).map(CommentResponseDTO::new);
    }


    public Page<CommentResponseDTO> findAllByPostAndParentCommentIsNull(Long postId, Pageable pageable) {
        Post post = postService.findById(postId).orElseThrow(NotFoundPostException::new);
        return commentRepository.findAllByPostAndParentCommentIsNull(post, pageable).map(CommentResponseDTO::new);
    }

    public CommentResponseDTO updateComment(CommentPutRequestDTO dto) {
        Comment comment = commentRepository.findById(dto.getCommentId()).orElseThrow(NotFoundCommentException::new);

        comment.updateText(dto);
        commentRepository.saveAndFlush(comment);
        return new CommentResponseDTO(comment);
    }

    public Comment findByCommentId(Long paretnCommentId) {
        return commentRepository.findById(paretnCommentId).orElseThrow(NotFoundCommentException::new);
    }
}
