package com.cy.bean;

import org.springframework.stereotype.Component;


public class User {
    private int id;
    private String username;
    private String userpassword;
    private Boolean state;
    private String email;
    private String role;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userpassword='" + userpassword + '\'' +
                ", state=" + state +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User() {
    }

    public User(String username, String userpassword) {
        this.username = username;
        this.userpassword = userpassword;
    }

    public User(String username, String userpassword, Boolean state, String email, String role) {
        this.username = username;
        this.userpassword = userpassword;
        this.state = state;
        this.email = email;
        this.role = role;
    }
}
