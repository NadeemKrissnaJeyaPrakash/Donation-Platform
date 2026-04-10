package com.codelock.donation.repository;

import com.codelock.donation.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, Long> {
    // Spring magically turns these method names into SQL queries!
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}