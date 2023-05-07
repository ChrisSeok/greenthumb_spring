package com.greenThumb.domain;

import jdk.jfr.Category;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;



    private Long fileId;

    @Builder
    public Post(String title, String content, String category, Long fileId, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.fileId = fileId;
        this.user = user;
    }

    // 비즈니스 메서드 //
    public void update(String title, String content, String category, Long fileId, User user) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.fileId = fileId;
        this.user = user;
    }
}
