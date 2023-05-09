package com.greenThumb.service;

import com.greenThumb.domain.Comment;
import com.greenThumb.domain.Post;
import com.greenThumb.domain.User;
import com.greenThumb.dto.request.CommentRequestDto;
import com.greenThumb.repository.CommentRepository;
import com.greenThumb.repository.PostRepository;
import com.greenThumb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // 댓글 생성
    @Transactional
    public Long commentSave(String username, Long postId, CommentRequestDto dto) {

        User user = userRepository.findByUsername(username).get();
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("댓글 쓰기 실패: 해당 게시글이 존재하지 않습니다."));

        dto.setUser(user);
        dto.setPost(post);

        Comment comment = dto.toEntity();
        commentRepository.save(comment);

        return dto.getId();
    }
}
