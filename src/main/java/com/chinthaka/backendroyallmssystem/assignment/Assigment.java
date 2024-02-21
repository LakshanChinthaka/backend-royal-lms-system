package com.chinthaka.backendroyallmssystem.assignment;

import com.chinthaka.backendroyallmssystem.utils.Auditor;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@DynamicUpdate
@AllArgsConstructor
@Builder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "assigment")
public class Assigment extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assiId;

    @Column(name = "code", unique = true)
    private String assiCode;

    @Column(name = "batchId")
    private long batchId;

    @Column(name = "url")
    private String assiUrl;

    @Column(name = "dead_line")
    private String deadLine;
}
