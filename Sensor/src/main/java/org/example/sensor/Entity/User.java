package org.example.sensor.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    // Реализуем методы UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(); // Здесь можно добавить роли, если нужно
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Можно добавить логику для проверки статуса аккаунта
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Логика для блокировки аккаунта
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Логика для проверки срока действия учетных данных
    }

    @Override
    public boolean isEnabled() {
        return true; // Логика для учета статуса пользователя
    }
}