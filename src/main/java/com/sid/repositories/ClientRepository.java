package com.sid.repositories;

import com.sid.entities.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends MongoRepository<Client, String> {

    Client findByCodeEquals(@Param("code") String code);

    Client findByTelephoneEquals(@Param("telephone") String telephone);

    Client findByEmailEquals(@Param("email") String email);

}
