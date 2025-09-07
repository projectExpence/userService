package org.userService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.userService.dto.UpdateUserRequestDto;
import org.userService.dto.UserProfileDto;
import org.userService.repository.UserRepository;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @PutMapping("/update")
    public ResponseEntity<?> updateUserProfile(
            @RequestBody UpdateUserRequestDto request,
            Authentication authentication
            ){
        String email = (String) authentication.getPrincipal();

        return userRepository.findByEmail(email)
                .map(user -> {
                    if (request.getFirstName() != null) {
                        user.setFirstName(request.getFirstName());
                    }
                    if (request.getLastName() != null) {
                        user.setLastName(request.getLastName());
                    }
                    if (request.getUsername() != null) {
                        user.setUsername(request.getUsername());
                    }
                    if (request.getProfilePictureUrl() != null) {
                        user.setProfilePictureUrl(request.getProfilePictureUrl());
                    }

                    userRepository.save(user);
                    return ResponseEntity.ok("Profile updated successfully ✅");
                })
                .orElse(ResponseEntity.notFound().build());


    }
    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(Authentication authentication){
        String email =(String) authentication.getPrincipal();

        return userRepository.findByEmail(email)
                .map(user->{
                    UserProfileDto profile = UserProfileDto.builder()
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .username(user.getUsername())
                            .profilePictureUrl(user.getProfilePictureUrl())
                            .build();
                    return ResponseEntity.ok(profile);

                }). orElse(ResponseEntity.notFound().build());
    }
}
