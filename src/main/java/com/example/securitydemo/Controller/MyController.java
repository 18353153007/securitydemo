package com.example.securitydemo.Controller;

import com.sun.deploy.net.HttpResponse;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Collection;

@Controller
@RequestMapping("/lcc")
public class MyController {
    @RequestMapping("test1")
    @ResponseBody
    public String method1() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object cri = SecurityContextHolder.getContext().getAuthentication().getCredentials();

        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return "success";
    }

    @RequestMapping("test2")
    @ResponseBody
    public String method2(@RequestParam(value = "error", required = false) String error, Model model) {
        System.out.println("**************test2*****************");
        return "success test2";
    }

    @RequestMapping("test3")
    public void method3(@RequestParam(value = "error", required = false) String error, Model model) {
        System.out.println("**************test3*****************");

    }

    @RequestMapping(value = "test4",method = RequestMethod.POST)
    public void method4(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("**************test3*****************");

    }

}
