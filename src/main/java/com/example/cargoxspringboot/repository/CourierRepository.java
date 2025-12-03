package com.example.cargoxspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cargoxspringboot.entity.Courier;

public interface CourierRepository extends JpaRepository<Courier, Long>{
    Courier findByUser(String user);
}

