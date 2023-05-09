package com.greenThumb.dto.response;

import com.greenThumb.domain.Comment;
import com.greenThumb.domain.Post;
import com.greenThumb.domain.User;
import com.greenThumb.dto.request.CommentRequestDto;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long id;
    private String comment;
    private String username;
    private Long postId;
    private String created;

    /* entity -> dto */
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.username = comment.getUser().getUsername();
        this.postId = comment.getPost().getId();
        this.created = comment.getCreated();
    }

}
