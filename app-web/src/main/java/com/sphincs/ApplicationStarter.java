package com.sphincs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
@RequestMapping("/")
public class ApplicationStarter {

    public static void main(String[] args) {

        SpringApplication.run(ApplicationStarter.class, args);

    }

}
