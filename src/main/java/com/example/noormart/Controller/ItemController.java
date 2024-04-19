package com.example.noormart.Controller;

import com.example.noormart.Payloads.ApiResponse;
import com.example.noormart.Payloads.ItemDto;
import com.example.noormart.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @PostMapping("/add/user/{userId}/product/{productId}/quantity/{quantity}")
    public ResponseEntity<ItemDto> createItem(@PathVariable Long userId,@PathVariable Long productId,@PathVariable Integer quantity){
        ItemDto itemDto=itemService.createItem(userId,productId,quantity);
        return new ResponseEntity<ItemDto>(itemDto, HttpStatus.CREATED);
    }
    @PutMapping("/update/itemId/{itemId}/quantity/{quantity}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable Long itemId,@PathVariable Integer quantity){
        ItemDto itemDto=itemService.updateItem(itemId,quantity);
        return new ResponseEntity<ItemDto>(itemDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Long itemId)
    {
        itemService.deleteItem(itemId);
        ApiResponse apiResponse=new ApiResponse("Item has been delete successfully",true);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }
}
