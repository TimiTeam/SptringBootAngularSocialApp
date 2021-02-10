package com.gmail.timatiblackstar.authentication_example.controllers;

import com.gmail.timatiblackstar.authentication_example.helpers.UserHelper;
import com.gmail.timatiblackstar.authentication_example.payload.request.AddToFriendRequest;
import com.gmail.timatiblackstar.authentication_example.payload.response.UserResponse;
import com.gmail.timatiblackstar.authentication_example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/home")
public class HomePageController {

    private UserRepository userRepository;

    private UserHelper userHelper;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserHelper(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    @GetMapping("/friends")
    @PreAuthorize("hasRole('USER') || hasRole('MODERATOR')")
    public ResponseEntity<?> getAllFriends(@RequestParam Long userId){
        List<UserResponse> friends = new java.util.ArrayList<>(Collections.emptyList());
        var currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User withe id: "+userId+" Not found"));
        friends.addAll(currentUser.getFriends()
                .stream().map((u) -> new UserResponse(u.getId(), u.getUsername()))
                .collect(Collectors.toSet()));
        return ResponseEntity.ok(friends);
    }

    @PostMapping("/addToFried")
    @PreAuthorize("hasRole('USER') || hasRole('MODERATOR')")
    public ResponseEntity<?> addToFriend(@Valid @RequestBody AddToFriendRequest friendRequest){
        var cuser = userHelper.getCurrentUser();
        var friend = userRepository.findById(friendRequest.getFriendId())
                .orElseThrow(() -> new RuntimeException("User withe id: "+friendRequest.getFriendId()+" Not found"));
        var result = cuser.addToFriends(friend);
        userRepository.save(cuser);
        return ResponseEntity.ok(result ?
                "User "+friend.getUsername()+" added to "+cuser.getUsername()+" friends successfully" :
                "Error adding friend");
    }

    @PostMapping("/removeFriend")
    @PreAuthorize("hasRole('USER') || hasRole('MODERATOR')")
    public ResponseEntity<?> removeFriend(@Valid @RequestBody AddToFriendRequest friendRequest){
        var cuser = userHelper.getCurrentUser();
        var friend = userRepository.findById(friendRequest.getFriendId())
                .orElseThrow(() -> new RuntimeException("User withe id: "+friendRequest.getFriendId()+" Not found"));
        var result = cuser.removeFriend(friend);
        userRepository.save(cuser);
            return ResponseEntity.ok(result ?
                "User "+friend.getUsername()+" removed from "+cuser.getUsername()+"s friends successfully" :
                "Error removing friend");
    }
}