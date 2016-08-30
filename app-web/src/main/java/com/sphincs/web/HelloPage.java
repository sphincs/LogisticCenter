package com.sphincs.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HelloPage {

    @RequestMapping("/")
    public String init() {
        return "helloPage";
    }

    @RequestMapping(value = "/onepage")
    public String onepage() {
        return "onepage";
    }

    @RequestMapping(value = "/jsp")
    public String jsp() {
        return "jsp";
    }

}
