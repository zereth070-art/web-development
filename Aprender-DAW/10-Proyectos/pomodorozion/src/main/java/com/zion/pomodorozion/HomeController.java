package com.zion.pomodorozion;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "PomodoroZion esta funcionando!";
    }

    @GetMapping("/hola")    
    public String home() {
        return "Hola PomodoroZion!";
    }
}
