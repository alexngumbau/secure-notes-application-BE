package com.alex.Notes_Application_BE.security;

import com.alex.Notes_Application_BE.config.Oauth2LoginSuccessHandler;
import com.alex.Notes_Application_BE.models.AppRole;
import com.alex.Notes_Application_BE.models.Role;
import com.alex.Notes_Application_BE.models.User;
import com.alex.Notes_Application_BE.repositories.RoleRepository;
import com.alex.Notes_Application_BE.repositories.UserRepository;
import com.alex.Notes_Application_BE.security.jwt.AuthEntryPointJwt;
import com.alex.Notes_Application_BE.security.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Autowired
    @Lazy
    private Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;

    @Value("${frontend.url}")
    String frontendUrl;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return  new AuthTokenFilter();
    }


    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/auth/public/**").permitAll()
                        .requestMatchers("/oauth2/**").permitAll()
                        .requestMatchers("/api/csrf-token").permitAll()
                        .anyRequest().authenticated())
                        .oauth2Login(oauth2 -> {

                            oauth2.successHandler(oauth2LoginSuccessHandler);
                })
                .csrf(csrf ->
                        csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                                .ignoringRequestMatchers("/api/auth/public/**")
                )
                .cors(cors ->
                        cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new CustomLoggingFilter(), UsernamePasswordAuthenticationFilter.class)
//                .addFilterAfter(new RequestValidationFilter(), CustomLoggingFilter.class)
//                .httpBasic(withDefaults())
                .build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList(frontendUrl));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(Arrays.asList("*"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CommandLineRunner initData(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            Role userRole = roleRepository.findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_USER)));
            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() -> roleRepository.save(new Role(AppRole.ROLE_ADMIN)));

            if (!userRepository.existsByUserName("user1")) {
                User user1 = new User("user1", "user1@example.com", passwordEncoder().encode("password1"));
                user1.setAccountNonLocked(false);
                user1.setAccountNonExpired(true);
                user1.setCredentialsNonExpired(true);
                user1.setEnabled(true);
                user1.setCredentialsExpiryDate(LocalDateTime.now().plusMinutes(60));
                user1.setAccountExpiryDate(LocalDateTime.now().plusMinutes(60));
                user1.setTwoFactorEnabled(false);
                user1.setSignUpMethod("email");
                user1.setRole(userRole);
                userRepository.save(user1);
            }

            if (!userRepository.existsByUserName("admin")) {
                User admin = new User("admin", "admin@example.com", passwordEncoder().encode("adminPass"));
                admin.setAccountNonLocked(false);
                admin.setAccountNonExpired(true);
                admin.setCredentialsNonExpired(true);
                admin.setEnabled(true);
                admin.setCredentialsExpiryDate(LocalDateTime.now().plusMinutes(60));
                admin.setAccountExpiryDate(LocalDateTime.now().plusMinutes(60));
                admin.setTwoFactorEnabled(false);
                admin.setSignUpMethod("email");
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // Using Jdbc
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
//        if (!manager.userExists("user1")) {
//            manager.createUser(
//                    User.withUsername("user1")
//                            .password("{noop}password1")
//                            .roles("USER")
//                            .build()
//            );
//        }
//        if (!manager.userExists("admin")) {
//            manager.createUser(
//                    User.withUsername("admin")
//                            .password("{noop}adminPass")
//                            .roles("ADMIN")
//                            .build()
//            );
//        }
//        return manager;
//    }

//    // Using in memory
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        if (!manager.userExists("user1")) {
//            manager.createUser(
//                    User.withUsername("user1")
//                            .password("{noop}password1")
//                            .roles("USER")
//                            .build()
//            );
//        }
//        if (!manager.userExists("admin")) {
//            manager.createUser(
//                    User.withUsername("admin")
//                            .password("{noop}adminPass")
//                            .roles("ADMIN")
//                            .build()
//            );
//        }
//        return manager;
//    }



}
