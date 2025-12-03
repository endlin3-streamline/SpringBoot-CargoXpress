package com.example.cargoxspringboot.entity;

import jakarta.persistence.Entity;

@Entity
public class Admin extends Person{
    
    public Admin() {}

    public Admin(String user, String pass, String name, String phone, String email) {
        super(user, pass, name, phone, email);
    }

    public void setId(Long id) {}

    @Override
    public String getRole() {
        return "ADMIN";
    }
}
