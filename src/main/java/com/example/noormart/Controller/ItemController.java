package com.example.noormart.Controller;

import com.example.noormart.Payloads.ApiResponse;
import com.example.noormart.Payloads.ItemDto;
import com.example.noormart.Service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/item")
@SecurityRequirement(name = "JWT-Auth")
@Tag(name = "Add to Chart")
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Operation(
            summary = "Add to Chart",
            description = "Add product to chart by providing user ID product ID and product quantity.")
    @PostMapping("/add/user/{userId}/product/{productId}/quantity/{quantity}")
    public ResponseEntity<ItemDto> createItem(@PathVariable Long userId,@PathVariable Long productId,@PathVariable Integer quantity){
        ItemDto itemDto=itemService.createItem(userId,productId,quantity);
        return new ResponseEntity<ItemDto>(itemDto, HttpStatus.CREATED);
    }
    @Operation(
            summary = "Update the chart",
            description = "Provide item ID to update Item in chart")
    @PutMapping("/update/itemId/{itemId}/quantity/{quantity}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable Long itemId,@PathVariable Integer quantity){
        ItemDto itemDto=itemService.updateItem(itemId,quantity);
        return new ResponseEntity<ItemDto>(itemDto, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Remove item from a chart",
            description = "Remove item form a chart.")
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Long itemId)
    {
        itemService.deleteItem(itemId);
        ApiResponse apiResponse=new ApiResponse("Item has been delete successfully",true);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }
}
