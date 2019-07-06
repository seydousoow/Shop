package com.sid.repositories;

import com.sid.entities.AppRole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RoleRepository extends MongoRepository<AppRole, String> {

    AppRole findByRoleNameEquals(@Param(value = "roleName") String roleName);

    AppRole findByRoleIdEquals(@Param("role_id") String role_id);
}
