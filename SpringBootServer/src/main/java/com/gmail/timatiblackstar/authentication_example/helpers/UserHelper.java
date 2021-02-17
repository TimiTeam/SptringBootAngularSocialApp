package com.gmail.timatiblackstar.authentication_example.helpers;

import com.gmail.timatiblackstar.authentication_example.models.*;
import com.gmail.timatiblackstar.authentication_example.payload.request.SignupRequest;
import com.gmail.timatiblackstar.authentication_example.repository.RoleRepository;
import com.gmail.timatiblackstar.authentication_example.repository.SexRepository;
import com.gmail.timatiblackstar.authentication_example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Scope(value = "singleton")
public class UserHelper {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private SexRepository sexRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setSexRepository(SexRepository sexRepository) {
        this.sexRepository = sexRepository;
    }

    public User getCurrentUser(){
        var userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User "+userDetails.getUsername()+" not found"));
    }

    private Set<Role> getRolesFromSignupRequest(SignupRequest signupRequest){
        var strRoles = signupRequest.getRoles();
        var roles = new HashSet<Role>();

        if (strRoles == null){
            Role role = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role user is not found."));
            roles.add(role);
        }else {
            strRoles.forEach(role ->{
                switch (role){
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role admin is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role mod is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role user is not found."));
                        roles.add(userRole);
                }
            });
        }

        return roles;
    }

    private Sex getSexFromSignupRequest(SignupRequest signupRequest){
        var strSex = signupRequest.getSex();
        Sex result = null;
        switch (strSex){
            case "male":
                result = sexRepository.findByName(ESex.SEX_MALE)
                        .orElseThrow(() -> new RuntimeException("Error: Sex male not found"));
                break;
            case "female":
                result = sexRepository.findByName(ESex.SEX_FEMALE)
                        .orElseThrow(() -> new RuntimeException("Error: Sex female not found"));
                break;
            case "trans":
                result = sexRepository.findByName(ESex.SEX_TRANSSEXUAL)
                        .orElseThrow(() -> new RuntimeException("Error: Sex trans not found"));
                break;
            default:
                break;
        }
        return result;
    }

    private String getDefaultImageURL(ESex userSex){
        String result = "";
        switch (userSex){
            case SEX_MALE:
                result = "http://s3.amazonaws.com/37assets/svn/765-default-avatar.png";
                break;
            case SEX_FEMALE:
                result = "https://st2.depositphotos.com/4111759/12123/v/600/depositphotos_121233300-stock-illustration-female-default-avatar-gray-profile.jpg";
                break;
            case SEX_TRANSSEXUAL:
                result = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGxyWuRUC1WNfeRK5I5SfDKIyr-4bqp64dsg&usqp=CAU";
                break;
        }
        return result;
    }

    public User buildUserFromSignupRequest(SignupRequest signupRequest){
        var user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        user.setRoles(getRolesFromSignupRequest(signupRequest));

        user.setSex(getSexFromSignupRequest(signupRequest));

        user.setImageURL(getDefaultImageURL(user.getSex().getName()));

        return user;
    }
}
