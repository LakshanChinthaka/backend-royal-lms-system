package com.chinthaka.backendroyallmssystem.MailSender;

import com.chinthaka.backendroyallmssystem.MailSender.request.MailSenderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMailService {

    String mailSend(MailSenderDTO mailSenderDTO);

    Page<MailResponseDTO> getAllMail(Pageable pageable);
}
