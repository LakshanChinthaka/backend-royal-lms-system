package com.chinthaka.backendroyallmssystem.assignment.assigmentSubmit;

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
@Table(name = "assigment_sumbit")
public class AssigmentSubmit extends Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submitId;

    @Column(name = "code" )
    private String assiCode;

    @Column(name = "studentId")
    private long studentId;

    @Column(name = "url")
    private String submitUrl;

    @Column(name = "batchId")
    private long batchId;

    @Column(name = "grade")
    private String grade;

}
