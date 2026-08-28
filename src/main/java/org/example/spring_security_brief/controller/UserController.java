package org.example.spring_security_brief.controller;

import org.example.spring_security_brief.dto.RegisterRequest;
import org.example.spring_security_brief.dto.UserCreatedRequest;
import org.example.spring_security_brief.dto.UserDto;
import org.example.spring_security_brief.entity.UserEntity;
import org.example.spring_security_brief.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserController(UserRepository repository, PasswordEncoder passwordEncoder1){
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder1;
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER', 'SCOPE_ROLE_ADMIN')")
    @GetMapping
    public List<UserDto> getAllUser() {
        return userRepository.findAll().stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable UUID id) {

        UserEntity user = userRepository.findById(id)
                .orElseThrow();

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody RegisterRequest request) {

        UserEntity user = UserEntity.builder()
                .username(request.username())
                .email(request.email())
                .hashedPassword(passwordEncoder.encode(request.password()))
                .build();

        UserEntity saved = userRepository.save(user);

        return new UserDto(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail()
        );
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id){
        userRepository.deleteById(id);
    }


}
