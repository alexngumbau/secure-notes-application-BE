package com.alex.Notes_Application_BE.security.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoResponse {

    private Long id;
    private String username;
    private String email;
    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private LocalDateTime credentialsExpiryDate;
    private LocalDateTime accountExpiryDate;
    private boolean isTwoFactorEnabled;
    private List<String> roles;


}
