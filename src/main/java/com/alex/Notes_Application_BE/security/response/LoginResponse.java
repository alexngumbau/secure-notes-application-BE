package com.alex.Notes_Application_BE.security.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String jwtToken;
    private String username;
    private List<String> roles;


}
