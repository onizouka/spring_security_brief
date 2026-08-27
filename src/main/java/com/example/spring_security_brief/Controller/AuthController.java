package com.example.spring_security_brief.Controller;


import com.example.spring_security_brief.Entity.RoleEntity;
import com.example.spring_security_brief.Entity.UserEntity;
import com.example.spring_security_brief.Repository.RoleRepository;
import com.example.spring_security_brief.Repository.UserRepository;
import com.example.spring_security_brief.dto.LoginDto;
import com.example.spring_security_brief.dto.LoginRequest;
import com.example.spring_security_brief.dto.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.example.spring_security_brief.Service.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(
            UserRepository userRepositoryInjected,
            RoleRepository roleRepositoryInjected,
            AuthenticationManager authManagerInjected,
            PasswordEncoder passwordEncoderInjected,
            TokenService tokenServiceInjected) {
        this.userRepository = userRepositoryInjected;
        this.roleRepository = roleRepositoryInjected;
        this.authManager = authManagerInjected;
        this.passwordEncoder = passwordEncoderInjected;
        this.tokenService = tokenServiceInjected;
    }



    @PostMapping("/login")
    public LoginDto login(@RequestBody LoginRequest request) {

        Authentication auth =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.username(),
                                request.password()
                        )
                );

        String token = tokenService.generateToken(auth);

        UserEntity userConnected =
                (UserEntity) auth.getPrincipal();

        return new LoginDto(
                token,
                userConnected.getUsername()
        );
    }
    @PostMapping("/register")
    public ResponseEntity<String>register(
            @RequestBody RegisterRequest request) {

        if(userRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        RoleEntity userRole = roleRepository.findById("ROLE_USER")
                .orElseThrow();
        UserEntity user = UserEntity.builder()
                .username(request.username())
                .email(request.email())
                .hashedPassword(
                        passwordEncoder.encode(
                                request.password()))
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Utilisateur créé");
    }
}

