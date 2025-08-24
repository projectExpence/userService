package org.userService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.userService.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,String> {
    UserEntity findByUserId(String userId);
}
