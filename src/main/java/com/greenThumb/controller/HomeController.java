package com.greenThumb.controller;

import com.greenThumb.dto.UserResponseDto;
import com.greenThumb.dto.request.UserRequestDto;
import com.greenThumb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final HttpSession session;


    @GetMapping("/")
    public String home(Model model) {

        UserResponseDto user = (UserResponseDto) session.getAttribute("user");

        if (user!=null) {
            model.addAttribute("user", user.getUsername());
        }
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("userRequestDto", new UserRequestDto());
        return "join";
    }

    
    /*
    * 회원가입
     */
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute UserRequestDto userRequestDto, BindingResult result, Model model) {

        /* 회원가입 실패 시 입력 데이터 값들을 유지함 */
        if (result.hasErrors()) {
//            model.addAttribute("userDto", dto);
//
//
//            Map<String, String> validatorResult = userService.validateHandling(errors);
//            for (String key : validatorResult.keySet()) {
//                model.addAttribute(key, validatorResult.get(key));
//            }
            return "join";
        }

        userService.checkUsernameDuplication(userRequestDto);

        userService.join(userRequestDto);
        return "redirect:/";
    }
}
