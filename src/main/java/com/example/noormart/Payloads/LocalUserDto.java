package com.example.noormart.Payloads;

import com.example.noormart.Model.Chart;
import com.example.noormart.Model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class LocalUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<RoleDto> roles=new HashSet<>();
    private ChartDto chart;

}
