package com.example.noormart.Controller;

import com.example.noormart.Payloads.JWTRequest;
import com.example.noormart.Payloads.JWTResponse;
import com.example.noormart.Payloads.LocalUserDto;
import com.example.noormart.Payloads.NewUserRegistrationRequest;
import com.example.noormart.Security.JwtHelper;
import com.example.noormart.Service.LocalUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication",description = "Can be used to rigister and login ther user.")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private LocalUserService localUserServices;
    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Operation(
            summary = "Long in User",
            description = "Provide credentials that you gave while registering.")
    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());


        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        JWTResponse response = JWTResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

    //register new user api
    @Operation(
            summary = "Register User",
            description = "For this time please provide only user's firstName, LastName, email and password nothing else. If you register successfully you'll a conformation email.")
    @PostMapping("/register")
    public ResponseEntity<LocalUserDto> registerUser(@Valid @RequestBody NewUserRegistrationRequest newUserRegistrationRequest)
    {
        LocalUserDto registeredUser=this.localUserServices.registerUser(newUserRegistrationRequest);
        return new ResponseEntity<LocalUserDto>(registeredUser,HttpStatus.CREATED);
    }
}