package com.example.noormart.Controller;

import com.example.noormart.Payloads.BuyDto;
import com.example.noormart.Service.BuyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buy")
@SecurityRequirement(name = "JWT-Auth")
@Tag(name = "Purchese")
public class BuyController {
    @Autowired
    private BuyService buyService;
    @Operation(
            summary = "Purchase product",
            description = "A regular user can purchase product by providing payment method and status.")
    @PostMapping("/user/{userId}")
    public ResponseEntity<BuyDto> buyProduct(@PathVariable Long userId,
                                             @RequestParam(value = "paid",defaultValue = "false",required = true)boolean paid,
                                             @RequestParam(value = "method",defaultValue = "bank",required = false)String method) throws InterruptedException {
        BuyDto buyDto=buyService.buyProducts(userId,paid,method);
        return new ResponseEntity<BuyDto>(buyDto, HttpStatus.OK);
    }
    @Operation(
            summary = "Get Purchase History",
            description = "A regular user can check his/her purchase history.")
    @GetMapping("/userBuy/{userId}")
    public ResponseEntity<List<BuyDto>> getBuyByUser(@PathVariable Long userId)
    {
        List<BuyDto> buyDtoList=buyService.getBuyByUser(userId);
        return new ResponseEntity<List<BuyDto>>(buyDtoList,HttpStatus.OK);
    }

}
