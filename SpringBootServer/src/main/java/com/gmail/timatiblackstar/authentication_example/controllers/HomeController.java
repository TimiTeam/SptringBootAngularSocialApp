package com.gmail.timatiblackstar.authentication_example.controllers;

import com.gmail.timatiblackstar.authentication_example.helpers.UserHelper;
import com.gmail.timatiblackstar.authentication_example.models.ESex;
import com.gmail.timatiblackstar.authentication_example.models.Sex;
import com.gmail.timatiblackstar.authentication_example.payload.response.UserResponse;
import com.gmail.timatiblackstar.authentication_example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class HomeController {
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

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/allUsers")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        var currentUser = userHelper.getCurrentUser();
        var allUsers = userRepository.findAll().stream()
                .filter((u) -> !u.equals(currentUser))
                .map((u) -> new UserResponse(u.getId(), u.getUsername(), (u.getSex() != null ? u.getSex().getName().name() : "empty"), u.getImageURL()))
                .collect(Collectors.toSet());
        allUsers.remove(currentUser);
        return ResponseEntity.ok(allUsers);
    }
}
