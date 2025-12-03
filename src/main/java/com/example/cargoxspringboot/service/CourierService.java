package com.example.cargoxspringboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cargoxspringboot.entity.Courier;
import com.example.cargoxspringboot.repository.CourierRepository;

import jakarta.transaction.Transactional;

@Service
public class CourierService {
    @Autowired
    private CourierRepository courierRepository;
    
    public List<Courier> getAllCourier() {
        return courierRepository.findAll();
    }

    public Courier addCourier(Courier obj){
        Long id = null;
        obj.setId(id);
        return courierRepository.save(obj);
    }
    
    public Courier getCourierById(long id){
        return courierRepository.findById(id).orElse(null);
    }

    public Courier updateCourier(long id, Courier obj){
        obj.setId(id);
        return courierRepository.save(obj);
    }

    @Transactional
    public void deleteCourier(long id){
        courierRepository.deleteById(id);
    }

    public Courier findByUsername(String username) {
        return courierRepository.findByUser(username);
    }

}
