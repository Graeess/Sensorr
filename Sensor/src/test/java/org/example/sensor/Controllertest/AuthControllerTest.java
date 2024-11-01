package org.example.sensor.Controllertest;

import org.checkerframework.checker.units.qual.A;
import org.example.sensor.Controller.AuthController;
import org.example.sensor.Dto.AuthRequest;
import org.example.sensor.Entity.User;
import org.example.sensor.Config.JwtUtils;
import org.example.sensor.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserService userService;

    @Mock
    private UserDetailsService userDetailsService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testRegisterUser() {
        AuthRequest authRequest = new AuthRequest("testUser", "testPass");
        User mockUser = new User();
        mockUser.setUsername("testUser");

        when(userService.registerUser(authRequest.getUsername(), authRequest.getPassword())).thenReturn(mockUser);

        User user = authController.registerUser(authRequest);
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
        verify(userService).registerUser(authRequest.getUsername(), authRequest.getPassword());
    }

    @Test
    public void testCreateToken() throws Exception {
        String username = "testUser";
        String password = "testPassword";
        String token = "mockToken";

        // Mock the behavior for generating the token
        when(jwtUtils.generateToken(username)).thenReturn(token);

        // Mock authentication
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userDetailsService.loadUserByUsername(any(String.class))).thenReturn(new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return "testPassword";
            }

            @Override
            public String getUsername() {
                return "testUser";
            }
        });

        // Perform the login logic
        AuthRequest authRequest = new AuthRequest(username, password);
        String generatedToken = authController.createToken(authRequest);

        // Assertions
        assertEquals(token, generatedToken);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtUtils).generateToken(username);
    }
}
