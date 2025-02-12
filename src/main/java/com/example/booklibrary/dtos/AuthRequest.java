package com.example.booklibrary.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
}
