package com.example.spring_security_brief.Controller;

import com.example.spring_security_brief.Entity.UserEntity;
import com.example.spring_security_brief.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository repository){
        this.userRepository = repository;
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER', 'SCOPE_ROLE_ADMIN')")
    @GetMapping("")
    public List<UserEntity> getAllUser(){
        return this.userRepository.findAll();
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable UUID id){

        return userRepository.findById(id)
                .orElseThrow();
    }
    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserEntity createUser(@RequestBody UserEntity user){
        return this.userRepository.save(user);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id){
        userRepository.deleteById(id);
    }


}
