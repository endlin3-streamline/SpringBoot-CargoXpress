package com.example.cargoxspringboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Packages {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_package;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(name = "pkgname") 
    private String pkgName; 

    private Double cost;
    
    private Double weightKg; 
    private Double lengthCm; 
    private Double widthCm; 
    private Double heightCm;

    public Packages() {}

    public Packages(String pkgName, Double weightKg, Double lengthCm, Double widthCm, Double heightCm, Double cost) {
        this.pkgName = pkgName;
        this.weightKg = weightKg;
        this.lengthCm = lengthCm;
        this.widthCm = widthCm;
        this.heightCm = heightCm;
        this.cost = cost;
    }

    //GETTERS AND SETTERS

    public String getPkgName() { return pkgName; }
    public void setPkgName(String pkgName) { this.pkgName = pkgName; }

    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }


    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }


    public Double getLengthCm() { return lengthCm; }
    public void setLengthCm(Double lengthCm) { this.lengthCm = lengthCm; }


    public Double getWidthCm() { return widthCm; }
    public void setWidthCm(Double widthCm) { this.widthCm = widthCm; }

    public Double getHeightCm() { return heightCm; }
    public void setHeightCm(Double heightCm) { this.heightCm = heightCm; }

    public Long getId() { return id_package; }
    public void setId(Long id) { this.id_package = id; }
}