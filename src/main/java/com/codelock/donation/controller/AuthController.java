package com.codelock.donation.controller;

import com.codelock.donation.model.Donor;
import com.codelock.donation.repository.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Pattern;

@Controller
public class AuthController {

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {

        String passwordPolicy = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";

        // 1. Keep data in form if an error happens
        model.addAttribute("username", username);
        model.addAttribute("email", email);

        // 2. Security Check: Do passwords match?
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Error: Passwords do not match.");
            return "register"; 
        }

        // 3. Security Check: SR1 Strong Password Policy
        if (!Pattern.matches(passwordPolicy, password)) {
            model.addAttribute("error", "[SECURITY ALERT] Weak Password. Must contain at least 8 chars, 1 uppercase, 1 number, and 1 symbol.");
            return "register"; 
        }

        // 4. Database Check: Does username or email already exist?
        if (donorRepository.existsByUsername(username)) {
            model.addAttribute("error", "Username is already taken.");
            return "register";
        }
        if (donorRepository.existsByEmail(email)) {
            model.addAttribute("error", "Email is already registered.");
            return "register";
        }

        // 5. Success! Hash the password and save to DB
        Donor newDonor = new Donor();
        newDonor.setUsername(username);
        newDonor.setEmail(email);
        newDonor.setPassword(passwordEncoder.encode(password)); // Scrambles the password!

        donorRepository.save(newDonor); // Saves to H2 Database

        return "redirect:/login?registered=true";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}