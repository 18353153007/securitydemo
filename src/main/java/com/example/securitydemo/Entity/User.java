package com.example.securitydemo.Entity;

import javax.persistence.*;
import java.util.*;

@Entity(name = "user")
public class User{
    @Id
    @GeneratedValue
    private int userId;
    @Column
    private String username;

    @Column
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @OrderBy("roleName ASC")
    private Set<Role> roles;
    public User() {}
    public User(String username, String password, Role role) {
        this.password = password;
        this.username = username;
        if (roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role) ;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
