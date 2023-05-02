package com.greenThumb.controller;

import com.greenThumb.request.PostRequestDto;
import com.greenThumb.response.PostResponseDto;
import com.greenThumb.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/postList")
    public String postList(Model model){
        model.addAttribute("list", postService.findAll());
        return "postList";
    }

    /*
    질문 게시판
     */
    @GetMapping("/questionList")
    public String questionList(Model model) {
        model.addAttribute("list", postService.findAll());
        return "questionList";
    }

    /*
    나눔 게시판
     */
    @GetMapping("/sharingList")
    public String sharingList(Model model) {
        model.addAttribute("list", postService.findAll());
        return "sharingList";
    }

    @GetMapping("/post/{postId}")
    public String postView(@PathVariable Long postId, Model model){
        PostResponseDto response = postService.findById(postId);
        model.addAttribute("post", response);
        return "post";
    }


    /**
     *
     * modelAttribute -> 파라미터로 넘겨준 타입의 오브젝트를 자동으로 생성
     * 생성된 오브젝트 http로 넘어온 값들을 자동으로 바인딩
     * 객체가 자동으로 model 객체에 추가되고 뷰로 전달됨됨
     * */
    @GetMapping("/register")
    public String register(@ModelAttribute PostRequestDto postRequestDto){  // 빈 오브젝트 만들어서 뷰에 전달
        return "registerForm";
    }

    @PostMapping("/register")
    public String post(@Valid PostRequestDto postRequestDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "registerForm";
        }

        postService.save(postRequestDto);
        return "redirect:/postList";

    }

    /**
     * 게시글 수정
     */
    @GetMapping("/post/{postId}/update")
    public String update(@PathVariable Long postId, Model model) {

        PostResponseDto response = postService.findById(postId);
        model.addAttribute("postForm", response);
        model.addAttribute("postId", postId);

        return "editForm";

    }

    @PostMapping("/post/{postId}/update")
    public String update(@PathVariable Long postId, @Valid @ModelAttribute PostRequestDto request, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "editForm";
        }

        postService.update(postId, request);
        return "redirect:/post/{postId}";
    }

    /**
     * 
     * 게시글 삭제
     */
    @GetMapping("/post/{postId}/delete")
    public String delete(@PathVariable Long postId) {
        postService.deleteById(postId);
        return "redirect:/postList";
    }
    

}

