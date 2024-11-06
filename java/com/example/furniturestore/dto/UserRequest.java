package com.example.furniturestore.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {
    private String username;

    @Size(min = 8, message = "Mat khau phai tren 8 ki tu")
    private String password;
    private String email;
}