package com.module.service.impl;

import com.module.entity.Comment;
import com.module.entity.Post;
import com.module.entity.User;
import com.module.repository.UserRepository;
import com.module.utils.PrimitiveLongToInteger;

import com.module.exception.AppApiException;
import com.module.exception.ResourceNotFoundException;
import com.module.dto.CommentDto;
import com.module.repository.CommentRepository;
import com.module.repository.PostRepository;
import com.module.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    UserRepository userRepository;
    CommentRepository commentRepository;
    PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CommentDto createComment(Long memberId, long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        //retrieve member entity by id
        User user = userRepository.findById(Math.toIntExact(memberId)).orElseThrow(()-> new ResourceNotFoundException("Member", "Member ID", memberId));

        //retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post ID", postId));

        //set post to comment
        comment.updateComment(user, post);

        // comment entity to db
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
        // retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        // convert list of comment entities to list of comment dto's
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new AppApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new AppApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        comment.updateCommentBody(commentRequest);

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new AppApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        commentRepository.delete(comment);
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = CommentDto.builder()
                .id(comment.getId())
                .name(comment.getUser().getName())
                .body(comment.getBody())
                .createdTime(comment.getCreatedDate())
                .modifiedTime(comment.getModifiedDate())
                .build();

        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = Comment.builder()
                .id(commentDto.getId())
                .body(commentDto.getBody())
                .build();

        return comment;
    }
}
