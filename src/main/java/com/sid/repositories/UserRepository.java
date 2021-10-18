package com.sid.repositories;

import com.sid.entities.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends MongoRepository<AppUser, String> {

    Optional<AppUser> findByUsernameEquals(@Param(value = "username") String username);

    boolean existsByUsernameEquals(String username);

}
