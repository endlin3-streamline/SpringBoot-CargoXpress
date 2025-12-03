package com.example.cargoxspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.cargoxspringboot.entity.Admin;
import com.example.cargoxspringboot.repository.AdminRepository;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    
    public List<Admin> getAllAdmin() {
        return adminRepository.findAll();
    }

    public void addAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public Admin findByUsername(String username) {
        return adminRepository.findByUser(username);
    }
}
