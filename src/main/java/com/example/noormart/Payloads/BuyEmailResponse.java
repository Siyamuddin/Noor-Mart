package com.example.noormart.Payloads;

import com.example.noormart.Model.Buy;
import com.example.noormart.Model.Chart;
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
    public static String getProductInfo(List<String> productNames, List<Double> productPrices) {
        // Input validation
        if (productNames.size() != productPrices.size()) {
            throw new IllegalArgumentException("Length of productNames and productPrices must be equal");
        }

        // Create a list to store product information
        List<String> productInfo = new ArrayList<>();
        for (int i = 0; i < productNames.size(); i++) {
            productInfo.add(String.format("%s: $%.2f", productNames.get(i), productPrices.get(i)));
        }

        // Join the product information list into a string with newlines
        return String.join("\n", productInfo);
    }

    public String response(Buy buy, Chart chart){

        double total=buy.getTotalAmount();
        String paymentMethod=buy.getPayment().getPaymentMethod();
        boolean paymentStatus=buy.getPayment().isPaid();
        List<String> productName=chart.getItems().stream().map((item)-> item.getProduct().getName()).collect(Collectors.toList());
        List<Double> productPrice= chart.getItems().stream().map((item)-> item.getProduct().getPrice()).collect(Collectors.toList());
        String productInfo=getProductInfo(productName,productPrice);
        log.info("OutPut: "+productInfo);String finalinfo=String.format(productInfo+"\nTotal: %.2f\nPayment Method: %s\nPayment Status: %s",total,paymentMethod,paymentStatus);

        return finalinfo;
    }
}
