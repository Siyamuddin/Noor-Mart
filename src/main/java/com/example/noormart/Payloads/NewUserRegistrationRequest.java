package com.example.noormart.Payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class NewUserRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
