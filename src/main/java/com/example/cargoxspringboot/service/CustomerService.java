package com.example.cargoxspringboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cargoxspringboot.entity.Customer;
import com.example.cargoxspringboot.repository.CustomerRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Customer addCustomer(Customer obj){
        Long id = null;
        obj.setId(id);
        return customerRepository.save(obj);
    }
    public Customer getCustomerById(long id){
        return customerRepository.findById(id).orElse(null);
    }
    public Customer updateCustomer(long id, Customer obj){
        obj.setId(id);
        return customerRepository.save(obj);
    }

    @Transactional
    public void deleteCustomer(long id){
        customerRepository.deleteById(id);
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByUser(username);
    }

}
