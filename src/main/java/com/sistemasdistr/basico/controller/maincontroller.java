package com.sistemasdistr.basico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class maincontroller {
    @GetMapping("/")
    public String visitaHome( ModelMap interfazConPantalla){
        return "index";
    }
}
