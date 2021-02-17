package com.gmail.timatiblackstar.authentication_example.payload.response;

public class UserResponse {
    private Long userId;
    private String firstName;
    private String sex;
    private String imageURL;

    public UserResponse() {
    }

    public UserResponse(Long userId, String firstName, String sex, String imageURL) {
        this.userId = userId;
        this.firstName = firstName;
        this.sex = sex;
        this.imageURL = imageURL;
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
}