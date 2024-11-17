package com.alex.Notes_Application_BE.repositories;

import com.alex.Notes_Application_BE.models.AppRole;
import com.alex.Notes_Application_BE.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
