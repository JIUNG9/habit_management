package com.module.service;

import com.module.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long memberId, long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest);

    void deleteComment(Long postId, Long commentId);
}
