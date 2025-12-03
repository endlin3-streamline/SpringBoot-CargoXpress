package com.example.cargoxspringboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cargoxspringboot.entity.Packages;
import com.example.cargoxspringboot.repository.PackagesRepository;

import jakarta.transaction.Transactional;

@Service
public class PackagesService {
    @Autowired
    private PackagesRepository packagesRepository;
    
    public List<Packages> getAllPackages() {
        return packagesRepository.findAll();
    }

    public Packages addpackages(Packages obj){
        Long id = null;
        obj.setId(id);
        return packagesRepository.save(obj);
    }
    
    public Packages getPackagesById(long id){
        return packagesRepository.findById(id).orElse(null);
    }

    public Packages updatePackages(long id, Packages obj){
        obj.setId(id);
        return packagesRepository.save(obj);
    }

    @Transactional
    public void deletePackages(long id){
        packagesRepository.deleteById(id);
    }

}
