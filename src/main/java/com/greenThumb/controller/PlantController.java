package com.greenThumb.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlantController {

    @GetMapping("/plant")
    public String plant(){
        return "plant";
    }
}
