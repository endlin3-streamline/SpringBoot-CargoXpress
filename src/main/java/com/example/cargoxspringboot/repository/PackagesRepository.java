package com.example.cargoxspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cargoxspringboot.entity.Packages;

public interface PackagesRepository extends JpaRepository<Packages, Long>{}
