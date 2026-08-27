package com.example.spring_security_brief.Service;

import com.example.spring_security_brief.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return this.userRepository.findByUsername(username)
                    .orElseThrow(()-> new UsernameNotFoundException("User not found with name" + username));
        }

}


