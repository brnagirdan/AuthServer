package com.oauth.controller;

import com.oauth.model.AuthUser;
import com.oauth.repository.OAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    OAuthRepository oAuthRepository;

     UserController(OAuthRepository oAuthRepository) {
        this.oAuthRepository = oAuthRepository;
    }

    @GetMapping("/user")
     Optional<AuthUser> getEmployee(@RequestParam String email) {
        return this.oAuthRepository.findByEmail(email);
    }

    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> postMessage(@RequestBody AuthUser user) {
        AuthUser authuser=oAuthRepository.save(new AuthUser(user.getEmail(),user.getPassword()));
                  URI location= ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

}
