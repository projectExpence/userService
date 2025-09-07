package org.userService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.userService.dto.UserPreferenceDto;
import org.userService.service.UserPreferenceService;

@RestController
@RequestMapping("/user/preference")
public class UserPreferenceController {

    @Autowired
    UserPreferenceService userPreferenceService;

    @GetMapping
    public ResponseEntity<?> getPreference (Authentication authentication){
        String email = authentication.getName();
        UserPreferenceDto dto = userPreferenceService.preferenceDto(email);
        return ResponseEntity.ok(dto);
    }
    @PostMapping
    public ResponseEntity<?> updatePreference (Authentication authentication,
                                                               @RequestBody UserPreferenceDto dto){
        String email = authentication.getName();
        UserPreferenceDto update = userPreferenceService.updatePreferenceDto(email,dto);
        return ResponseEntity.ok().body("Thank you for sharing");

    }
}
