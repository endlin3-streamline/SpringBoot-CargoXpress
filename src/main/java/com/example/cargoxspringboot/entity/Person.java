package com.example.cargoxspringboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 8, max = 50, message = "Username minimal 8 karakter")
    @Column(unique = true)
    private String user;

    @Size(min = 3, max = 50, message = "Password minimal 3 karakter")
    private String pass;

    @Size(min = 3, max = 50, message = "Nama minimal 3 karakter")
    private String name;

    @Size(min = 8, max = 15, message = "Nomor HP tidak valid")
    private String phone;

    @Size(min = 5, max = 50)
    private String email;

    //Constructor
    public Person() {}
    public Person(String user, String pass, String name, String phone, String email) {
        this.user = user;
        this.pass = pass;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    
    // Getters and setters
    public void setUser(String user) {
        this.user = user;
    }
    public String getUser() {
        return user;
    }

    public void setPass(String pass){
        this.pass = pass;
    }
    public String getPass(){
        return pass;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public abstract String getRole();
}