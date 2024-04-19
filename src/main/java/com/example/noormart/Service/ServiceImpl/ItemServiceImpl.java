package com.example.noormart.Service.ServiceImpl;

import com.example.noormart.Exceptions.ResourceNotFoundException;
import com.example.noormart.Model.Chart;
import com.example.noormart.Model.Item;
import com.example.noormart.Model.LocalUser;
import com.example.noormart.Model.Product;
import com.example.noormart.Payloads.ItemDto;
import com.example.noormart.Payloads.ProductDto;
import com.example.noormart.Repository.ChartRepo;
import com.example.noormart.Repository.ItemRepo;
import com.example.noormart.Repository.ProductRepo;
import com.example.noormart.Repository.UserRepo;
import com.example.noormart.Service.ItemService;
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
    @Override
    public ItemDto createItem(Long userId,Long productId, Integer quantity) {
        LocalUser localUser=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","User Id",userId));
        Product product=productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Produc","product ID",productId));
        Item item=new Item();
        item.setChart(localUser.getChart());
        item.setProduct(product);
        item.setQuantity(quantity);
       Item savedItem= itemRepo.save(item);
        Chart chart=localUser.getChart();
        chart.getItems().add(item);
        chartRepo.save(chart);
       userRepo.save(localUser);
    return modelMapper.map(savedItem,ItemDto.class);
    }

    @Override
    public ItemDto updateItem(Long itemId, Integer quantity) {
        Item item=itemRepo.findById(itemId).orElseThrow(()->new ResourceNotFoundException("Item","Item ID",itemId));
        item.setQuantity(quantity);
        Item newItem=itemRepo.save(item);

        return modelMapper.map(newItem,ItemDto.class);
    }

    @Override
    public void deleteItem(Long itemId) {
        Item item = itemRepo.findById(itemId).orElseThrow(() -> new ResourceNotFoundException("Item", "Item ID", itemId));
        Chart chart=item.getChart();
        chart.getItems().remove(item);
        chartRepo.save(chart);
        itemRepo.delete(item);

    }
}
