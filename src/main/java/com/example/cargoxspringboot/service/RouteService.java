package com.example.cargoxspringboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cargoxspringboot.entity.Route;
import com.example.cargoxspringboot.repository.RouteRepository;

import jakarta.transaction.Transactional;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;
    
    public List<Route> getAllRoute() {
        return routeRepository.findAll();
    }

    public Route addRoute(Route obj){
        Long id = null;
        obj.setId(id);
        return routeRepository.save(obj);
    }
    
    public Route getRouteById(long id){
        return routeRepository.findById(id).orElse(null);
    }

    public Route updateRoute(long id, Route obj){
        obj.setId(id);
        return routeRepository.save(obj);
    }

    @Transactional
    public void deleteRoute(long id){
        routeRepository.deleteById(id);
    }

}
