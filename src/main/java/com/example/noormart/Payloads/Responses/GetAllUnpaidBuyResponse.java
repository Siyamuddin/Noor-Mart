package com.example.noormart.Payloads.Responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllUnpaidBuyResponse {
    private Long purchaseId;
    private String userName;
    private String userEmail;
    private Double totalAmount;
    private Date purchaseTime;
    private boolean paid;
}
