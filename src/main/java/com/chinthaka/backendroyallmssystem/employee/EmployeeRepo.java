package com.chinthaka.backendroyallmssystem.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {

    boolean existsByNic(String nic);

    Employee findByNic(String nic);
}
