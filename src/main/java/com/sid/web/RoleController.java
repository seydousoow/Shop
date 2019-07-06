package com.sid.web;

import com.sid.entities.AppRole;
import com.sid.service.CredentialsService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    private final CredentialsService credentialsService;

    public RoleController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @GetMapping
    public Page<AppRole> getRoles(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "24") int size,
                                  @RequestParam(name = "sort", defaultValue = "roleName") String sort,
                                  @RequestParam(name = "direction", defaultValue = "ASC") String direction) {
        return credentialsService.getRoles(page, size, sort, direction);
    }

    @GetMapping("/roles/{id}")
    public AppRole getRole(@PathVariable("id") String role_id) {
        return credentialsService.getRole(role_id);
    }

    @PostMapping("/roles")
    public AppRole saveRole(@RequestBody AppRole role) {
        return credentialsService.saveRole(role);
    }

    @PutMapping("/roles")
    public AppRole updateRole(@RequestBody AppRole role) {
        return credentialsService.updateRole(role);
    }

    @DeleteMapping("/roles/{id}")
    public void deleteRole(@PathVariable("id") String role_id) {
        credentialsService.deleteRole(role_id);
    }


}
