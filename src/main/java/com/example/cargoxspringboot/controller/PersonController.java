package com.example.cargoxspringboot.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.cargoxspringboot.entity.Person;
import com.example.cargoxspringboot.service.PersonService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ResponseBody;

import com.example.cargoxspringboot.entity.Admin;
import com.example.cargoxspringboot.entity.Courier;
import com.example.cargoxspringboot.entity.Customer;
import com.example.cargoxspringboot.service.CustomerService;
import com.example.cargoxspringboot.service.AdminService;
import com.example.cargoxspringboot.service.CourierService;

@Controller
public class PersonController {
    @Autowired
    private PersonService personService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CourierService courierService;
    @Autowired
    private AdminService adminService;

    @GetMapping("/")
    @ResponseBody
    public String index() {
        return "Home Page";
    }


    //===========PERSON SECTION (FOR ADMIN ONLY)=============
    @GetMapping("/person")
    public String PersonPage(Model model, HttpServletRequest request) {
        @SuppressWarnings("unused")
        List<Person> personList;
        // pengecekkan jika user admin, selain admin tidak ada yang bisa akses
        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"ADMIN".equals(role)) {
            return "redirect:/login";
        }

        model.addAttribute("personList", personService.getAllPerson());
        return "person.html";
    }



    //===========CUSTOMER SECTION (FOR ADMIN ONLY)=============
    @GetMapping("/customer")
    public String CustomerPage(Model model, HttpServletRequest request) {
        List<Customer> customerList = customerService.getAllCustomer();
        // pengecekkan jika user admin bukan
        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"ADMIN".equals(role)) {
            return "redirect:/login";
        }
        model.addAttribute("customerList", customerList);
        model.addAttribute("customerInfo", new Customer());
        return "customer.html";
    }
    
    @GetMapping("/customer/{id}") 
    public String getCustomerPage(Model model, @PathVariable("id") Long id, HttpServletRequest request){
        List<Customer> customerList = customerService.getAllCustomer();
        Customer customerRec = customerService.getCustomerById(id);
        // pengecekkan jika user admin bukan
        String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
        if (!"ADMIN".equals(role)) {
            return "redirect:/login";
        }

        model.addAttribute("customerList", customerList);
        model.addAttribute("customerRec", customerRec);
        return "customer.html";
    }

    

    // === CRUD ===
    @PostMapping(value={"/customer/submit"}, params={"add"})
    public String customerAdd(@ModelAttribute("customerInfo") Customer customerInfo){
        customerService.addCustomer(customerInfo);
        return "redirect:/customer";
    }
    @PostMapping( value="/customer/submit/{id}", params={"edit"})
    public String customerEdit(@ModelAttribute("customerInfo") Customer customerInfo,
    @PathVariable("id") Long id){
        customerService.updateCustomer(id,customerInfo);
        return "redirect:/customer";
    }

    @PostMapping( value="/customer/submit/{id}", params={"delete"})
    public String customerDelete(@PathVariable("id") Long id){
        customerService.deleteCustomer(id);
        return "redirect:/customer";
    }

   
    //===========COURIER SECTION (FOR ADMIN ONLY)=============
        @GetMapping("/courier")
        public String CourierPage(Model model, HttpServletRequest request) {
            List<Courier> courierList = courierService.getAllCourier(); 
            // pengecekkan jika user admin bukan
            String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
            if (!"ADMIN".equals(role)) {
                return "redirect:/login";
            }

            model.addAttribute("courierList", courierList);
            model.addAttribute("courierInfo", new Courier()); 
            return "courier.html";
        }

        @GetMapping("/courier/{id}") 
        public String getCourierPage(Model model, @PathVariable("id") Long id, HttpServletRequest request){
            List<Courier> courierList = courierService.getAllCourier();
            // pengecekkan jika user admin bukan
            String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
            if (!"ADMIN".equals(role)) {
                return "redirect:/login";
            }

            Courier courierRec = courierService.getCourierById(id); 
            model.addAttribute("courierList", courierList);
            model.addAttribute("courierRec", courierRec);
            return "courier.html";
        }

        // === CRUD ===
        @PostMapping(value={"/courier/submit"}, params={"add"})
        public String courierAdd(@ModelAttribute("courierInfo") Courier courierInfo){
            courierService.addCourier(courierInfo); 
            return "redirect:/courier";
        }

        @PostMapping( value="/courier/submit/{id}", params={"edit"})
        public String courierEdit(@ModelAttribute("courierInfo") Courier courierInfo,
        @PathVariable("id") Long id){
            courierService.updateCourier(id, courierInfo); 
            return "redirect:/courier";
        }

        @PostMapping( value="/courier/submit/{id}", params={"delete"})
        public String courierDelete(@PathVariable("id") Long id){
            courierService.deleteCourier(id); 
            return "redirect:/courier";
        }





        //============ THE LOGIN ==============
        @GetMapping(value="/login")
        public String loginPage(Model model, HttpServletRequest request) {
            if (request.getSession().getAttribute("userRole") != null) {
                String role = (String) request.getSession().getAttribute("userRole");
                
                //pengecekkan login user
                if ("CUSTOMER".equals(role)) {
                    return "redirect:/dashboard/customer";
                } else if ("COURIER".equals(role)) {
                    return "redirect:/dashboard/courier";
                } else if ("ADMIN".equals(role)) {
                    return "redirect:/dashboard/admin";
                }
            }
            return "login.html";
        }

        @PostMapping(value="/validateLogin")
        public String validateLogin(Model model, @RequestParam("user") String username, @RequestParam("password") String password, HttpServletRequest request) {

            Person person = personService.findByUsername(username);

            if (person != null && person.getPass().equals(password)) {
                request.getSession().setAttribute("loggedInUser", person);
                
                // === POLYMORPHISM IN ACTION ===
                String role = person.getRole();
                request.getSession().setAttribute("userRole", role);
            
                //mempersingkat login menjadi userrole langsung
                if ("CUSTOMER".equals(role)) {
                    return "redirect:/dashboard/customer";
                } else if ("COURIER".equals(role)) {
                    return "redirect:/dashboard/courier";
                } else if ("ADMIN".equals(role)) {
                    return "redirect:/dashboard/admin";
                }
            }
            // --- FAILED: No match found ---
            return "redirect:/login?error";
        }

        @GetMapping(value="/logout")
        public String logout(HttpServletRequest request) {
            request.getSession().invalidate(); //<--- hilangkan sesi user
            return "redirect:/login";
        }





        //============ THE DASHBOARDS ==============
        @GetMapping("/dashboard/customer")
        public String showCustomerDashboard(HttpServletRequest request) {
            String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
            // pengecekkan jika sudah login, selain customer tidak ada yang bisa akses
            if (!"CUSTOMER".equals(role)) {
                return "redirect:/login";
            }
            return "dashboard_customer.html"; 
        }

        @GetMapping("/dashboard/courier")
        public String showCourierDashboard(HttpServletRequest request) {
            String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
            // pengecekkan jika sudah login, selain courier tidak ada yang bisa akses
            if (!"COURIER".equals(role)) {
                return "redirect:/login";
            }
            return "dashboard_courier.html"; 
        }
        @GetMapping("/dashboard/admin")
        public String showAdminDashboard(HttpServletRequest request) {
            String role = (String) request.getSession().getAttribute("userRole"); //paggil variable userRole
            // pengecekkan jika sudah login, selain admin tidak ada yang bisa akses
            if (!"ADMIN".equals(role)) {
                return "redirect:/login";
            }
            return "dashboard_admin.html"; 
        }



        

        //============ THE SIGNUP ==============
        @GetMapping("/signup")
        public String showSignupPage() {
            return "signup.html"; 
        }

        @GetMapping("/customersignup")
        public String showCustomerSignupPage(Model model) {
            model.addAttribute("customerInfo", new Customer());
            return "customersignup.html"; 
        }
        @PostMapping(value={"/customersignup/register"}, params={"register"})
        public String customerRegister(@ModelAttribute("customerInfo") Customer customerInfo){
            customerService.addCustomer(customerInfo);
            return "redirect:../login";
        }


        @GetMapping("/couriersignup")
        public String showCourierSignupPage(Model model) {
            model.addAttribute("courierInfo", new Courier());
            return "couriersignup.html"; 
        }
        @PostMapping(value={"/couriersignup/register"}, params={"register"})
        public String courierRegister(@ModelAttribute("courierInfo") Courier courierInfo){
            courierService.addCourier(courierInfo);
            return "redirect:../login";
        }


        @GetMapping("/adminsignup")
        private String showAdminSignupPage(Model model) {
            model.addAttribute("adminInfo", new Admin());
            return "adminsignup.html"; 
        }
        @PostMapping(value={"/adminsignup/register"}, params={"register"})
        private String adminRegister(@ModelAttribute("adminInfo") Admin adminInfo){
            adminService.addAdmin(adminInfo);
            return "redirect:../login";
        }
}