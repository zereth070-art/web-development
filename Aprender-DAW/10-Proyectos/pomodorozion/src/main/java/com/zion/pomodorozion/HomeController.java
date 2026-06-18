package com.zion.pomodorozion;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {
    
    @GetMapping("/status")    
    public String status() {
        return "PomodoroZion API is running!";
    }

    @GetMapping("/version")
    public String version() {
        return "PomodoroZion API v1.0";
    }   
}
