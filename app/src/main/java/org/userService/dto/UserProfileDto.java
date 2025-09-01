package org.userService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserProfileDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String profilePictureUrl;
}
