package com.chinthaka.backendroyallmssystem.account;

import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import io.micrometer.core.instrument.Counter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;
    private final Counter apiRequestCounter;


    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> registerUser(@RequestBody AccountCreateRequest accountCreateRequest) {
        apiRequestCounter.increment();
      String res = accountService.registerUser(accountCreateRequest);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",res), HttpStatus.OK);
    }
}
