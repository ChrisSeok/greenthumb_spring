package com.greenThumb.dto.request;

import com.greenThumb.domain.Comment;
import com.greenThumb.domain.Post;
import com.greenThumb.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequestDto {

    private Long id;
    private String comment;
    private User user;
    private Post post;

    /* dto -> entity */
    public Comment toEntity() {
        Comment comments = Comment.builder()
                .id(id)
                .comment(comment)
                .user(user)
                .post(post)
                .build();

        return comments;
    }
}
