package com.chinthaka.backendroyallmssystem.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUserId( long userId);

    boolean existsByUsername(String username);
}
