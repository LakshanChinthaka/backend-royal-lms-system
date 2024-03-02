package com.chinthaka.backendroyallmssystem.MailSender;

import com.chinthaka.backendroyallmssystem.MailSender.request.MailSenderDTO;
import com.chinthaka.backendroyallmssystem.utils.StandardResponse;
import io.micrometer.core.instrument.Counter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/mail")
@Slf4j
public class MailController {



   private final IMailService mailService;
    private final Counter apiRequestCounter;

    public MailController(IMailService mailService, Counter apiRequestCounter) {
        this.mailService = mailService;
        this.apiRequestCounter = apiRequestCounter;
    }


    @PostMapping("/send")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> mailSend(@RequestBody MailSenderDTO mailSenderDTO){
        apiRequestCounter.increment();
        log.info("POST request received on /api/v1/mail/send");
        final String response = mailService.mailSend(mailSenderDTO);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);

    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StandardResponse> getAllMail(
            @PageableDefault(sort = "mailId",direction = Sort.Direction.DESC) Pageable pageable){
        apiRequestCounter.increment();
        log.info("GET request received on /api/v1/mail/all");
        Page<MailResponseDTO> response = mailService.getAllMail(pageable);
        return new ResponseEntity<>(
                new StandardResponse(200,"Success",response), HttpStatus.OK);
    }
}
