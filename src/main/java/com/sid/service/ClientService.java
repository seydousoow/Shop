package com.sid.service;

import com.sid.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    Page<Client> getClients(Pageable pageable);
    Client getClientByCode(String code);
    Client addClient(Client client);
    Client updateClient(Client client);
    void deleteClient(String code);
}
