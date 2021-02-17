package com.gmail.timatiblackstar.authentication_example.payload.request;

import java.util.List;

public class SignupRequest {
    private String username;
    private String sex;
    private String email;
    private String password;
    private List<String> roles;

    public SignupRequest() {
    }

    public SignupRequest(String username, String sex, String email, String password, List<String> roles) {
        this.username = username;
        this.sex = sex;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
