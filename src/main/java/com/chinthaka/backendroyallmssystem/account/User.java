package com.chinthaka.backendroyallmssystem.account;


import com.chinthaka.backendroyallmssystem.role.Role;
import com.chinthaka.backendroyallmssystem.utils.Auditor;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user_account")
public class User extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "username",unique = true)
    @NotNull
    private String username;

    @Column(name = "user_Id")
    @NotNull
    private long userId;

    @Column(name = "user_nic")
    @NotNull
    private String userNic;

    @Column(name = "password")
    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

}
