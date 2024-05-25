package com.example.noormart.Payloads.Responses;

import com.example.noormart.Model.Buy;
import com.example.noormart.Model.Chart;
import com.example.noormart.Model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class BuyEmailResponse {
    public static String getProductInfo(List<Item> items) {

        // Create a list to store product information
        List<String> productInfo = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            productInfo.add(String.format("%s: $%.2f", items.get(i).getProduct().getName(),items.get(i).getProduct().getPrice()));
        }

        // Join the product information list into a string with newlines
        String str=String.join("\n", productInfo);
        return str;
    }

    public String response(Buy buy){

        double total=buy.getTotalAmount();

        String paymentMethod=buy.getPayment().getPaymentMethod();

        boolean paymentStatus=buy.getPayment().isPaid();

        Chart chart=buy.getLocalUser().getChart();

//        List<String> productName=chart.getItems().stream().map((item)-> item.getProduct().getName()).collect(Collectors.toList());
//        List<Double> productPrice= chart.getItems().stream().map((item)-> item.getProduct().getPrice()).collect(Collectors.toList());
        List<Item>  itemList=chart.getItems();



        return "Total: "+total+"\nPayment Method: "+paymentMethod+"\nPayment Status: "+paymentStatus+"\n"+getProductInfo(itemList);

    }
}
