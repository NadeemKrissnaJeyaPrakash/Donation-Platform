package com.codelock.donation.model;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Donation {

    // SR6: Buffer Overflow Protection
    @Size(max = 20, message = "[SECURITY ALERT] Buffer Overflow Prevented: Message exceeds 20 characters.")
    
    // SR2: Input Sanitization (Only allows letters, numbers, and basic punctuation)
    @Pattern(regexp = "^[a-zA-Z0-9 .,!?]*$", message = "[SECURITY ALERT] Input Sanitization: Malicious characters or scripts blocked.")
    private String donorMessage;

    // Getters and Setters (Required for Spring to read the data)
    public String getDonorMessage() {
        return donorMessage;
    }

    public void setDonorMessage(String donorMessage) {
        this.donorMessage = donorMessage;
    }
}