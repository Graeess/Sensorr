package org.example.sensor.ServiceTest;

import org.example.sensor.Entity.User;
import org.example.sensor.Repository.UserRepository;
import org.example.sensor.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Инициализация моков
    }

    @Test
    public void testRegisterUser_Success() {
        String username = "testUser";
        String password = "testPass";

        // Настраиваем мок так, чтобы при вызове save возвращался наш тестовый пользователь
        when(passwordEncoder.encode("testPass")).thenReturn("encodedPassword");
        when(userRepository.findByUsername(username)).thenReturn(null); // Нет пользователя с таким именем
        // Настраиваем UserRepository, чтобы он возвращал сохранённого пользователя
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User registeredUser = userService.registerUser(username, password);

        assertNotNull(registeredUser);
        assertEquals(username, registeredUser.getUsername());
        verify(userRepository).save(any(User.class)); // Проверяем, что метод save был вызван
    }

    @Test
    public void testRegisterUser_UsernameAlreadyExists() {
        String username = "testUser";
        String password = "testPass";

        User existingUser = new User();
        existingUser.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(existingUser); // Пользователь с таким именем уже существует

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(username, password);
        });

        assertEquals("User with this username already exists", exception.getMessage());
        verify(userRepository, never()).save(any(User.class)); // Проверяем, что save не был вызван
    }

    @Test
    public void testLoadUserByUsername_UserFound() {
        String username = "testUser";

        when(passwordEncoder.encode("testPass")).thenReturn("encodedPassword");

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode("testPass")); // Эмулируем закодированный пароль

        when(userRepository.findByUsername(username)).thenReturn(user); // Пользователь найден

        UserDetails foundUser = userService.loadUserByUsername(username);

        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String username = "nonExistentUser";

        when(userRepository.findByUsername(username)).thenReturn(null); // Пользователь не найден

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(username);
        });
    }
}
