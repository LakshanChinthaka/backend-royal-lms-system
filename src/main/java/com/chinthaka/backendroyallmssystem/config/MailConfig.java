package com.chinthaka.backendroyallmssystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {



    @Bean
    JavaMailSender createMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.ethereal.email");
//        mailSender.setPort(587);
//        mailSender.setUsername("alvah.heidenreich@ethereal.email");
//        mailSender.setPassword("8HjGDcHUeEZ7Fe6K2t");

        mailSender.setHost("sandbox.smtp.mailtrap.io");
        mailSender.setPort(2525);
        mailSender.setUsername("d372c0cbc7d654");
        mailSender.setPassword("be96dd456d7f2a");
//        mailSender.set


        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }
}
//play.mailer {
//    host = "sandbox.smtp.mailtrap.io"
//    port = 2525
//    ssl = no
//    tls = yes
//    user = "d372c0cbc7d654"
//    password = "be96dd456d7f2a"
