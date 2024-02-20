package com.chinthaka.backendroyallmssystem.MailSender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MailResponseDTO {
    private Long mailId;
    private String sendFrom;
    private String sendTo;
    private String subject;
    private String messageBody;
    private String createdDate;
    private long userId;
}
