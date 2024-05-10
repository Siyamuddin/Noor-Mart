package com.example.noormart.Service.ServiceImpl;

import com.example.noormart.Exceptions.ResourceNotFoundException;
import com.example.noormart.Model.Category;
import com.example.noormart.Model.Inventory;
import com.example.noormart.Model.Product;
import com.example.noormart.Payloads.InventoryDto;
import com.example.noormart.Payloads.ProductDto;
import com.example.noormart.Payloads.ProductPageableResponse;
import com.example.noormart.Payloads.SearchProductPageableResponse;
import com.example.noormart.Repository.CategoryRepo;
import com.example.noormart.Repository.InventoryRepo;
import com.example.noormart.Repository.ProductRepo;
import com.example.noormart.Service.ProductService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    @Transactional
    public ProductDto createNewProduct(ProductDto productDto, String path, MultipartFile multipartFile,Long categoryId,Integer quantity) throws IOException {
        Product product=modelMapper.map(productDto,Product.class);
        //creating new inventory for new product.
        Inventory inventory=new Inventory();
        Integer sum=inventory.getQuantity();
        inventory.setQuantity(sum+quantity);//setting the quantity of inventory.
        inventory.setProduct(product);//setting the product of inventory.
        //saving the inventory
        Inventory inventory1=inventoryRepo.save(inventory);

        //fetching category to add product by category.
        Category category=categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category ID",categoryId));
        //uploading product image by call image upload method.
        String fileName=imageUploadService.uploadImage(path,multipartFile);
        //setting product information.
        product.setImage(fileName);
        product.setCategory(category);//setting product category
        product.setAvailable(inventory1.getQuantity());//setting available product.
        Product savedProduct=productRepo.save(product);//saving the product.

        ProductDto productDto1=modelMapper.map(savedProduct,ProductDto.class);
        return productDto1;
    }

    @Override
    @Transactional
    public ProductDto refillProduct(Long productId, Integer Quantity) {
        Product product=productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product","product ID",productId));
        Inventory inventory=inventoryRepo.findByProductId(productId);
        Integer sum=inventory.getQuantity()+Quantity;
        inventory.setQuantity(sum);
        Inventory savedInventory=inventoryRepo.save(inventory);
        product.setAvailable(savedInventory.getQuantity());
       Product savedProduct= productRepo.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(Long id, ProductDto productDto, String path, MultipartFile multipartFile,Integer quantity) throws IOException {
        Product product=productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Produc","product ID",id));
        //fetching inventory by product.
        Inventory inventory=inventoryRepo.findByProductId(id);
        inventory.setQuantity(quantity);
        Inventory inventory1=inventoryRepo.save(inventory);

        //updating the image data.
        product.setName(productDto.getName());
        //setImage
        imageUploadService.deleteImage(path,product.getImage());//deleting the existing image from the directory
        String fileName= imageUploadService.uploadImage(path,multipartFile);//uploading new image.
        product.setImage(fileName);//setting new image name.
        product.setAvailable(inventory1.getQuantity());//replacing quantity.
        product.setShortDescription(productDto.getShortDescription());
        product.setLongDescription(productDto.getLongDescription());
        product.setPrice(productDto.getPrice());
        Product savedProduct= productRepo.save(product);



        ProductDto productDto1=modelMapper.map(savedProduct,ProductDto.class);

        return productDto1;
    }

    @Override
    public ProductDto getProduct(Long id) {
        Product product=productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Produc","product ID",id));
        return modelMapper.map(product,ProductDto.class);
    }

    @Override
    @Transactional
    public void deleteProduct(String path,Long id) throws IOException {
        Product product=productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Produc","product ID",id));
        //removing the product from the category
        Category category=product.getCategory();
        category.getProductList().remove(product);
        categoryRepo.save(category);
        //delete inventory.
        Inventory inventory=inventoryRepo.findByProductId(product.getId());
        inventoryRepo.delete(inventory);
        //delete product image
        String fileName=product.getImage();
        imageUploadService.deleteImage(path,fileName);
        //delete product
        productRepo.deleteById(id);



    }

    @Override
//    @Cacheable(cacheNames = "products",key = "12")
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

        Page<Product> products=productRepo.findByNameContainingIgnoreCase(keyword,pageable);

        List<ProductDto> productDtoList=products.stream().map((product)-> modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());

        SearchProductPageableResponse ppr=new SearchProductPageableResponse();

        ppr.setSearchproductDtoList(productDtoList);

        ppr.setTotalElements((int) products.getTotalElements());
        ppr.setPageSize(products.getSize());
        ppr.setPageNumber(products.getNumber());
        ppr.setTotalPages(products.getTotalPages());
        ppr.setLast(products.isLast());

        return ppr;
    }

    @Override
    public List<ProductDto> getAllProductByCategory(Long categoryId) {
        Category category=categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category ID",categoryId));
        List<Product> products=productRepo.findByCategory(category);
        List<ProductDto> productDtoList=products.stream().map((product)-> modelMapper.map(product,ProductDto.class)).collect(Collectors.toList());

        return productDtoList;
    }

    @Override
    public InventoryDto getProductStock(Long productId) {
        Product product=productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Produc","product ID",productId));
        Inventory inventory=inventoryRepo.findByProductId(productId);
        return modelMapper.map(inventory,InventoryDto.class);
    }


}
