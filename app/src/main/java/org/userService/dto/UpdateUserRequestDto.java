package org.userService.dto;

import lombok.Data;

@Data
public class UpdateUserRequestDto {
    private String firstName;
    private String lastName;
    private String username;
    private String profilePictureUrl;
}
