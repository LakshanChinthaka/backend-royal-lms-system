package com.chinthaka.backendroyallmssystem.auth;

import com.chinthaka.backendroyallmssystem.account.IAccountService;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final IAccountService accountService;

    @PostMapping("/authenticate")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) throws Exception {
        log.info("Execute Authentication Controller: password:{}, username: {} ",
                authenticationRequest.getUsername(),authenticationRequest.getUsername());
        Object response = accountService.createAuthenticationToken(authenticationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> getCurrentUserDetails() {
        log.info("GET request received on /profile");
        Object response = accountService.getCurrentUserDetails();
        log.info("Profile details before return: {}",response.toString());
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }

}
