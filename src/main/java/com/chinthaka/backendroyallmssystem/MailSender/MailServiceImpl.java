package com.chinthaka.backendroyallmssystem.MailSender;

import com.chinthaka.backendroyallmssystem.MailSender.request.MailSenderDTO;
import com.chinthaka.backendroyallmssystem.excaption.HandleException;
import com.chinthaka.backendroyallmssystem.excaption.NotFoundException;
import com.chinthaka.backendroyallmssystem.utils.EntityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

//            String truncatedMessage = mailSenderDTO.getMessageBody()
//                    .substring(0, Math.min(mailSenderDTO.getMessageBody().length(), 2000));

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

    @Override
    public Page<MailResponseDTO> getAllMail(Pageable pageable) {
      try {
          Page<Mail> mailPage = mailRepo.findAll(pageable);
          return mailPage.map(mail -> new MailResponseDTO(
                  mail.getMailId(),
                  mail.getSendFrom(),
                  mail.getSendTo(),
                  mail.getSubject(),
                  mail.getMessage(),
                  EntityUtils.convertToDateTime(mail.getCreatedDate()),
                  mail.getStudentId()

          ));
      }catch (Exception e){
          log.error("Error while fetching mails : {}",e.getMessage());
          throw new HandleException("Something went wrong while fetching mails");
      }
    }
}
