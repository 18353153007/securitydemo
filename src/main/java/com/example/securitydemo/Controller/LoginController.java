package com.example.securitydemo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
public class LoginController
{
    @RequestMapping("/login")
    public String login(Model model)
    {
        return "/sys/login";
    }

    @RequestMapping("/lcc/login")
    public String loginlcc(Model model)
    {


            return "/sys/login";
    }

    @RequestMapping("/hello")
    public String helloHtml(HashMap<String, Object> map) {
        map.put("hello", "欢迎进入HTML页面");
        return "/login";

    }


}
