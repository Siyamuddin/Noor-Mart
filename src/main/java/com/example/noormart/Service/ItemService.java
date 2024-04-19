package com.example.noormart.Service;

import com.example.noormart.Payloads.ItemDto;

public interface ItemService {
    ItemDto createItem(Long userId,Long productId,Integer quantity);
    ItemDto updateItem(Long itemId,Integer quantity);
    void deleteItem(Long itemId);
}
