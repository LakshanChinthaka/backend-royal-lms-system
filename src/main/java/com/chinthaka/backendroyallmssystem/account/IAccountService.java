package com.chinthaka.backendroyallmssystem.account;

import com.chinthaka.backendroyallmssystem.auth.AuthRequest;

public interface IAccountService {
    
    Object createAuthenticationToken(AuthRequest authenticationRequest) throws Exception;

    Object getCurrentUserDetails();

    String registerUser(AccountCreateRequest accountCreateRequest);
}
