package com.sphincs.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloPage {

    private static final Logger LOGGER = LogManager.getLogger();

    @RequestMapping("/")
    public String init() {
        return "helloPage";
    }
}
