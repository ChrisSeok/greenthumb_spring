package com.greenThumb.controller;

import com.greenThumb.domain.New;
import com.greenThumb.dto.response.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/receive-json")
    public String receiveJson(@RequestBody New data) {
        // Handle the JSON data here
        // You can parse jsonData as needed
        return data.toString();
    }
}