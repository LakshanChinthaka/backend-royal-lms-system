package com.chinthaka.backendroyallmssystem.qualification;

import com.chinthaka.backendroyallmssystem.utils.Auditor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "qualification")
public class Qualification extends Auditor {

    @Id
    @GeneratedValue
    private long qualificationId;

    private String qualification;
}
