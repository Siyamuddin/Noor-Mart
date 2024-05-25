package com.example.noormart.Service.ServiceImpl;

import com.example.noormart.Configuration.AppConstants;
import com.example.noormart.Exceptions.ResourceNotFoundException;
import com.example.noormart.Exceptions.UnpaidBillException;
import com.example.noormart.Exceptions.UserAlreadyExistException;
import com.example.noormart.Model.Buy;
import com.example.noormart.Model.Chart;
import com.example.noormart.Model.LocalUser;
import com.example.noormart.Model.Role;
import com.example.noormart.Payloads.LocalUserDto;
import com.example.noormart.Payloads.NewUserRegistrationRequest;
import com.example.noormart.Payloads.PaymentDto;
import com.example.noormart.Payloads.Responses.ApiResponse;
import com.example.noormart.Payloads.Responses.PageableResponse;
import com.example.noormart.Repository.BuyRepo;
import com.example.noormart.Repository.ChartRepo;
import com.example.noormart.Repository.RoleRepo;
import com.example.noormart.Repository.UserRepo;
import com.example.noormart.Service.LocalUserService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    ChartRepo chartRepo;
    @Autowired
    private MailSendingService mailSendingService;
    @Autowired
    private BuyRepo buyRepo;

    @Override
    @Transactional
    public LocalUserDto registerUser(NewUserRegistrationRequest localUserDto) {
        Optional<LocalUser> localUser=userRepo.findByEmail(localUserDto.getEmail());
        int count= (int) userRepo.count();
        if(localUser.isEmpty() && count==0)
        {
            LocalUser localUser1=modelMapper.map(localUserDto,LocalUser.class);
            localUser1.setPassword(passwordEncoder.encode(localUserDto.getPassword()));
            Role role=roleRepo.findById(Math.toIntExact(AppConstants.ADMIN_USER)).orElseThrow(()-> new ResourceNotFoundException("Role","ROle ID",AppConstants.ADMIN_USER));
            localUser1.getRoles().add(role);
            Chart chart=new Chart();
            chartRepo.save(chart);
            localUser1.setChart(chart);
            LocalUser registerdUser=userRepo.save(localUser1);
            return modelMapper.map(registerdUser, LocalUserDto.class);
        }
        else if(localUser.isEmpty())
        {
            LocalUser localUser1=modelMapper.map(localUserDto,LocalUser.class);
            localUser1.setPassword(passwordEncoder.encode(localUserDto.getPassword()));
            Role role=roleRepo.findById(Math.toIntExact(AppConstants.NORMAL_USER)).orElseThrow(()-> new ResourceNotFoundException("Role","ROle ID",AppConstants.NORMAL_USER));
            localUser1.getRoles().add(role);
            Chart chart=new Chart();
            chartRepo.save(chart);
            localUser1.setChart(chart);
            LocalUser registerdUser=userRepo.save(localUser1);
            mailSendingService.sendEmail(registerdUser.getEmail(), "NoorMart",String.format("Name: %s %s\nEmail: %s\nRole: %s\nThank you" +
                                                " for creating a new account in NoorMart.\nEnjoy your shopping.",registerdUser.getFirstName(),registerdUser.getLastName(),registerdUser.getEmail(),registerdUser.getRoles()));
            return modelMapper.map(registerdUser,LocalUserDto.class);

        }
        else throw new UserAlreadyExistException("User",localUserDto.getEmail());
    }

    @Override
    public LocalUserDto updateUser(Long id, NewUserRegistrationRequest localUserDto) {
        LocalUser localUser=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","User Id",id));
        localUser.setFirstName(localUserDto.getFirstName());
        localUser.setLastName(localUserDto.getLastName());
        localUser.setEmail(localUserDto.getEmail());
        localUser.setPassword(passwordEncoder.encode(localUserDto.getPassword()));
        LocalUser localUser1=userRepo.save(localUser);
        return modelMapper.map(localUser1,LocalUserDto.class);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        LocalUser localUser=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","User Id",id));
        List<Buy> userBuy=buyRepo.findAllByLocalUser(localUser);
        int count=0;
        for(int i=0;i<userBuy.size();i++)
        {
            if(userBuy.get(i).getPayment().isPaid()==false)
            {
                count++;
            }
        }
        if(count==0)
        {
            localUser.getRoles().clear();
            userRepo.save(localUser);
            buyRepo.deleteAllByLocalUserId(id);
            userRepo.deleteById(id);

        }
        else
        {
            throw new UnpaidBillException(count);
        }
    }

    @Override
    public LocalUserDto getUser(Long id) {
        LocalUser localUser=userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User","User Id",id));

        return modelMapper.map(localUser,LocalUserDto.class);
    }

    @Override
    public PageableResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDirect) {
        Sort sort;
        if(sortDirect.equalsIgnoreCase("asc"))
        {
            sort=Sort.by(sortBy).ascending();
        }
        else sort=Sort.by(sortBy).descending();

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<LocalUser> localUserPage=userRepo.findAll(pageable);
        List<LocalUserDto> localUserDtoList=localUserPage.stream().map((user)->modelMapper.map(user,LocalUserDto.class)).collect(Collectors.toList());
        PageableResponse pageableResponse=new PageableResponse();
        pageableResponse.setLocalUserDtoList(localUserDtoList);
        pageableResponse.setPageNumber(localUserPage.getNumber());
        pageableResponse.setPageSize(localUserPage.getSize());
        pageableResponse.setTotalPages(localUserPage.getTotalPages());
        pageableResponse.setTotalElements((int) localUserPage.getTotalElements());
       pageableResponse.setLast(localUserPage.isLast());
        return pageableResponse;
    }

    @Override
    public List<LocalUserDto> searchUser(String keyword) {
        List<LocalUser> localUsers=userRepo.findByFirstNameContaining(keyword);
        List<LocalUserDto> localUserDtoList=localUsers.stream().map((user)-> modelMapper.map(user,LocalUserDto.class)).collect(Collectors.toList());

        return localUserDtoList;
    }

    @Override
    public String authorizeUser(Long userId,int roleId) {
        LocalUser localUser=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User Id",userId));
        Optional<Role> role= Optional.ofNullable(roleRepo.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role","Role ID",(long)roleId)));
        localUser.getRoles().clear();
        localUser.getRoles().add(role.get());
        LocalUser saved=userRepo.save(localUser);
        return String.format("%s has a new role as %s",localUser.getFirstName(),role.get().getName());
    }

    @Override
    public PaymentDto updatePayment(Long buyId, boolean paid, String method) {
        Buy buy=buyRepo.findById(buyId).orElseThrow(()-> new ResourceNotFoundException("Buy","buy ID",buyId));
        buy.getPayment().setPaymentMethod(method);
        buy.getPayment().setPaid(paid);
        Buy savedBuy=buyRepo.save(buy);
        return modelMapper.map(savedBuy.getPayment(),PaymentDto.class);
    }
}
