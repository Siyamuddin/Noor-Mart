package com.example.noormart.Service;

import com.example.noormart.Payloads.*;
import com.example.noormart.Payloads.Responses.ProductPageableResponse;
import com.example.noormart.Payloads.Responses.SearchProductPageableResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ProductService {
    ProductDto createNewProduct(ProductDto productDto,Long categoryId,Integer quantity) throws IOException;
    ProductDto refillProduct(Long productId,Integer Quantity);
    ProductDto updateProduct(Long id, ProductDto productDto) throws IOException;
    ProductDto getProduct(Long id);
    void deleteProduct(String path,Long id) throws IOException;
    ProductPageableResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirect);
    SearchProductPageableResponse searchProducts(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDirect);
    List<ProductDto> getAllProductByCategory(Long categoryId);
    InventoryDto getProductStock(Long productId);
    ProductDto uploadProductImage(Long productId,String path, MultipartFile file) throws IOException;
    InputStream getImage(Long productId,String path) throws FileNotFoundException;

}
