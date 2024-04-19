package com.example.noormart.Payloads;

import com.example.noormart.Model.LocalUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class BuyDto {
        private Long buyId;
        private LocalUserDto localUser;
        private Double totalAmount;
        private Date buyingTime;
}
