package com.greenThumb.controller;

import com.greenThumb.dto.UserResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class PlantController {

    private final HttpSession session;

    @GetMapping("/plant")
    public String plant(Model model){

        UserResponseDto user = (UserResponseDto) session.getAttribute("user");

        if (user!=null) {
            model.addAttribute("user", user.getUsername());
        }

        return "plant";
    }
}
