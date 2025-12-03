package com.example.cargoxspringboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_route;

    @NotNull
    private String destination;
    @NotNull
    private String origin;
    private Double distance;
    private Double est_hour;

    // @OneToOne(mappedBy = "objRoute")
    // private Shipment shipment;
    
    //Constructor #1
    public Route() {}

    //Constructor #2
    public Route(String origin, String destination, Double distance, Double est_hour) {
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        this.est_hour = est_hour;
    }
    
    // Getters and setters
    public void setOrigin(String origin){
        this.origin = origin;
    }
    public String getOrigin(){
        return origin;
    }

    public void setDestination(String destination){
        this.destination = destination;
    }
    public String getDestination(){
        return destination;
    }

    public void setDistance(Double distance){
        this.distance = distance;
    }
    public Double getDistance(){
        return distance;
    }

    public void setEst_hour(Double est_hour) {
        this.est_hour = est_hour;
    }
    public Double getEst_hour() {
        return est_hour;
    }

    public Long getId() { return id_route; }
    public void setId(Long id) { this.id_route = id; }


}