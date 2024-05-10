package com.example.noormart.Repository;

import com.example.noormart.Model.LocalUser;
import com.example.noormart.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<LocalUser,Long> {
    Optional<LocalUser> findByEmail(String email);

    List<LocalUser> findByFirstNameContaining(String title);
}

