package com.chinthaka.backendroyallmssystem.employee;

import com.chinthaka.backendroyallmssystem.address.Address;
import com.chinthaka.backendroyallmssystem.baseUser.BaseUser;
import com.chinthaka.backendroyallmssystem.qualification.Qualification;
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
@Table(name = "employee")
public class Employee extends BaseUser {

    @Column(name = "experince")
    private String experince;

    @Enumerated(EnumType.STRING)
    @Column(name = "emp_type")
    private EmployeeType employeeType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qualification_id")
    private Qualification qualification;

}