package com.sid.security;

import com.sid.dto.CustomUserDetails;
import com.sid.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameEquals(username)
                .map(x -> {
                    var user = new CustomUserDetails(x);
                    if (!user.isEnabled()) {
                        log.error("Locked account");
                        throw new DisabledException("Your account has been disabled! Please contact your administrator!");
                    }
                    return user;
                }).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
