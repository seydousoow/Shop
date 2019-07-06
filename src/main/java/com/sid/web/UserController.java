package com.sid.web;

import com.sid.entities.AppUser;
import com.sid.service.CredentialsService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final CredentialsService credentialsService;

    public UserController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @GetMapping
    public Page<AppUser> getUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "24") int size,
                                  @RequestParam(name = "sort", defaultValue = "username") String sort,
                                  @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
        return credentialsService.getUsers(page, size, sort, direction);
    }

    @GetMapping("/{username}")
    public AppUser getUserByUsername(@PathVariable("username") String username) {
        return credentialsService.getByUsername(new String(Base64.getDecoder().decode(username)));
    }

    @PostMapping
    public AppUser saveUser(@RequestBody AppUser user) {
        return credentialsService.saveUser(user);
    }

    @PutMapping
    public AppUser updateUser(@RequestBody AppUser user) {
        return credentialsService.updateUser(user);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable("username") String username) {
        credentialsService.deleteUser(username);
    }

}
