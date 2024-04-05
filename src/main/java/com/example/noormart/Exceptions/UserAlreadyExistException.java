package com.example.noormart.Exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAlreadyExistException extends RuntimeException{
    private String user;
    private String email;

    public UserAlreadyExistException(String user, String email) {
        super(String.format("%s with user email %s is already exist.",user,email));
        this.user = user;
        this.email = email;
    }
}
