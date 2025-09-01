package org.userService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.userService.dto.UpdateUserRequest;
import org.userService.repository.UserRepository;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @PutMapping("/update")
    public ResponseEntity<?> updateUserProfile(
            @RequestBody UpdateUserRequest request,
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
}
