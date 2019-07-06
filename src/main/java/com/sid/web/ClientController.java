package com.sid.web;

import com.sid.entities.Client;
import com.sid.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public Page<Client> getClients(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "10") int size,
                                   @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        return clientService.getClients(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction.toUpperCase()),
                "lastName", "firstName")));
    }

    @GetMapping(value = "/{code}")
    public Client getClient(@PathVariable String code) {
        return clientService.getClientByCode(code);
    }

    @PostMapping
    public Client addClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @PutMapping
    public Client updateClient(@RequestBody Client client) {
        return clientService.updateClient(client);
    }

    @DeleteMapping(value = "/{code}")
    public void deleteClient(@PathVariable String code) {
        clientService.deleteClient(code);
    }
}
