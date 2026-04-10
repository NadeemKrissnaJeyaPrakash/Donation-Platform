package com.codelock.donation.controller;

import com.codelock.donation.model.Donation;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    // Shows the blank form when they first load the page
    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("donation", new Donation());
        return "home"; 
    }

    // Handles the data when they click "Submit"
    @PostMapping("/submitDonation")
    public String processDonation(@Valid Donation donation, BindingResult result, Model model) {
        // If the @Size or @Pattern rules are broken, send them back to the form with errors!
        if (result.hasErrors()) {
            return "home"; 
        }
        
        // If it passes security, show a success message
        model.addAttribute("successMessage", "Donation processed securely!");
        return "home";
    }
}