package com.chinthaka.backendroyallmssystem.MailSender;

import com.chinthaka.backendroyallmssystem.MailSender.request.MailSenderDTO;

public interface IMailService {

    String mailSend(MailSenderDTO mailSenderDTO);
}
