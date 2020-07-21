package com.app.manager.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    //    @RequestMapping("/search")
    public String index() {
        return "search";
    }
}
