package com.example.securitydemo.Service;

import com.example.securitydemo.Dao.RoleRepository;
import com.example.securitydemo.Dao.UserRepository;
import com.example.securitydemo.Entity.Role;
import com.example.securitydemo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("myUserService")
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username);
        Set<? extends GrantedAuthority> auths = getAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), auths);
    }

    public Set<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        return authorities;
    }
}
