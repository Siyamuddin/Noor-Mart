package com.example.noormart.Payloads;

//import com.example.noormart.Model.Address;
import com.example.noormart.Model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocalUserDto {
    @NotEmpty
    @Size(min = 2,message = "Need at least 2 characters.")
    private String firstName;
    @NotEmpty
    @Size(min = 2,message = "Need at least 2 characters.")
    private String lastName;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 8,message = "Password must contain at least 8 characters or numbers")
    private String password;
//    private List<AddressDto> addressDtoList=new ArrayList<>();
    private Set<RoleDto> roles=new HashSet<>();
}
