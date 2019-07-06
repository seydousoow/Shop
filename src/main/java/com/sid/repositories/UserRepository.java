package com.sid.repositories;

import com.sid.entities.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends MongoRepository<AppUser, String> {

    AppUser findByUsernameEquals(@Param(value = "username") String username);
    AppUser findByUserIdEquals(@Param(value = "user_id") String user_id);
}
