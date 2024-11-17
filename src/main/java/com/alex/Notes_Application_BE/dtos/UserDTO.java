package com.alex.Notes_Application_BE.dtos;


import com.alex.Notes_Application_BE.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String userName;
    private String email;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private  boolean credentialsNonExpired;
    private boolean enabled;
    private LocalDateTime credentialsExpiryDate;
    private LocalDateTime accountExpiryDate;
    private String twofactorSecret;
    private boolean isTwoFactorEnabled;
    private String signUpMethod;
    private Role role;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
