package com.example.noormart.Service.ServiceImpl;

import com.example.noormart.Configuration.AppConstants;
import com.example.noormart.Exceptions.ResourceNotFoundException;
import com.example.noormart.Exceptions.UserAlreadyExistException;
import com.example.noormart.Model.LocalUser;
import com.example.noormart.Model.Role;
import com.example.noormart.Payloads.LocalUserDto;
import com.example.noormart.Repository.RoleRepo;
import com.example.noormart.Repository.UserRepo;
import com.example.noormart.Service.LocalUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements LocalUserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public LocalUserDto registerUser(LocalUserDto localUserDto) {
        Optional<LocalUser> localUser=userRepo.findByEmail(localUserDto.getEmail());
        int count= (int) userRepo.count();
        if(localUser.isEmpty() && count==0)
        {
            LocalUser localUser1=modelMapper.map(localUserDto,LocalUser.class);
            localUser1.setPassword(passwordEncoder.encode(localUserDto.getPassword()));
            Role role=roleRepo.findById(AppConstants.ADMIN_USER).orElseThrow(()-> new ResourceNotFoundException("Role","Role ID",AppConstants.ADMIN_USER));
            localUser1.getRoles().add(role);
            LocalUser registerdUser=userRepo.save(localUser1);
            return modelMapper.map(registerdUser,LocalUserDto.class);
        }
        else if(localUser.isEmpty())
        {
            LocalUser localUser1=modelMapper.map(localUserDto,LocalUser.class);
            localUser1.setPassword(passwordEncoder.encode(localUserDto.getPassword()));
            Role role=roleRepo.findById(AppConstants.ADMIN_USER).orElseThrow(()-> new ResourceNotFoundException("Role","Role ID",AppConstants.ADMIN_USER));
            localUser1.getRoles().add(role);
            LocalUser registerdUser=userRepo.save(localUser1);
            return modelMapper.map(registerdUser,LocalUserDto.class);

        }
        else throw new UserAlreadyExistException("User",localUserDto.getEmail());
    }

    @Override
    public LocalUserDto updateUser(Long id, LocalUserDto localUserDto) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }

    @Override
    public LocalUserDto getUser(Long id) {
        return null;
    }

    @Override
    public LocalUserDto getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDirect) {
        return null;
    }

    @Override
    public List<LocalUserDto> searchUser(String firstName) {
        return null;
    }
}
