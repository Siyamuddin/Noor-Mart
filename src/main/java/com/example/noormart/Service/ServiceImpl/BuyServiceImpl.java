package com.example.noormart.Service.ServiceImpl;

import com.example.noormart.Exceptions.ChartIsEmpty;
import com.example.noormart.Exceptions.ResourceNotFoundException;
import com.example.noormart.Model.*;
import com.example.noormart.Payloads.BuyDto;
import com.example.noormart.Payloads.BuyEmailResponse;
import com.example.noormart.Payloads.SellResponse;
import com.example.noormart.Repository.*;
import com.example.noormart.Service.BuyService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BuyServiceImpl implements BuyService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ChartRepo chartRepo;
    @Autowired
    private InventoryRepo inventoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BuyRepo buyRepo;
    @Autowired
    private  PaymentRepo paymentRepo;
    @Autowired
    private MailSendingService mailSendingService;
    @Override
    public BuyDto buyProducts(Long userId,boolean paid,String payMethod) throws InterruptedException {
        LocalUser localUser=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","user ID",userId));
        if (localUser.getChart().getItems().isEmpty()) {
            throw new ChartIsEmpty(userId);
        }
        else {
        Buy buy=new Buy();
        Payment payment=new Payment();
        Chart chart=localUser.getChart();
        List<Item> items=chart.getItems();
        for(int i=0;i<items.size();i++)
        {
            Inventory inventory=inventoryRepo.findByProductId(items.get(i).getProduct().getId());
            Integer amt=inventory.getQuantity()-items.get(i).getQuantity();
            inventory.setQuantity(amt);//update inventory quantity
            Inventory saved=inventoryRepo.save(inventory);//saved inventory
            items.get(i).getProduct().setAvailable(saved.getQuantity());//update product quantity
            productRepo.save(items.get(i).getProduct());//saved product quantity
        }

        buy.setLocalUser(localUser);
        buy.setTotalAmount(chart.getTotalAmount());
        buy.setPayment(payment);
        Buy savedBuy=buyRepo.save(buy);

            payment.setPaymentMethod(payMethod);
            payment.setPaid(paid);
            payment.setBuy(buy);
            Payment savedPay=paymentRepo.save(payment);


            chart.getItems().clear();
        chart.setTotalAmount(0.00);
        chartRepo.save(chart);
        userRepo.save(localUser);
        BuyEmailResponse buyEmailResponse=new BuyEmailResponse();
        mailSendingService.sendEmail(savedBuy.getLocalUser().getEmail(),"NoorMart Shopping ("+new Date().toString()+")",buyEmailResponse.response(savedBuy));
        return  modelMapper.map(savedBuy,BuyDto.class);}}

    @Override
    public List<BuyDto> getBuyByUser(Long userId) {
        LocalUser localUser=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User ID",userId));
        List<Buy> buyList=buyRepo.findAllByLocalUser(localUser);
        List<BuyDto> buyDtoList=buyList.stream().map((buy)-> modelMapper.map(buy,BuyDto.class)).collect(Collectors.toList());
        return buyDtoList;
    }

    @Override
    public List<BuyDto> getUnpaidBuys() {
        List<Payment> paymentList=paymentRepo.findByPaidFalse();
        List<Buy> buyList=new ArrayList<>();
        for(int i=0;i<paymentList.size();i++)
        {
           Buy buy=buyRepo.findByPayment(paymentList.get(i));
           buyList.add(buy);
        }
        List<BuyDto> buyDtoList=buyList.stream().map((buy)-> modelMapper.map(buy,BuyDto.class)).collect(Collectors.toList());

        return buyDtoList;
    }

    @Override
    public List<BuyDto> getBuyByDate(Date date) {
        List<Buy> buyList=buyRepo.findByBuyingTimeAfter(date);
        List<BuyDto> buyDtoList=buyList.stream().map((buy)-> modelMapper.map(buy,BuyDto.class)).collect(Collectors.toList());

        return buyDtoList;
    }

    @Override
    public SellResponse getSellData(Date date) {
        List<Payment> paymentList=paymentRepo.findAllByPaymentTimeAfterAndPaidFalse(date);
        List<Payment> paymentList1=paymentRepo.findAllByPaymentTimeAfterAndPaidTrue(date);
        SellResponse sellResponse=new SellResponse();
        for(int i=0;i<paymentList.size();i++)
        {
            double up= (sellResponse.getUnPaid())+(paymentList.get(i).getBuy().getTotalAmount());
            sellResponse.setUnPaid(up);
        }
        for(int i=0;i<paymentList1.size();i++)
        {
            double pd= (sellResponse.getPaid())+(paymentList1.get(i).getBuy().getTotalAmount());
            sellResponse.setPaid(pd);
        }
        sellResponse.setTotalSell((sellResponse.getPaid()+ sellResponse.getUnPaid()));
        return sellResponse;
    }

}


