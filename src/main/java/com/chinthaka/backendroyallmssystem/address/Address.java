package com.chinthaka.backendroyallmssystem.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private long addressId;

    @Column(name = "address",length = 100)
    private String address;

    @Column(name = "district",length = 50)
    private String district;

    @Column(name = "city",length = 50)
    private String city;
}
