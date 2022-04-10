package com.project.nsbet.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping("/error")
    public String handle() {
        return "404";
    }

    public String getErrorPath() {
        return PATH;
    }
}
