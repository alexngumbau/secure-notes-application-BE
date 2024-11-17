package com.alex.Notes_Application_BE.services;

import com.alex.Notes_Application_BE.dtos.UserDTO;
import com.alex.Notes_Application_BE.models.Role;
import com.alex.Notes_Application_BE.models.User;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    void updateUserRole(Long userId, String roleName);

    UserDTO getUserById(Long id);

    User findByUsername(String username);

    void updateAccountLockStatus(Long userId, boolean lock);

    List<Role> getAllRoles();

    void updateExpiryStatus(Long userId, boolean expire);

    void updateCredentialsExpiryStatus(Long userId, boolean expire);

    void updateAccountEnabledStatus(Long userId, boolean enabled);

    void updatePassword(Long userId, String password);

    void generatePasswordResetToken(String email);

    void resetPassword(String token, String newPassword);

    Optional<User> findByEmail(String email);

    User registerUser(User newUser);

    GoogleAuthenticatorKey generate2FASecret(Long userId);

    boolean validate2FACode(Long userId, int code);

    void enable2FA(Long userId);

    void disable2FA(Long userId);
}
