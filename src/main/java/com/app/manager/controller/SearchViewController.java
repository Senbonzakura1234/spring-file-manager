package com.app.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchViewController {
    @GetMapping("/api/search")
    public String index() {
        return "views/search";
    }
}
