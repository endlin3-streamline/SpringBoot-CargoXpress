package com.example.cargoxspringboot.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;

@Entity
public class Courier extends Person{
    @Size(min = 3, max = 50) 
    private String employeeId;

    @Size(min = 3, max = 50)
    private String serviceStatus;
    
    public Courier() {}

    public Courier(String user, String pass, String name, String phone, String email, String employeeId, String serviceStatus) {
        super(user, pass, name, phone, email);
        this.employeeId= employeeId;
        this.serviceStatus = serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    @Override
    public String getRole() {
        return "COURIER";
    }
}
