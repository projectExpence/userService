package org.userService.dto;

import lombok.Data;

@Data
public class UserPreferenceDto {
    private String gender;    // enum
    private String occupation; // it's an enum STUDENT, EMPLOYEE, etc
    private String field;      //  developer , HR , doctor etc;
    private String incomeLevel; // enum eg: LOW,MED,HIGH
    private String goal; // enum eg: SAVE, TRACK MONEY
    private String riskTolerance; // enum eg: LOW , MED ,HIGH

}
