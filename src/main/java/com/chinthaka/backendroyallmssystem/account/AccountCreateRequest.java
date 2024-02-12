package com.chinthaka.backendroyallmssystem.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateRequest {
    private String username;
    private String password;
    private String role;
    private long userId;
}
