package com.gmail.timatiblackstar.authentication_example.payload.request;

public class AddToFriendRequest {
    private Long userId;
    private Long friendId;

    public AddToFriendRequest() {
    }

    public AddToFriendRequest(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendIf) {
        this.friendId = friendIf;
    }
}
