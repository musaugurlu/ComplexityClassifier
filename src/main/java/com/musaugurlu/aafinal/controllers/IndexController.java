package com.musaugurlu.aafinal.controllers;

import com.musaugurlu.aafinal.model.Code;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    @GetMapping
    public String getIndex(Model model){
        model.addAttribute("code", new Code());
        return "index";
    }

    @PostMapping
    public String postIndex(@ModelAttribute Code code, Model model) {
        model.addAttribute("code", code);
        return "index";
    }
}
