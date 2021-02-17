package com.gmail.timatiblackstar.authentication_example.payload.response;

import java.util.List;

public class JwtResponse {
    private String token;
    private Long userId;
    private String username;
    private String imageURL;
    private String sex;
    private String email;
    private List<String> roles;

    public JwtResponse() {
    }

    public JwtResponse(String token, Long userId, String username, String imageURL, String sex, String email, List<String> roles) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.imageURL = imageURL;
        this.sex = sex;
        this.email = email;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
