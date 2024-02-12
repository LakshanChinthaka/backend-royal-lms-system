package com.chinthaka.backendroyallmssystem.baseUser;

import com.chinthaka.backendroyallmssystem.gender.Gender;
import com.chinthaka.backendroyallmssystem.utils.Auditor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public class BaseUser extends Auditor {

    @Id
    @GeneratedValue
    @Column(name = "st_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "nic",unique = true)
    private String nic;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "dob")
    private String dob;

    @Column(name = "active_statue")
    private boolean activeStatus = true;

    @Column(name = "profile_url")
    private String imageUrl;

}
