package com.example.cargoxspringboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.cargoxspringboot.entity.Packages;
import com.example.cargoxspringboot.service.PackagesService;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class PackagesController {
    @Autowired
    private PackagesService packagesService;


    @GetMapping("/packages")
    public String listPackages(Model model, HttpServletRequest request) {
        // --- ADMIN CHECK ---
        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"ADMIN".equals(role)) {
            return "redirect:/login";
        }
        List<Packages> list = packagesService.getAllPackages();
        model.addAttribute("packagesList", list);
        model.addAttribute("packageInfo", new Packages()); 
        return "packages.html";
    }

    @GetMapping("/packages/{id}") 
    public String getPackagePage(Model model, @PathVariable("id") Long id, HttpServletRequest request){        
        // --- ADMIN CHECK ---
        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"ADMIN".equals(role)) {
            return "redirect:/login";
        }
        List<Packages> list = packagesService.getAllPackages();
        Packages packageRec = packagesService.getPackagesById(id); 
        
        model.addAttribute("packagesList", list);
        model.addAttribute("packageInfo", packageRec);
        return "packages.html"; 
    }

    // === CRUD ===
    @PostMapping(value="/packages/submit", params={"edit"})
    public String packageEdit(@ModelAttribute("packageInfo") Packages packageInfo, HttpServletRequest request){

        Long id = packageInfo.getId();
        
        packagesService.updatePackages(id, packageInfo); 
        return "redirect:/packages";
    }
    @PostMapping( value="/packages/submit", params={"delete"})
    public String packagesDelete(@ModelAttribute("packageInfo") Packages packageInfo, HttpServletRequest request){

        Long id = packageInfo.getId();

        packagesService.deletePackages(id);
        return "redirect:/packages";
    }
}