package com.chinthaka.backendroyallmssystem.MailSender;

import com.chinthaka.backendroyallmssystem.MailSender.request.MailSenderDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/mail")
@Slf4j
public class MailController {



   private final IMailService mailService;

    public MailController(IMailService mailService) {
        this.mailService = mailService;
    }


    @PostMapping("/send")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> mailSend(@RequestBody MailSenderDTO mailSenderDTO){

        log.info("POST request received on /api/v1/mail/send");
        final String response = mailService.mailSend(mailSenderDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);

    }
}
