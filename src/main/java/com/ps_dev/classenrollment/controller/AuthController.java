package com.ps_dev.classenrollment.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String home(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            for (GrantedAuthority auth : authentication.getAuthorities()) {
                if (auth.getAuthority().equals("ROLE_ADMIN")) {
                    return "redirect:/admin/dashboard";
                } else if (auth.getAuthority().equals("ROLE_DOSEN")) {
                    return "redirect:/dosen/dashboard";
                } else if (auth.getAuthority().equals("ROLE_MAHASISWA")) {
                    return "redirect:/mahasiswa/dashboard";
                }
            }
        }
        return "redirect:/login";
    }
}