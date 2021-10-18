package com.sid.repositories;

import com.sid.entities.AppRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<AppRole, String> {

    Optional<AppRole> findByRoleNameEquals(@Param(value = "roleName") String roleName);

    boolean existsByRoleNameEquals(String name);

}
