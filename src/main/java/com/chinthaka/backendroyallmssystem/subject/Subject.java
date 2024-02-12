package com.chinthaka.backendroyallmssystem.subject;

import com.chinthaka.backendroyallmssystem.utils.Auditor;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
@Table(name = "subject")
@Builder
public class Subject extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;

    @Column(name = "code", unique = true, nullable = false)
    private String subjectCode;

    @Column(name = "name", nullable = false)
    private String name;

}
