package com.chinthaka.backendroyallmssystem.auth;

import com.chinthaka.backendroyallmssystem.account.IAccountService;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final IAccountService accountService;


    @PostMapping("/authenticate")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) throws Exception {
        System.out.println(authenticationRequest.getUsername());
        System.out.println(authenticationRequest.getPassword());
        log.info("Execute Authentication Controller: password:{}, username: {} ",
                authenticationRequest.getUsername(),authenticationRequest.getUsername());
        Object response = accountService.createAuthenticationToken(authenticationRequest);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<StandardResponse> getCurrentUserDetails() {
        Object response = accountService.getCurrentUserDetails();
        return null;
    }
}
