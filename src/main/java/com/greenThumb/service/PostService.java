package com.greenThumb.service;

import com.greenThumb.domain.Post;
import com.greenThumb.domain.User;
import com.greenThumb.dto.request.PostRequestDto;
import com.greenThumb.dto.response.PostResponseDto;
import com.greenThumb.repository.PostRepository;
import com.greenThumb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * 게시글 생성
     */
    @Transactional
    public Long save(String username, PostRequestDto requestDto) {

        User user = userRepository.findByUsername(username).get();
        requestDto.setUser(user);
        Post post = postRepository.save(requestDto.toEntity());
        return post.getId();
    }

    /**
     * 게시판 전체 조회
     */
    public List<PostResponseDto> findAll() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postDtos = new ArrayList<>();
        posts.forEach(s -> postDtos.add(new PostResponseDto(s)));
        return postDtos;
    }

    /**
     * 게시글 id로 조회
     */
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        return new PostResponseDto(post);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Long update(Long id, String username, PostRequestDto requestDto) {

        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        User user = userRepository.findByUsername(username).get();

        post.update(requestDto.getTitle(), requestDto.getContent(), post.getCategory(), requestDto.getFileId(), user);
        return id;
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

}
