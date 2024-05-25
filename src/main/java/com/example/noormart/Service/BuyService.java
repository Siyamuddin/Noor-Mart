package com.example.noormart.Service;

import com.example.noormart.Payloads.BuyDto;
import com.example.noormart.Payloads.Responses.GetAllUnpaidBuyResponse;
import com.example.noormart.Payloads.Responses.SellResponse;

import java.util.Date;
import java.util.List;

public interface BuyService {
    BuyDto buyProducts(Long userId,boolean paid,String payMethod) throws InterruptedException;
    List<GetAllUnpaidBuyResponse> getBuyByUser(Long userId);
    List<GetAllUnpaidBuyResponse> getTotalUnpaidBuys();
    List<GetAllUnpaidBuyResponse> getAllBuyByDate(Date date);
    SellResponse getTotalSellDataByDate(Date date);

}
