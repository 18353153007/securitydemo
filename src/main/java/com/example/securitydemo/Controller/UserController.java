package com.example.securitydemo.Controller;

import com.example.securitydemo.Dao.RoleRepository;
import com.example.securitydemo.Dao.UserRepository;
import com.example.securitydemo.Entity.Role;
import com.example.securitydemo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/insert")
    @ResponseBody
    public String insertUsers() {


        Role role = new Role("ROLE_ADMIN");
        roleRepository.save(role);

        User user = new User("user",passwordEncoder.encode("123456"),role);
        userRepository.save(user);
        return "insert success";


    }

    @RequestMapping("/insert2")
    @ResponseBody
    public String insertUsers2() {


        Role role = new Role("ROLE_PRO");
        roleRepository.save(role);

        User user = new User("pro",passwordEncoder.encode("123456"),role);
        userRepository.save(user);
        return "insert success";


    }
}
