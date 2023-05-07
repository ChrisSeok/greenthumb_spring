package com.greenThumb.dto.request;

import com.greenThumb.domain.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String category;

    private Long fileId;


    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .category(category)
                .fileId(fileId)
                .build();
    }

}
