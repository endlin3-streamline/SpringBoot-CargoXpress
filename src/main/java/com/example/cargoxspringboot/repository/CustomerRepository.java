package com.example.cargoxspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cargoxspringboot.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
    Customer findByUser(String user);
}
