package com.example.noormart.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JWTRequest {
    @Email(message = "Input must be an email address.")
    private String email;
    @NotBlank(message = "Please enter password")
    private String password;
}
