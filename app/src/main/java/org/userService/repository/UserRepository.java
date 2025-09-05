package org.userService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.userService.entities.UserInfo;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserInfo,String> {
    Optional<UserInfo> findByUserId(String userId);
    Optional<UserInfo> findByEmail(String email);
}
