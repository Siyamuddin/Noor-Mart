package com.example.noormart.Controller;

import com.example.noormart.Configuration.AppConstants;
import com.example.noormart.Payloads.ApiResponse;
import com.example.noormart.Payloads.LocalUserDto;
import com.example.noormart.Payloads.PageableResponse;
import com.example.noormart.Service.LocalUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User")
@SecurityRequirement(name = "JWT-Auth")
public class UserController {
    @Autowired
    private LocalUserService localUserService;
    @PutMapping("/update/{id}")
    public ResponseEntity<LocalUserDto> updateUser(@RequestBody LocalUserDto localUserDto, @PathVariable Long id)
    {
        LocalUserDto updatedUser=localUserService.updateUser(id,localUserDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id)
    {
        localUserService.deleteUser(id);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage("User deleted successfully.");
        apiResponse.setSuccess(true);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<LocalUserDto> getLocalUser(@PathVariable Long id)
    {
        LocalUserDto localUserDto=localUserService.getUser(id);
        return new ResponseEntity<LocalUserDto>(localUserDto,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/all")
    public ResponseEntity<PageableResponse> getAllUsers(@RequestParam(value ="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                        @RequestParam(value ="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                        @RequestParam(value ="sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                        @RequestParam(value ="sortDirect",defaultValue = AppConstants.SORT_DIRECT,required = false) String sortDirect){
         PageableResponse pageableResponse=localUserService.getAllUsers(pageNumber,pageSize,sortBy,sortDirect);
        return new ResponseEntity<PageableResponse>(pageableResponse,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<LocalUserDto>> searchUser(@PathVariable("keyword") String keyword)
    {
        List<LocalUserDto> localUserDto=localUserService.searchUser(keyword);
        return new ResponseEntity<List<LocalUserDto>>(localUserDto,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/authorize/{userId}/{roleId}")
    ResponseEntity<String> authorize(@PathVariable Long userId,@PathVariable int roleId)
    {
        String string= localUserService.authorizeUser(userId,roleId);
        return new ResponseEntity<>(string,HttpStatus.OK);
    }
}
