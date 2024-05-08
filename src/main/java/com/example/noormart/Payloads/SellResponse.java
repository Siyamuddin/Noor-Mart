package com.example.noormart.Payloads;

import lombok.Data;

@Data
public class SellResponse {
    private Double TotalSell=0.0;
    private Double paid=0.0;
    private Double unPaid=0.0;
}
