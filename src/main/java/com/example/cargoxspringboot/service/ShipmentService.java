package com.example.cargoxspringboot.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cargoxspringboot.entity.Shipment;
import com.example.cargoxspringboot.entity.Customer;
import com.example.cargoxspringboot.entity.Courier;
import com.example.cargoxspringboot.repository.ShipmentRepository;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    public void createShipment(Shipment shipment) {
        shipmentRepository.save(shipment);
    }

    public List<Shipment> getAllShipments() {
        return shipmentRepository.findAll();
    }

    public List<Shipment> getShipmentsByCustomer(Customer customer) {
        return shipmentRepository.findByObjCustomer(customer);
    }

    public List<Shipment> getPendingShipments() {
        return shipmentRepository.findByStatus("PENDING");
    }
    

    public List<Shipment> getShipmentsByCourier(Courier courier) {
        return shipmentRepository.findByObjCourier(courier);
    }


    public Shipment getShipmentById(Long id) {
        return shipmentRepository.findById(id).orElse(null);
    }
    
    
    public void updateShipment(Shipment shipment) {
        shipmentRepository.save(shipment);
    }
    public void deleteShipment(Long id) {
        shipmentRepository.deleteById(id);
    }
}