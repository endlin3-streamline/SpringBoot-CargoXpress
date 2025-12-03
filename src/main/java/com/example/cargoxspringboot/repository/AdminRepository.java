package com.example.cargoxspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cargoxspringboot.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
    Admin findByUser(String user);
}