package com.bcaf.tama.FinalProject.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class RegisterController {

    @GetMapping
    @RequestMapping({"/register"})
    public String main() {
        return "register";
    }
}
