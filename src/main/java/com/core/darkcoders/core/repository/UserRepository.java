package com.core.darkcoders.core.repository;

import com.core.darkcoders.core.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmail(String email);
    Optional<AppUser> findByMobileNumber(String mobileNumber);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByMobileNumber(String mobileNumber);
} 