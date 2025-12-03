package com.example.cargoxspringboot.entity;


import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double totalCost;
    private String status; 
    
    
    //timestamp
    private LocalDateTime createdDate; 

    //========== RELATIONSHIPS ==========
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "package_id")
    private Packages objPackages;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "route_id")
    private Route objRoute;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer objCustomer;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier objCourier;

    public Shipment() {
        this.createdDate = LocalDateTime.now();
        this.status = "ON PROCESS"; 
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public Packages getObjPackages() { return objPackages; }
    public void setObjPackages(Packages objPackage) { this.objPackages = objPackage; }

    public Route getObjRoute() { return objRoute; }
    public void setObjRoute(Route objRoute) { this.objRoute = objRoute; }

    public Customer getObjCustomer() { return objCustomer; }
    public void setObjCustomer(Customer objCustomer) { this.objCustomer = objCustomer; }

    public Courier getObjCourier() { return objCourier; }
    public void setObjCourier(Courier objCourier) { this.objCourier = objCourier; }


    //referensi untuk localdatetime
    //https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html
}