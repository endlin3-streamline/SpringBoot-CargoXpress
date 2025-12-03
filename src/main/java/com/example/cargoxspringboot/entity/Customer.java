package com.example.cargoxspringboot.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;

@Entity
public class Customer extends Person{
    @Size(min = 3, max = 50)
    private String address;

    public Customer() {}

    public Customer(String user, String pass, String name, String phone, String email, String address) {
        super(user, pass, name, phone, email);
        this.address= address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String getRole() {
        return "CUSTOMER";
    }
}
