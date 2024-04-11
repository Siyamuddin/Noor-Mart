package com.example.noormart.Service.ServiceImpl;

import com.example.noormart.Exceptions.ResourceNotFoundException;
import com.example.noormart.Model.Inventory;
import com.example.noormart.Model.Product;
import com.example.noormart.Payloads.ProductDto;
import com.example.noormart.Payloads.ProductPageableResponse;
import com.example.noormart.Payloads.SearchProductPageableResponse;
import com.example.noormart.Repository.InventoryRepo;
import com.example.noormart.Repository.ProductRepo;
import com.example.noormart.Service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private InventoryRepo inventoryRepo;
    @Autowired
    private ImageUploadService imageUploadService;
    @Override
    public ProductDto createProduct(ProductDto productDto, String path, MultipartFile multipartFile) throws IOException {
        Inventory inventory=new Inventory();
        Product product=modelMapper.map(productDto,Product.class);
        String fileName=imageUploadService.uploadImage(path,multipartFile);
        product.setImage(fileName);
        Product savedProduct=productRepo.save(product);


        inventory.setProduct(product);
        inventory.setQuantity(productDto.getQuantity());
        inventoryRepo.save(inventory);


        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto, String path, MultipartFile multipartFile) throws IOException {
        Product product=productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Produc","product ID",id));
        List<Inventory> inventories=inventoryRepo.findByProductId(id);
        product.setName(productDto.getName());
        //setImage
        imageUploadService.deleteImage(path,product.getImage());
        String fileName= imageUploadService.uploadImage(path,multipartFile);
        product.setImage(fileName);

        product.setQuantity(productDto.getQuantity());
        product.setShortDescription(productDto.getShortDescription());
        product.setLongDescription(productDto.getLongDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        productRepo.save(product);

        Inventory inventory=inventories.getFirst();
        inventory.setQuantity(productDto.getQuantity());
        inventoryRepo.save(inventory);


        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public ProductDto getProduct(Long id) {
        Product product=productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Produc","product ID",id));

        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    public void deleteProduct(String path,Long id) throws IOException {
        Product product=productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Produc","product ID",id));
        //delete inventory.
        List<Inventory> inventories=inventoryRepo.findByProductId(id);
        inventoryRepo.delete((Inventory) inventories.getFirst());
        //delete product image
        String fileName=product.getImage();
        imageUploadService.deleteImage(path,fileName);
        //delete product
        productRepo.delete(product);

    }

    @Override
    public ProductPageableResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirect) {
        Sort sort;
        if(sortDirect.equalsIgnoreCase("asc"))
        {
            sort=Sort.by(sortBy).ascending();
        }
        else sort=Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> products=productRepo.findAll(pageable);
        List<ProductDto> productDtoList=products.stream().map((product)-> modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());

        ProductPageableResponse ppr=new ProductPageableResponse();
        ppr.setProductDtoList(productDtoList);
        ppr.setTotalElements((int) products.getTotalElements());
        ppr.setPageSize(products.getSize());
        ppr.setPageNumber(products.getNumber());
        ppr.setTotalPages(products.getTotalPages());
        ppr.setLast(products.isLast());
        return ppr;
    }

    @Override
    public SearchProductPageableResponse searchProducts(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDirect) {
        Sort sort;
        if(sortDirect.equalsIgnoreCase("asc"))
        {
            sort=Sort.by(sortBy).ascending();
        }
        else sort=Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> products=productRepo.findByName(keyword,pageable);
        List<ProductDto> productDtoList=products.stream().map((product)-> modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());
        SearchProductPageableResponse ppr=new SearchProductPageableResponse();
        ppr.setProductDtoList(productDtoList);
        ppr.setTotalElements((int) products.getTotalElements());
        ppr.setPageSize(products.getSize());
        ppr.setPageNumber(products.getNumber());
        ppr.setTotalPages(products.getTotalPages());
        ppr.setLast(products.isLast());
        return ppr;
    }


}
