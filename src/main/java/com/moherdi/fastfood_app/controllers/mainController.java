package com.moherdi.fastfood_app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainController {

    @GetMapping(value = {"/", "/home","/inicio"})
    public String inicio() {
        return new String("inicio");
    }

}
