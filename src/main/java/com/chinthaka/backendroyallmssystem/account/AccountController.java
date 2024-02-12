package com.chinthaka.backendroyallmssystem.account;

import com.chinthaka.backendroyallmssystem.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/create")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> registerUser(@RequestBody AccountCreateRequest accountCreateRequest) {
        if (userRepository.existsByUsername(accountCreateRequest.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // Creating a new user object
        User user = new User();
        user.setUsername(accountCreateRequest.getUsername());
        user.setPassword(passwordEncoder.encode(accountCreateRequest.getPassword()));
        user.setRole(Role.valueOf(accountCreateRequest.getRole()));
        user.setUserId(accountCreateRequest.getUserId());

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
