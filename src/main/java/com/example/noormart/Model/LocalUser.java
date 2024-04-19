package com.example.noormart.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "local_user")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class LocalUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = true,length = 1000)
    private String password;
    @Column(nullable = false,unique = true,length = 320)
    private String email;
    @Column(nullable = false,unique = true)
    private String firstName;
    @Column(nullable = false,unique = true)
    private String lastName;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Chart chart;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Buy> buyList=new ArrayList<>();


    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
            joinColumns = @JoinColumn(name="user",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name="role",referencedColumnName = "id"))
    private Set<Role> roles=new HashSet<>();

    @CreationTimestamp
    private Date UserCreated;
    @UpdateTimestamp
    private Date UserUpdated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities=  this.roles.stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
