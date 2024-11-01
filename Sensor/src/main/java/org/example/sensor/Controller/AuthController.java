package org.example.sensor.Controller;

import org.example.sensor.Config.JwtUtils;
import org.example.sensor.Dto.AuthRequest;
import org.example.sensor.Entity.User;
import org.example.sensor.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public String createToken(@RequestBody AuthRequest authRequest) {
        try {
            // Аутентификация пользователя
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // Получаем детали пользователя
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

            // Генерируем токен
            return jwtUtils.generateToken(userDetails.getUsername());
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException("User not found: " + authRequest.getUsername(), e);
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
        }
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody AuthRequest authRequest) {
        // Логика регистрации пользователя
        return userService.registerUser(authRequest.getUsername(), authRequest.getPassword());
    }
}
