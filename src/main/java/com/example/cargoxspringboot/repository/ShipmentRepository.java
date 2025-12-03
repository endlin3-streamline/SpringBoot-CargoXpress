package com.example.cargoxspringboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cargoxspringboot.entity.Courier;
import com.example.cargoxspringboot.entity.Customer;
import com.example.cargoxspringboot.entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long>{
    // Customer: supaya user hanya bisa lihat ordernya saja
    List<Shipment> findByObjCustomer(Customer customer);

    // Courier: melihat semua paket yang belum diambily
    List<Shipment> findByStatus(String status);
    
    // Courier: melihat semua paket yang sedang diambil
    List<Shipment> findByObjCourier(Courier courier);
}
