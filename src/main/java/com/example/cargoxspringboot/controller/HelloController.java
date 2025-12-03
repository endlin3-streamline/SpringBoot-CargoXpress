package com.example.cargoxspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HelloController {
    @RequestMapping(value="/hello", method = RequestMethod.GET)
    @ResponseBody
    public String index() {
        return "Hello world wide web!";
    }
    @GetMapping("/home")
    public String home(){
        return "home.html";
    }

    // melakukan redirect ke dashboard jika user sudah login
    // sehingga user tidak usah balik ke main
    @GetMapping("/main")
    public String main(HttpServletRequest request){
        if (!isLoggedIn(request)) {
            HttpSession session = request.getSession();
            String role = (String) session.getAttribute("userRole");
        
            if ("CUSTOMER".equals(role)) {
                return "redirect:/dashboard/customer";
            } 
            else if ("COURIER".equals(role)) {
                return "redirect:/dashboard/courier";
            }
            else if ("ADMIN".equals(role)) {
                return "redirect:/dashboard/admin";
            }
        }
        return "main";
    }


    // login redirect BOOL
    private boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
    
        if (session == null) return false;
        return session.getAttribute("COURIER") != null || 
               session.getAttribute("CUSTOMER") != null ||
               session.getAttribute("ADMIN") != null;
    }
}