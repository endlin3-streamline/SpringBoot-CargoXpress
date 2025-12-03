package com.example.cargoxspringboot.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.cargoxspringboot.entity.*;
import com.example.cargoxspringboot.service.RouteService;
import com.example.cargoxspringboot.service.ShipmentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;
    @Autowired
    private RouteService routeService;


    // ==============================
    // 1. CUSTOMER: CREATE SHIPMENTS
    // ==============================
    @GetMapping("/shipments/create")
    public String showShipmentsForm(HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"CUSTOMER".equals(role)) {
            return "redirect:/login";
        }
        return "shipments_create.html";
    }

    @PostMapping("/shipments/submit")
    public String submitShipments(
            @RequestParam("pkgName") String pkgName,
            @RequestParam("weight") double weight,
            @RequestParam("length") double length,
            @RequestParam("width") double width,
            @RequestParam("height") double height,
            @RequestParam("cost") double cost,
            @RequestParam("origin") String origin,
            @RequestParam("destination") String destination,
            HttpServletRequest request) {

        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"CUSTOMER".equals(role)) {
            return "redirect:/login";
        }

        Packages newPackage = new Packages(pkgName, weight, length, width, height, cost);
        Route newRoute = new Route(origin, destination, 0.0, 0.0);
        
        Shipment shipment = new Shipment();
        shipment.setObjPackages(newPackage);
        shipment.setObjRoute(newRoute);
        shipment.setStatus("PENDING"); // Default status
        
        // 4. Link to Logged-in Customer
        Customer loggedInCustomer = (Customer) request.getSession().getAttribute("loggedInUser");
        shipment.setObjCustomer(loggedInCustomer);

        // 5. Save (Cascade will save Package and Route automatically!)
        shipmentService.createShipment(shipment);

        return "redirect:/shipments/history";
    }

    // ==========================================
    // 2. CUSTOMER: VIEW HISTORY (Filters by ID)
    // ==========================================
    @GetMapping("/shipments/history")
    public String showCustomerHistory(Model model, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"CUSTOMER".equals(role)) {
            return "redirect:/login";
        }

        Customer currentUser = (Customer) request.getSession().getAttribute("loggedInUser");
        
        // FILTER: Only get shipments for THIS customer
        List<Shipment> myShipments = shipmentService.getShipmentsByCustomer(currentUser);
        
        model.addAttribute("shipmentList", myShipments);
        return "shipments_list.html"; // Create this HTML to show the table
    }



    // ==========================================
    // 3. COURIER: VIEW PENDING JOBS
    // ==========================================
    @GetMapping("/courier/jobs")
    public String showAvailableJobs(Model model, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"COURIER".equals(role)) {
            return "redirect:/login";
        }

        // FILTER: Only get shipments with status "PENDING"
        // This solves your requirement: Accepted jobs disappear from this list!
        List<Shipment> availableJobs = shipmentService.getPendingShipments();
        
        model.addAttribute("jobList", availableJobs);
        return "shipments_for_courier.html"; 
    }


    // ==========================================
    // 4. COURIER: ACCEPT JOB (Action)
    // ==========================================
    @PostMapping("/courier/accept/{id}")
    public String acceptJob(@PathVariable("id") Long shipmentId, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"COURIER".equals(role)) {
            return "redirect:/login";
        }

        Courier currentCourier = (Courier) request.getSession().getAttribute("loggedInUser");
        Shipment shipment = shipmentService.getShipmentById(shipmentId);

        if (shipment != null && "PENDING".equals(shipment.getStatus())) {
            //assign Courier
            shipment.setObjCourier(currentCourier);
            
            //change status (this makes it disappear from PENDING list)
            shipment.setStatus("ACCEPTED");
            
            shipmentService.updateShipment(shipment);
            return "redirect:/courier/update/" + shipmentId;
        }
        return "redirect:/shipments_delivery";
    }

    // ============================================
    // 5. COURIER: MAPPPING FOR UPDATE STATUS PAGE
    // ============================================
    @GetMapping("/courier/my-deliveries")
    public String showMyDeliveriesList(Model model, HttpServletRequest request) {
        if (!isRole(request, "COURIER")) return "redirect:/login";

        Courier currentCourier = (Courier) request.getSession().getAttribute("loggedInUser");
    
        List<Shipment> myJobs = shipmentService.getShipmentsByCourier(currentCourier);
        model.addAttribute("myShipmentList", myJobs);
        model.addAttribute("shipmentRec", null);

        return "shipment_delivery";
    }

    @GetMapping("/courier/my-deliveries/{id}")
    public String selectDeliveryToUpdate(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        
        if (!isRole(request, "COURIER")) return "redirect:/login";

        Courier currentCourier = (Courier) request.getSession().getAttribute("loggedInUser");

        // Load Table Data
        List<Shipment> myJobs = shipmentService.getShipmentsByCourier(currentCourier);
        model.addAttribute("myShipmentList", myJobs);

        Shipment shipment = shipmentService.getShipmentById(id);
        
        if (shipment != null && shipment.getObjCourier().getId().equals(currentCourier.getId())) {
            model.addAttribute("shipmentRec", shipment); // Fill the form!
        } else {
            return "redirect:/courier/my-deliveries";
        }

        return "shipment_delivery"; 
    }


    //COURIER: MENGAMBIL DELIVERY 
    @GetMapping("/courier/update/{id}")
    public String showUpdateStatusPage(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"COURIER".equals(role)) {
            return "redirect:/login";
        }

        Shipment shipment = shipmentService.getShipmentById(id);
        Courier currentCourier = (Courier) request.getSession().getAttribute("loggedInUser"); // mengecek juga courier sudah mengambil delivery ini!
        
        //jika tidak ada shipment akan redirect
        if (shipment == null) {
             return "redirect:/courier/jobs";
        }

        // SAFETY CHECK: APAKAH COURIER NULL?
        // If no courier is assigned yet, kick them back to the job board.
        if (shipment.getObjCourier() == null) {
            return "redirect:/courier/jobs";
        }
        // Strict Check: Use IDs to compare (Prevent couriers seeing others' jobs)
        if (!shipment.getObjCourier().getId().equals(currentCourier.getId())) {
            return "redirect:/courier/jobs";
        }

        model.addAttribute("shipmentrec", shipment);

        // mengambil data dengan id dalam courier
        List<Shipment> myJobs = shipmentService.getShipmentsByCourier(currentCourier);
        model.addAttribute("myShipmentList", myJobs);


        return "shipment_delivery";
    }

    @PostMapping("/courier/update/submit")
    public String processStatusUpdate(@RequestParam("id") Long id, @RequestParam("status") String status, HttpServletRequest request) {
        if (!isRole(request, "COURIER")) return "redirect:/login";
        
        Shipment shipment = shipmentService.getShipmentById(id);
        if (shipment != null) {
            shipment.setStatus(status);
            shipmentService.updateShipment(shipment);
        }
        
        return "redirect:/courier/my-deliveries";
    }
    


    // ============================================
    // 6. ADMIN: VIEW EVERYTHING ROUTE & SHIPMENTS
    // ============================================
    @GetMapping("/route")
    public String routePage(Model model, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"ADMIN".equals(role)) {
            return "redirect:/login";
        }
        model.addAttribute("routeList", routeService.getAllRoute()); // List untuk tabel
        model.addAttribute("routeInfo", new Route()); // Object kosong untuk form
        return "route";
    }

    @GetMapping("/route/{id}")
    public String getRoutePage(Model model, @PathVariable("id") Long id, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"ADMIN".equals(role)) {
            return "redirect:/login";
        }
        model.addAttribute("routeList", routeService.getAllRoute());
        model.addAttribute("routeRec", routeService.getRouteById(id)); // Data untuk diedit
        return "route";
    }

    // ===CRUD===
    @PostMapping(value="/route/submit", params={"edit"})
    public String routeEdit(@ModelAttribute("routeInfo") Route routeInfo, HttpServletRequest request) {
        
        Route existing = routeService.getRouteById(routeInfo.getId());
        if (existing != null) {
            existing.setDestination(routeInfo.getDestination());
            existing.setDistance(routeInfo.getDistance());
            existing.setEst_hour(routeInfo.getEst_hour());
            
            if (routeInfo.getOrigin() != null) existing.setOrigin(routeInfo.getOrigin());
            routeService.updateRoute(existing.getId(), existing);
        }
        
        return "redirect:/route";
    }
    @PostMapping(value="/route/submit", params={"delete"})
    public String routeDelete(@ModelAttribute("routeInfo") Route routeInfo, HttpServletRequest request) {
        routeService.deleteRoute(routeInfo.getId()); 
        
        return "redirect:/route";
    }



    @GetMapping("/shipment")
    public String showAllShipments(Model model, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"ADMIN".equals(role)) {
            return "redirect:/login";
        }
        // ADMIN SEES ALL
        List<Shipment> allShipments = shipmentService.getAllShipments();
        model.addAttribute("shipmentList", allShipments);
        return "shipment.html";
    }
    @GetMapping("/shipment/{id}")
    public String getShipmentPage(Model model, @PathVariable("id") Long id, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"ADMIN".equals(role)) {
            return "redirect:/login";
        }
        model.addAttribute("shipmentList", shipmentService.getAllShipments());
        model.addAttribute("shipmentRec", shipmentService.getShipmentById(id)); // Data untuk diedit
        return "shipment";
    }

    // ===CRUD===
    @PostMapping(value="/shipment/submit", params={"edit"})
    public String shipmentEdit(@ModelAttribute("shipmentInfo") Shipment shipmentInfo, HttpServletRequest request) {
        
        Shipment existing = shipmentService.getShipmentById(shipmentInfo.getId());
        if (existing != null) {
            existing.setStatus(shipmentInfo.getStatus());
            existing.setTotalCost(shipmentInfo.getTotalCost());
            shipmentService.updateShipment(existing);
        }
        
        return "redirect:/shipment";
    }
    @PostMapping(value="/shipment/submit", params={"delete"})
    public String shipmentDelete(@ModelAttribute("shipmentInfo") Shipment shipmentInfo, HttpServletRequest request) {
        shipmentService.deleteShipment(shipmentInfo.getId()); 
        
        return "redirect:/shipment";
    }





    // ================================
    // 7. PENGECEKKAN BOOL USERROLE
    // ================================
    private boolean isRole(HttpServletRequest request, String role) {
        HttpSession session = request.getSession(false);
        return session != null && role.equals(session.getAttribute("userRole"));
    }





}