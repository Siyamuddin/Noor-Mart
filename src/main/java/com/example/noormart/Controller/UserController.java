package com.example.noormart.Controller;

import com.example.noormart.Payloads.NewUserRegistrationRequest;
import com.example.noormart.Payloads.PaymentDto;
import com.example.noormart.Payloads.Responses.ApiResponse;
import com.example.noormart.Payloads.LocalUserDto;
import com.example.noormart.Service.LocalUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User")
@SecurityRequirement(name = "JWT-Auth")
public class UserController {
    @Autowired
    private LocalUserService localUserService;
    @Operation(
            summary = "Update user",
            description = "Update user information.")
    @PutMapping("/update/{id}")
    public ResponseEntity<LocalUserDto> updateUser(@RequestBody NewUserRegistrationRequest localUserDto, @PathVariable Long id)
    {
        LocalUserDto updatedUser=localUserService.updateUser(id,localUserDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    @Operation(
            summary = "Delete User",
            description = "Delete user by providing user ID.")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id)
    {
        localUserService.deleteUser(id);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage("User deleted successfully.");
        apiResponse.setSuccess(true);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }
    @Operation(
            summary = "Get a single User",
            description = "Get a single user details by providing user ID.")
    @GetMapping("/get/{id}")
    public ResponseEntity<LocalUserDto> getLocalUser(@PathVariable Long id)
    {
        LocalUserDto localUserDto=localUserService.getUser(id);
        return new ResponseEntity<LocalUserDto>(localUserDto,HttpStatus.OK);
    }

    @PostMapping("/pay/{purchaseId}")
    public ResponseEntity<PaymentDto> pay(@RequestParam(value = "paid",defaultValue = "unpaid",required = true) boolean paid,
                                          @RequestParam(value = "method",defaultValue = "bank",required = true) String paymethod,
                                          @PathVariable Long purchaseId)
    {
        PaymentDto paymentDto=localUserService.updatePayment(purchaseId,paid,paymethod);
        return new ResponseEntity<>(paymentDto,HttpStatus.OK);
    }

}
