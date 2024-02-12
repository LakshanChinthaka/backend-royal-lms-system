package com.chinthaka.backendroyallmssystem.qualification;

import com.chinthaka.backendroyallmssystem.utils.Auditor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "qualification")
public class Qualification extends Auditor {

    @Id
    @GeneratedValue
    private long qualificationId;

    private String qualification;
}
