package com.greenThumb.response;

import com.greenThumb.domain.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String category;
    private String created;
    private String modified;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.category = post.getCategory();
        this.created = post.getCreated();
        this.modified = post.getModified();
    }
}
