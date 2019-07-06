package com.sid.service;

import com.sid.entities.AppRole;
import com.sid.entities.AppUser;
import org.springframework.data.domain.Page;

public interface CredentialsService {
    Page<AppUser> getUsers(int page, int size, String sortBy, String direction);
    AppUser getByUsername(String username);

    Page<AppRole> getRoles(int page, int size, String sortBy, String direction);
    AppRole getRole(String role_id);

    AppUser saveUser(AppUser appUser);
    AppRole saveRole(AppRole appRole);

    AppUser updateUser(AppUser appUser);
    AppRole updateRole(AppRole appRole);

    void deleteUser(String user_id);
    void deleteRole(String role_id);

    AppUser findByUsername(String username);

}
