package com.example.noormart.Service.ServiceImpl;

import com.example.noormart.Exceptions.ProductOutOfStockException;
import com.example.noormart.Exceptions.ResourceNotFoundException;
import com.example.noormart.Model.*;
import com.example.noormart.Payloads.ItemDto;
import com.example.noormart.Payloads.ProductDto;
import com.example.noormart.Repository.*;
import com.example.noormart.Service.ItemService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ItemRepo itemRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ChartRepo chartRepo;
    @Autowired
    private InventoryRepo inventoryRepo;
    @Override
    @Transactional
    public ItemDto createItem(Long userId,Long productId, Integer quantity) {
        //fetching user who is adding
        LocalUser localUser=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User Id",userId));
        //fetching selected product
        Product product=productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Produc","product ID",productId));
        //fetching the selected products inventory to check is the product is available or not
        Inventory inventory=inventoryRepo.findByProductId(productId);
        //Checking if the item is available or not
        if(inventory.getQuantity()<quantity) {
            throw new ProductOutOfStockException(productId,inventory.getQuantity());
        }
        else {
        //creating new Item
        Item item=new Item();
        item.setChart(localUser.getChart());
        product.setAvailable(inventory.getQuantity());
        Product saved=productRepo.save(product);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setTotalAmount(quantity*product.getPrice());//setting total amount of the product according to quantity.
        Item savedItem= itemRepo.save(item);//item is saved to database after setting all the fields.

        //after creating Item, now it's being added to the user chart.
        Chart chart=localUser.getChart();
        chart.getItems().add(item);
        //setting total amount of chart
        Double amount=chart.getTotalAmount()+item.getTotalAmount();
        chart.setTotalAmount(amount);
        chartRepo.save(chart);//saving the user chart
       userRepo.save(localUser);//saving the user
    return modelMapper.map(savedItem,ItemDto.class);
        }
    }

    @Override
    public ItemDto updateItem(Long itemId, Integer quantity) {
        Item item=itemRepo.findById(itemId).orElseThrow(()->new ResourceNotFoundException("Item","Item ID",itemId));
        item.setQuantity(quantity);
        item.setTotalAmount((item.getProduct().getPrice())*quantity);
        Item newItem=itemRepo.save(item);

        return modelMapper.map(newItem,ItemDto.class);
    }

    @Override
    public void deleteItem(Long itemId) {
        Item item = itemRepo.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item", "Item ID", itemId));
        Chart chart=item.getChart();
        chart.getItems().remove(item);
        double amt=chart.getTotalAmount();
        chart.setTotalAmount(amt-(item.getTotalAmount()));
        chartRepo.save(chart);
        itemRepo.delete(item);

    }
}
