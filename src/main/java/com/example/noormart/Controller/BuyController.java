package com.example.noormart.Controller;

import com.example.noormart.Payloads.BuyDto;
import com.example.noormart.Service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/buy")
public class BuyController {
    @Autowired
    private BuyService buyService;
    @PostMapping("/userChart/{userId}")
    public ResponseEntity<BuyDto> buyProduct(@PathVariable Long userId,
                                             @RequestParam(value = "paid",defaultValue = "false",required = true)boolean paid,
                                             @RequestParam(value = "method",defaultValue = "bank",required = false)String method)
    {
        BuyDto buyDto=buyService.buyProducts(userId,paid,method);
        return new ResponseEntity<BuyDto>(buyDto, HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<BuyDto>> getBuyByUser(@PathVariable Long id)
    {
        List<BuyDto> buyDtoList=buyService.getBuyByUser(id);
        return new ResponseEntity<List<BuyDto>>(buyDtoList,HttpStatus.OK);
    }
    @GetMapping("/all/unpaid")
    public ResponseEntity<List<BuyDto>> getAllUnpaidBuy()
    {
        List<BuyDto> buyDtoList=buyService.getUnpaidBuys();
        return new ResponseEntity<List<BuyDto>>(buyDtoList,HttpStatus.OK);
    }
    @GetMapping("/all/byDate")
    public ResponseEntity<List<BuyDto>> getAllBuyByDate(@RequestParam(value = "time", required = false) String time) {
        // Handle cases where time parameter is missing or empty
        if (time == null || time.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }

        // Use a date parsing library for reliable parsing
        try {
            // Replace "yyyy-MM-dd" with the expected format from your data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(time, formatter);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            List<BuyDto> buyDtoList = buyService.getBuyByDate(date);
            return new ResponseEntity<>(buyDtoList, HttpStatus.OK);
        } catch (ParseException e) {
            // Handle parsing exception (log the error or return a bad request response)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
