package com.example.noormart.Service;

import com.example.noormart.Payloads.LocalUserDto;
import com.example.noormart.Payloads.PageableResponse;

import java.util.List;

public interface LocalUserService {
    LocalUserDto registerUser(LocalUserDto localUserDto);
    LocalUserDto updateUser(Long id,LocalUserDto localUserDto);
    void deleteUser(Long id);
    LocalUserDto getUser(Long id);
    PageableResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDirect);
    List<LocalUserDto> searchUser(String firstName);
    String authorizeUser(Long userId,int roleId);
}
