package com.greenThumb.controller;

import com.greenThumb.domain.Post;
import com.greenThumb.dto.FileDto;
import com.greenThumb.dto.request.CommentRequestDto;
import com.greenThumb.dto.request.PostRequestDto;
import com.greenThumb.dto.response.CommentResponseDto;
import com.greenThumb.dto.response.PostResponseDto;
import com.greenThumb.dto.response.UserResponseDto;
import com.greenThumb.service.CommentService;
import com.greenThumb.service.FileService;
import com.greenThumb.service.PostService;
import com.greenThumb.util.MD5Generator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final FileService fileService;
    private final CommentService commentService;

    private final HttpSession session;


    /*
    질문 게시판
     */
    @GetMapping("/questionList")
    public String questionList(Model model) {

        UserResponseDto user = (UserResponseDto) session.getAttribute("user");

        if (user!=null) {
            model.addAttribute("user", user.getUsername());
        }

        model.addAttribute("list", postService.findAll());
        return "questionList";
    }

    /*
    나눔 게시판
     */
    @GetMapping("/sharingList")
    public String sharingList(Model model) {

        UserResponseDto user = (UserResponseDto) session.getAttribute("user");

        if (user!=null) {
            model.addAttribute("user", user.getUsername());
        }

        model.addAttribute("list", postService.findAll());
        return "sharingList";
    }

    @GetMapping("/post/{postId}")
    public String postView(@PathVariable Long postId, Model model){

        PostResponseDto response = postService.findById(postId);
        List<CommentResponseDto> comments = response.getComments();

        if (comments != null && !comments.isEmpty()) {
            model.addAttribute("comments", comments);
        }

        UserResponseDto user = (UserResponseDto) session.getAttribute("user");

        if (user!=null) {
            model.addAttribute("user", user.getUsername());

//            if (response.getUser().getId().equals(user.getId())) {
//                model.addAttribute("writer", true);
//            }
        }

        if(response.getFileId()!=null){
            FileDto fileDto = fileService.getFile(response.getFileId());
            model.addAttribute("file", fileDto);
        }

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
    public String register(@ModelAttribute PostRequestDto postRequestDto, Model model){  // 빈 오브젝트 만들어서 뷰에 전달
        UserResponseDto user = (UserResponseDto) session.getAttribute("user");

        if (user!=null) {
            model.addAttribute("user", user.getUsername());
        }

        return "registerForm";
    }

    @PostMapping("/register")
    public String post(@RequestParam("file") MultipartFile files, @Valid PostRequestDto postRequestDto, BindingResult bindingResult) {

        UserResponseDto user = (UserResponseDto) session.getAttribute("user");

        if (bindingResult.hasErrors()){
            log.info("errors = {}", bindingResult);
            return "registerForm";
        }

        try {
            String origFilename = files.getOriginalFilename();
            String filename = new MD5Generator(origFilename).toString();
            /* 실행되는 위치의 'files' 폴더에 파일이 저장됩니다. */
            String savePath = System.getProperty("user.dir") + "\\files";
            /* 파일이 저장되는 폴더가 없으면 폴더를 생성합니다. */
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }

            String filePath = savePath + "\\" + filename;
            files.transferTo(new File(filePath));

            FileDto fileDto = new FileDto();
            fileDto.setOrigFilename(origFilename);
            fileDto.setFilename(filename);
            fileDto.setFilePath(filePath);

            Long fileId = fileService.saveFile(fileDto);
            postRequestDto.setFileId(fileId);

            postService.save(user.getUsername(), postRequestDto);

        } catch (Exception e){
            e.printStackTrace();

        }

        if(postRequestDto.getCategory().equals("질문게시판")){
            return "redirect:/questionList";
        }

        else{
            return "redirect:/sharingList";
        }

    }

    /**
     * 게시글 수정
     */
    @GetMapping("/post/{postId}/update")
    public String update(@PathVariable Long postId, Model model) {

        UserResponseDto user = (UserResponseDto) session.getAttribute("user");

        if (user!=null) {
            model.addAttribute("user", user.getUsername());
        }

        PostResponseDto response = postService.findById(postId);

        if(response.getFileId()!=null){
            FileDto fileDto = fileService.getFile(response.getFileId());
            model.addAttribute("file", fileDto);
        }

        model.addAttribute("postForm", response);
        model.addAttribute("postId", postId);

        return "editForm";

    }

    @PostMapping("/post/{postId}/update")
    public String update(@PathVariable Long postId, @Valid @ModelAttribute PostRequestDto request, BindingResult bindingResult){

        UserResponseDto user = (UserResponseDto) session.getAttribute("user");

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "editForm";
        }

        PostResponseDto post = postService.findById(postId);

        if(post.getFileId()!=null){
            request.setFileId(post.getFileId());
        }
        postService.update(postId, user.getUsername(), request);
        return "redirect:/post/{postId}";
    }

    /**
     * 
     * 게시글 삭제
     */
    @GetMapping("/post/{postId}/delete")
    public String delete(@PathVariable Long postId) {

        PostResponseDto post = postService.findById(postId);
        String category = post.getCategory();

        if(category.equals("질문게시판")){
            postService.deleteById(postId);
            return "redirect:/questionList";
        }

        else{
            postService.deleteById(postId);
            return "redirect:/sharingList";
        }
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("fileId") Long fileId) throws IOException {
        FileDto fileDto = fileService.getFile(fileId);
        Path path = Paths.get(fileDto.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDto.getOrigFilename() + "\"")
                .body(resource);
    }


    @PostMapping("/comment/create/{postId}")
    public String commentSave(@PathVariable Long postId, CommentRequestDto dto) {


        UserResponseDto user = (UserResponseDto) session.getAttribute("user");
        commentService.commentSave(user.getUsername(), postId, dto);

        return "redirect:/post/{postId}";

    }

    @GetMapping("/post/search")
    public String search(String keyword, Model model) {
        List<Post> searchList = postService.search(keyword);
        model.addAttribute("searchList", searchList);

        UserResponseDto user = (UserResponseDto) session.getAttribute("user");

        if (user!=null) {
            model.addAttribute("user", user.getUsername());
        }

        return "post-search";
    }
}

