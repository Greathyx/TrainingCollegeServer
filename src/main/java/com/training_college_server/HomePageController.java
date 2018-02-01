package com.training_college_server;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePageController {

    @RequestMapping(value = "/home")
    public String hello() {
        return "hello,Spring Boot";
    }

}
