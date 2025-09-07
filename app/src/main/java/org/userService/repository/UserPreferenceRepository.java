package org.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.userService.entities.UserPreference;

import java.util.Optional;

public interface UserPreferenceRepository extends JpaRepository<UserPreference,Long> {
    Optional<UserPreference> findByUserEmail(String email);
}
