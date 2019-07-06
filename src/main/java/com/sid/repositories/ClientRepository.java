package com.sid.repositories;

import com.sid.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientRepository extends MongoRepository<Client, String> {

    Client findByCodeEquals(@Param("code") String code);
    Client findByTelephoneEquals(@Param("telephone") String telephone);
    Client findByEmailEquals(@Param("email") String email);

    @Override
    Page<Client> findAll(Pageable pageable);
}
