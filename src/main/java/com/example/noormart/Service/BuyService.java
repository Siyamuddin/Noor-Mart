package com.example.noormart.Service;

import com.example.noormart.Payloads.BuyDto;
import com.example.noormart.Payloads.SellResponse;

import java.util.Date;
import java.util.List;

public interface BuyService {
    BuyDto buyProducts(Long userId,boolean paid,String payMethod) throws InterruptedException;
    List<BuyDto> getBuyByUser(Long userId);
    List<BuyDto> getUnpaidBuys();
    List<BuyDto> getBuyByDate(Date date);
    SellResponse getSellData(Date date);

}
