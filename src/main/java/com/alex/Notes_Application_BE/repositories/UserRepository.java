package com.alex.Notes_Application_BE.repositories;

import com.alex.Notes_Application_BE.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String username);

    boolean existsByUserName(String username);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
