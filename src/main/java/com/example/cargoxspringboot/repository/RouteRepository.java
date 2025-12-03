package com.example.cargoxspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cargoxspringboot.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Long>{}
