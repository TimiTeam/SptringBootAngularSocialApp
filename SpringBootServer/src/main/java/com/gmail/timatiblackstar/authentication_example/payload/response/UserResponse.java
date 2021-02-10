package com.gmail.timatiblackstar.authentication_example.payload.response;

public class UserResponse {
    private Long userId;
    private String firstName;

    public UserResponse() {
    }

    public UserResponse(Long userId, String firstName) {
        this.userId = userId;
        this.firstName = firstName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}