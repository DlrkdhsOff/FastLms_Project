package com.zero.fastlms.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMainController {

    @GetMapping("/admin/main.do")
    public String adminMain() {
        return "admin/main";
    }
}
