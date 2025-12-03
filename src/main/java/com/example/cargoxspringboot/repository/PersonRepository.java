package com.example.cargoxspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.cargoxspringboot.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{
    Person findByUser(String user);
}
