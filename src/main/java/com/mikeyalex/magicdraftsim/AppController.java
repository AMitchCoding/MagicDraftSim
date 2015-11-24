package com.mikeyalex.magicdraftsim;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class AppController {

    @RequestMapping("/")
    public String main(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model){
        model.addAttribute("name", name);
        return "main";
    }
}