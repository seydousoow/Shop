package com.sid.service;

import com.sid.entities.Client;
import com.sid.repositories.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Page<Client> getClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public Client getClientByCode(String code) {
        Client c = clientRepository.findByCodeEquals(code);
        if (c == null) {
            throw new RuntimeException("There is no client registered with this code!");
        }
        return c;
    }

    @Override
    public Client addClient(Client client) {
        /*
         * capitalize firstName and lastName
         */
        if (client.getEmail().length() > 0 && clientRepository.findByEmailEquals(client.getEmail()) != null)
            throw new RuntimeException("This email is already used by another user");

        if (clientRepository.findByTelephoneEquals(client.getTelephone()) != null)
            throw new RuntimeException("This phone number is already used by another user");

        client.setFirstName(client.getFirstName().substring(0, 1).toUpperCase() + client.getFirstName().substring(1));
        client.setLastName(client.getLastName().substring(0, 1).toUpperCase() + client.getLastName().substring(1));

        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(Client client) {
        Client client1 = clientRepository.findByCodeEquals(client.getCode());
        if (client1 == null)
            throw new RuntimeException("There is no client registered with this code!");
        if (client.getEmail().length() > 0 && clientRepository.findByEmailEquals(client.getEmail()) != null)
            throw new RuntimeException("This email is already used by another user");

        if (clientRepository.findByTelephoneEquals(client.getTelephone()) != null)
            throw new RuntimeException("This phone number is already used by another user");

        client.setIdClient(client1.getIdClient());
        return addClient(client);
    }

    @Override
    public void deleteClient(String code) {
        Client client = clientRepository.findByCodeEquals(code);
        if (client == null)
            throw new RuntimeException("There is no client registered with this code!");
        else
            clientRepository.delete(client);
    }

}
