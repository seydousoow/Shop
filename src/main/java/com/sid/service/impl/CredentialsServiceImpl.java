package com.sid.service.impl;

import com.sid.entities.AppRole;
import com.sid.entities.AppUser;
import com.sid.exception.RestException;
import com.sid.repositories.RoleRepository;
import com.sid.repositories.UserRepository;
import com.sid.service.CredentialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@Transactional
@RequiredArgsConstructor
public class CredentialsServiceImpl implements CredentialsService {

    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Page<AppUser> getUsers(int page, int size, String sortBy, String direction) {
        Sort.Direction dir = Sort.Direction.fromString(direction.toUpperCase());
        return userRepository.findAll(PageRequest.of(page, size, Sort.by(dir, sortBy)));
    }

    @Override
    public Page<AppRole> getRoles(int page, int size, String sortBy, String direction) {
        Sort.Direction dir = Sort.Direction.fromString(direction.toUpperCase());
        return roleRepository.findAll(PageRequest.of(page, size, Sort.by(dir, sortBy)));
    }

    @Override
    public AppRole getRole(String roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new RestException("This role does not exist!"));
    }

    @Override
    public AppUser getByUsername(String username) {
        return userRepository.findByUsernameEquals(username).orElseThrow(() -> new RestException("This user does not exist!"));
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        if (userRepository.existsByUsernameEquals(appUser.getUsername())) throw new RestException("This username is not available!");
        final String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        /*
         * Set the role of the user
         * By default only the role USER is added. An administrator role will be added if isAdmin os set to true
         */
        appUser.getRoles().add(roleRepository.findByRoleNameEquals(ROLE_USER).orElseGet(() -> roleRepository.save(new AppRole(null, ROLE_USER))));
        if (Boolean.TRUE.equals(appUser.isAdmin()))
            appUser.getRoles().add(roleRepository.findByRoleNameEquals(ROLE_ADMIN).orElseGet(() -> roleRepository.save(new AppRole(null, ROLE_ADMIN))));

        return userRepository.save(appUser);
    }

    @Override
    public AppRole saveRole(AppRole appRole) {
        if (isBlank(appRole.getRoleName()))
            throw new RestException("The name of the role is empty!");

        if (roleRepository.existsByRoleNameEquals(appRole.getRoleName())) throw new RestException("This role already exist!");

        return roleRepository.save(appRole);
    }

    @Override
    public AppUser updateUser(AppUser user) {
        var id = userRepository.findByUsernameEquals(user.getUsername()).map(AppUser::getUserId)
                .orElseThrow(() -> new RestException("This user could not be found!"));
        user.setUserId(id);
        /*
         * Change the password if the password has been set
         * Check if is admin had been set and edit the roleList
         */
        if (user.getPassword() != null && user.getPassword().length() > 4) {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }
        if (user.getRoles().isEmpty())
            user.getRoles().add(roleRepository.findByRoleNameEquals(ROLE_USER).orElseGet(() -> roleRepository.save(new AppRole(null, ROLE_USER))));

        var roleAdmin = roleRepository.findByRoleNameEquals(ROLE_ADMIN).orElseGet(() -> roleRepository.save(new AppRole(null, ROLE_ADMIN)));
        if (Boolean.TRUE.equals(user.isAdmin()) && !user.getRoles().contains(roleAdmin)) user.getRoles().add(roleAdmin);
        else if (Boolean.FALSE.equals(user.isAdmin())) user.getRoles().remove(roleAdmin);
        return userRepository.save(user);
    }

    @Override
    public AppRole updateRole(AppRole appRole) {
        return roleRepository.findByRoleNameEquals(appRole.getRoleName())
                        .map(role -> {
                            role.setRoleName(appRole.getRoleName());
                            return roleRepository.save(role);
                        }).orElseThrow(() -> new RestException("This role does not exist!"));
    }

    @Override
    public void deleteUser(String username) {
        userRepository.findByUsernameEquals(username).ifPresentOrElse(userRepository::delete, () -> {
            throw new RestException("This user doesn't exist");
        });
    }

    @Override
    public void deleteRole(String roleId) {
        roleRepository.findById(roleId).ifPresentOrElse(roleRepository::delete, () -> {
            throw new RestException("This role doesn't exist");
        });
    }

}
