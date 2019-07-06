package com.sid.service;

import com.sid.entities.AppRole;
import com.sid.entities.AppUser;
import com.sid.repositories.RoleRepository;
import com.sid.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CredentialsServiceImpl implements CredentialsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public CredentialsServiceImpl(UserRepository userRepository,
                                  RoleRepository roleRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Page<AppUser> getUsers(int page, int size, String sortBy, String direction) {
        Sort.Direction dir = Sort.Direction.fromString(direction.toUpperCase());
        return userRepository.findAll(PageRequest.of(page, size, Sort.by(dir, sortBy)));
    }

    @Override
    public Page<AppRole> getRoles(int page, int size, String sortBy, String direction) {
        Sort.Direction dir = Sort.Direction.fromString(direction.toUpperCase());
        return roleRepository.findAll(
                PageRequest.of(page, size, Sort.by(dir, sortBy)));
    }

    @Override
    public AppRole getRole(String role_id) {
        return roleRepository.findByRoleIdEquals(role_id);
    }

    @Override
    public AppUser getByUsername(String username) {
        AppUser user = userRepository.findByUsernameEquals(username);
        if (user == null)
            throw new RuntimeException("This user does not exist!");
        return user;
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        if (userRepository.findByUsernameEquals(appUser.getUsername()) != null)
            throw new RuntimeException("This user already exists!");
        /*
         * changed the plain text password to an BCrypt encoded password
         */
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        /*
         * Set the role of the user
         * By default only the role USER is added. An administrator role will be added if isAdmin os set to true
         */
        appUser.getRoles().add(roleRepository.findByRoleNameEquals("USER"));
        if (appUser.getIsAdmin())
            appUser.getRoles().add(roleRepository.findByRoleNameEquals("ADMIN"));

        return userRepository.save(appUser);
    }

    @Override
    public AppRole saveRole(AppRole appRole) {
        if (appRole.getRoleName() == null || appRole.getRoleName().length() < 1)
            throw new RuntimeException("The name of the role is empty!");

        if (roleRepository.findByRoleNameEquals(appRole.getRoleName()) != null)
            throw new RuntimeException("This role already exist!");

        return roleRepository.save(appRole);
    }

    @Override
    public AppUser updateUser(AppUser user) {
        user.setUserId(userRepository.findByUsernameEquals(user.getUsername()).getUserId());
        /*
         * Change the password if the password has been set
         * Check if is admin had been set and edit the roleList
         */
        if (user.getPassword() != null && user.getPassword().length() > 4) {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }
        if (user.getRoles().isEmpty())
            user.getRoles().add(roleRepository.findByRoleNameEquals("USER"));

        if (user.getIsAdmin() != null) {
            AppRole roleAdmin = roleRepository.findByRoleNameEquals("ADMIN");
            if (user.getIsAdmin() && !user.getRoles().contains(roleAdmin))
                user.getRoles().add(roleAdmin);
            else if (!user.getIsAdmin() && user.getRoles().contains(roleAdmin))
                user.getRoles().remove(roleAdmin);
        }
        return userRepository.save(user);
    }

    @Override
    public AppRole updateRole(AppRole appRole) {
        AppRole role = roleRepository.findByRoleNameEquals(appRole.getRoleName());
        role.setRoleName(appRole.getRoleName());
        return roleRepository.save(role);
    }

    @Override
    public void deleteUser(String username) {
        AppUser user = userRepository.findByUsernameEquals(username);
        if (user == null)
            throw new RuntimeException("This user doesn't exist");
        userRepository.delete(user);
    }

    @Override
    public void deleteRole(String role_id) {
        AppRole role = roleRepository.findByRoleIdEquals(role_id);
        if (role == null)
            throw new RuntimeException("This role doesn't exist");
        roleRepository.delete(role);
    }

    @Override
    public AppUser findByUsername(String username) {
        return userRepository.findByUsernameEquals(username);
    }
}
