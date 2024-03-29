package com.chinthaka.backendroyallmssystem.MailSender.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailSenderDTO {
    private String sendFrom;
    private String sendTo;
    private String subject;
    private String messageBody;
    private String createdDate;
    private long userId;

}
