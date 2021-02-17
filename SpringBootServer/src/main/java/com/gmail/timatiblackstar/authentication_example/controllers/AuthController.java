package com.gmail.timatiblackstar.authentication_example.controllers;

import com.gmail.timatiblackstar.authentication_example.helpers.UserHelper;
import com.gmail.timatiblackstar.authentication_example.models.ERole;
import com.gmail.timatiblackstar.authentication_example.models.ESex;
import com.gmail.timatiblackstar.authentication_example.models.Role;
import com.gmail.timatiblackstar.authentication_example.models.User;
import com.gmail.timatiblackstar.authentication_example.payload.request.LoginRequest;
import com.gmail.timatiblackstar.authentication_example.payload.request.SignupRequest;
import com.gmail.timatiblackstar.authentication_example.payload.response.JwtResponse;
import com.gmail.timatiblackstar.authentication_example.payload.response.MessageResponse;
import com.gmail.timatiblackstar.authentication_example.repository.RoleRepository;
import com.gmail.timatiblackstar.authentication_example.repository.UserRepository;
import com.gmail.timatiblackstar.authentication_example.security.jwt.JwtUtils;
import com.gmail.timatiblackstar.authentication_example.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;

    private UserRepository userRepository;

    private JwtUtils jwtUtils;

    private UserHelper userHelper;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setJwtUtils(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Autowired
    public void setUserHelper(UserHelper userHelper) {
        this.userHelper = userHelper;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var jwt = jwtUtils.generateJwtToken(authentication);

        var userDetails = (UserDetailsImpl) authentication.getPrincipal();

        var roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        var curUser = userHelper.getCurrentUser();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                curUser.getImageURL(),
                curUser.getSex().getName().name(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        if (userRepository.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        var user = userHelper.buildUserFromSignupRequest(signupRequest);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
