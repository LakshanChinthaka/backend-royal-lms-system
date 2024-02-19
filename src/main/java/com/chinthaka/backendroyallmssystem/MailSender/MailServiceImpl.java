package com.chinthaka.backendroyallmssystem.MailSender;

import com.chinthaka.backendroyallmssystem.MailSender.request.MailSenderDTO;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class MailServiceImpl implements IMailService{

    private final JavaMailSender mailSender;
    private final MailRepo mailRepo;

    public MailServiceImpl(JavaMailSender mailSender, MailRepo mailRepo) {
        this.mailSender = mailSender;
        this.mailRepo = mailRepo;
    }

    @Override
    public String mailSend(MailSenderDTO mailSenderDTO) {

        if (Objects.isNull(mailSenderDTO)){
            throw new HandleException("Mail details not provide");
        }

        if (mailSenderDTO.getUserId() <= 0 ){
            throw new NotFoundException("User id can not be empty");
        }

        if ( Objects.isNull(mailSenderDTO.getSendFrom()) || Objects.isNull(mailSenderDTO.getSendTo())
        || Objects.isNull(mailSenderDTO.getMessageBody()) || Objects.isNull(mailSenderDTO.getSubject())){
            throw new NotFoundException("All the filed is required to fill");
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailSenderDTO.getSendFrom());
            message.setTo(mailSenderDTO.getSendTo());
            message.setSubject(mailSenderDTO.getSubject());
            message.setText(mailSenderDTO.getMessageBody());
            message.getSentDate();

            mailSender.send(message);
            log.info("Mail send success");

            Mail mail = new Mail.MailBuilder()
                    .sendFrom(mailSenderDTO.getSendFrom())
                    .sendTo(mailSenderDTO.getSendTo())
                    .Subject(mailSenderDTO.getSubject())
                    .message(mailSenderDTO.getMessageBody())
                    .studentId(mailSenderDTO.getUserId())
                    .build();

            mailRepo.save(mail);
            log.info("Mail save to db success");
            return "Successfully send";
        }catch (Exception e){
            log.error("Error while sending mail : {}",e.getMessage());
            throw new HandleException("Something went wrong while sending mail");
        }

    }
}
