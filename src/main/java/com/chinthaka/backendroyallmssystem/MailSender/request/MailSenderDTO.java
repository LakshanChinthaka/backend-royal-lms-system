package com.chinthaka.backendroyallmssystem.MailSender.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailSenderDTO {
    private String sendFrom;
    private String sendTo;
    private String subject;
    private String messageBody;
    private long userId;
}
