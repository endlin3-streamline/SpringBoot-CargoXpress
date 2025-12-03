package com.example.cargoxspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cargoxspringboot.entity.Person;
import com.example.cargoxspringboot.repository.PersonRepository;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    
    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    public Person findByUsername(String username) {
        return personRepository.findByUser(username);
    }
}
