package com.chinthaka.backendroyallmssystem.MailSender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailRepo extends JpaRepository<Mail,Long> {
}
