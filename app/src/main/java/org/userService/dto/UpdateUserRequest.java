package org.userService.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String profilePictureUrl;
}
