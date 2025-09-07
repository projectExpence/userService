package org.userService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.userService.Enums.*;
import org.userService.dto.UserPreferenceDto;
import org.userService.entities.UserInfo;
import org.userService.entities.UserPreference;
import org.userService.repository.UserPreferenceRepository;
import org.userService.repository.UserRepository;

import java.util.Collections;

@Service
public class UserPreferenceService {
    @Autowired
    UserPreferenceRepository userPreferenceRepository;
    @Autowired
    UserRepository userRepository;

    public UserPreferenceDto preferenceDto (String email){
        return userPreferenceRepository.findByUserEmail(email).map(
                pref-> {
                    UserPreferenceDto dto = new UserPreferenceDto();
                    dto.setGender(pref.getGender() != null ? pref.getGender().name() : null);
                    dto.setOccupation(pref.getOccupation() != null ? pref.getOccupation().name() : null);
                    dto.setField(pref.getField());
                    dto.setIncomeLevel(pref.getIncomeLevel() != null ? pref.getIncomeLevel().name() : null);
                    dto.setGoal(pref.getGoal() != null ? pref.getGoal().name() : null);
                    dto.setRiskTolerance(pref.getRiskTolerance() != null ? pref.getRiskTolerance().name() : null);
                    dto.setHobbies(Collections.singletonList(pref.getHobbies()));
                    return dto;

                }).orElse(null);
    }
    public UserPreferenceDto updatePreferenceDto(String email, UserPreferenceDto dto){
        UserInfo user= userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("User not Found"));
        UserPreference pref = userPreferenceRepository.findByUserEmail(email).orElse(UserPreference.builder().user(user).build());

        if(dto.getGender()!= null ){
            pref.setGender(Gender.valueOf(dto.getGender()));
        }
        if(dto.getOccupation()!= null){
            pref.setOccupation(Occupation.valueOf(dto.getOccupation()));
        }
        if(dto.getField()!=null){
            pref.setField(dto.getField());
        }
        if (dto.getIncomeLevel() != null) pref.setIncomeLevel(IncomeLevel.valueOf(dto.getIncomeLevel()));
        if (dto.getGoal() != null) pref.setGoal(Goal.valueOf(dto.getGoal()));
        if (dto.getRiskTolerance() != null) pref.setRiskTolerance(RiskTolerance.valueOf(dto.getRiskTolerance()));
        if(dto.getHobbies() !=null && !dto.getHobbies().isEmpty()){
            pref.setHobbies(String.valueOf(dto.getHobbies()));
        }
        userPreferenceRepository.save(pref);
        return dto;
    }
}
