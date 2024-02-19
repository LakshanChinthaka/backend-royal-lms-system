package com.chinthaka.backendroyallmssystem.MailSender;

import com.chinthaka.backendroyallmssystem.utils.Auditor;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
@Table(name = "mail")
public class Mail extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mailId;

    @Column(name = "sendFrom", nullable = false, updatable = false)
    private String sendFrom;

    @Column(name = "sendTo", nullable = false, updatable = false)
    private String sendTo;

    @Column(name = "subject", nullable = false, updatable = false)
    private String Subject;

    @Column(name = "message",nullable = false, updatable = false)
    private String message;

    @Column(name = "student_id",nullable = false, updatable = false)
    private long studentId;

}
