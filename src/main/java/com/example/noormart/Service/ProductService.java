package com.example.noormart.Service;

import com.example.noormart.Model.Product;
import com.example.noormart.Payloads.InventoryDto;
import com.example.noormart.Payloads.ProductDto;
import com.example.noormart.Payloads.ProductPageableResponse;
import com.example.noormart.Payloads.SearchProductPageableResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    ProductDto createNewProduct(ProductDto productDto, String path, MultipartFile multipartFile,Long categoryId,Integer quantity) throws IOException;
    ProductDto refillProduct(Long productId,Integer Quantity);
    ProductDto updateProduct(Long id, ProductDto productDto,String path, MultipartFile multipartFile,Integer quantity) throws IOException;
    ProductDto getProduct(Long id);
    void deleteProduct(String path,Long id) throws IOException;
    ProductPageableResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirect);
    SearchProductPageableResponse searchProducts(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDirect);
    List<ProductDto> getAllProductByCategory(Long categoryId);
    InventoryDto getProductStock(Long productId);

}
