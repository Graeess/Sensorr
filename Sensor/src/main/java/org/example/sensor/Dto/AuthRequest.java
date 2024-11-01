package org.example.sensor.Dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    private String username;
    private String password;

    public AuthRequest(String testUser, String testPass) {
        username = testUser;
        password = testPass;
    }
}