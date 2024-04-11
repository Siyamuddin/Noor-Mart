package com.example.noormart.Service;

import com.example.noormart.Payloads.WebOrderQuantityDto;

public interface WebOrderQuantity {

    WebOrderQuantityDto createQuantity(WebOrderQuantityDto webOrderQuantityDto);
    WebOrderQuantityDto updateQuantity(WebOrderQuantityDto webOrderQuantityDto,Long id);

    void deleteQuantity(Long id);

}
