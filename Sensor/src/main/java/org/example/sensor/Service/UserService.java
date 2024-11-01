package org.example.sensor.Service;

import org.example.sensor.Entity.User;
import org.example.sensor.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Убедитесь, что это интерфейс

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User registerUser(String username, String password) {
        // Проверяем наличие пользователя
        if (userRepository.findByUsername(username) != null) {
            throw new RuntimeException("User with this username already exists");
        }

        // Создаем нового пользователя
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Кодируем пароль
        return userRepository.save(user); // Сохраняем пользователя
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Возвращаем объект UserDetails
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles("USER") // Здесь можно указать роли, если нужно
                .build();
    }
}
