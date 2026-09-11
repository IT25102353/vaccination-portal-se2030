package com.se2030.vaccination_portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String landing() {
        return "landing";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
