package com.example.noormart.Service;

import com.example.noormart.Payloads.LocalUserDto;
import com.example.noormart.Payloads.NewUserRegistrationRequest;
import com.example.noormart.Payloads.PaymentDto;
import com.example.noormart.Payloads.Responses.PageableResponse;

import java.util.List;

public interface LocalUserService {
    LocalUserDto registerUser(NewUserRegistrationRequest localUserDto);
    LocalUserDto updateUser(Long id,NewUserRegistrationRequest localUserDto);
    void deleteUser(Long id);
    LocalUserDto getUser(Long id);
    PageableResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDirect);
    List<LocalUserDto> searchUser(String firstName);
    String authorizeUser(Long userId,int roleId);
    PaymentDto updatePayment(Long buyId, boolean paid, String method);

}
