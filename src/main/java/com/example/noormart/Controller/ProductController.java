package com.example.noormart.Controller;

import com.example.noormart.Configuration.AppConstants;
import com.example.noormart.Payloads.*;
import com.example.noormart.Service.ProductService;
import com.example.noormart.Service.ServiceImpl.ImageUploadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Value("${project.image}")
    String path;
    @Autowired
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/add/category/{categoryId}/{quantity}")
    public ResponseEntity<ProductDto> createProduct(@RequestParam("file")MultipartFile file,
                                                    @RequestParam("productInfo")String productInfo,
                                                    @PathVariable Long categoryId,
                                                    @PathVariable Integer quantity
                                                    ) throws IOException {
        ProductDto productDto=objectMapper.readValue(productInfo,ProductDto.class);
        ProductDto newProductDto=productService.createNewProduct(productDto,path,file,categoryId,quantity);
        return new ResponseEntity<ProductDto>(newProductDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}/{quantity}")
    public ResponseEntity<ProductDto> updateProduct(@RequestParam("file")MultipartFile file,
                                                    @RequestParam("productInfo")String productInfo,
                                                    @PathVariable Long id,
                                                    @PathVariable Integer quantity
    ) throws IOException {
        ProductDto productDto=objectMapper.readValue(productInfo,ProductDto.class);
        ProductDto newProductDto=productService.updateProduct(id,productDto,path,file,quantity);
        return new ResponseEntity<ProductDto>(newProductDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) throws IOException {
        productService.deleteProduct(path,id);
        ApiResponse apiResponse=new ApiResponse("Product deleted successfully",true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatusCode.valueOf(200));
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) throws IOException {
        ProductDto productDto=productService.getProduct(id);
//        InputStream resource= imageUploadService.getSource(path,productDto.getImage());
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(resource,response.getOutputStream());
        return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
    }

    @GetMapping("/get/all")
    public ResponseEntity<ProductPageableResponse> allProducts(@RequestParam(value ="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                               @RequestParam(value ="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                               @RequestParam(value ="sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                               @RequestParam(value ="sortDirect",defaultValue = AppConstants.SORT_DIRECT,required = false) String sortDirect,
                                               HttpServletResponse response) throws IOException {
        //retrivin all ProductPageableResponse
        ProductPageableResponse productPageableResponse=productService.getAllProducts(pageNumber,pageSize,sortBy,sortDirect);
        //listing all the images name from each product
//        List<String> images=productPageableResponse.getProductDtoList().stream().map((product)-> product.getImage()).collect(Collectors.toList());
//        //listing all the inputStream/resources from imageUploadService class.
//        List<InputStream> inputStreams=new ArrayList<>();
//        for(int i=0;i<images.size();i++)
//        {
//            inputStreams.add(imageUploadService.getSource(path,images.get(i)));
//        }
//        //setting response type.
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        //downloading all images.
//        for(int i=0;i<images.size();i++)
//        {
//            StreamUtils.copy(inputStreams.get(i),response.getOutputStream());
//        }
        //returning pageable all product response.
    return new ResponseEntity<ProductPageableResponse>(productPageableResponse,HttpStatus.OK);
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<SearchProductPageableResponse> searchProduct(@PathVariable("keyword") String keyword,
                                                                       @RequestParam(value ="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                                       @RequestParam(value ="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                                       @RequestParam(value ="sortBy",defaultValue = "id",required = false) String sortBy,
                                                                       @RequestParam(value ="sortDirect",defaultValue = AppConstants.SORT_DIRECT,required = false) String sortDirect){
        SearchProductPageableResponse searchProductPageableResponse=productService.searchProducts(keyword,pageNumber,pageSize,sortBy,sortDirect);

        return new ResponseEntity<SearchProductPageableResponse>(searchProductPageableResponse,HttpStatus.OK);
    }

    @GetMapping("/get/all/{categoryId}")
    public ResponseEntity<List<ProductDto>> getAllProductByCategory(@PathVariable Long categoryId)
    {
        List<ProductDto> productDtoList=productService.getAllProductByCategory(categoryId);
        return new ResponseEntity<List<ProductDto>>(productDtoList,HttpStatus.OK);
    }
    @GetMapping("/get/stock/productId/{id}")
    public ResponseEntity<InventoryDto> getProductStock(@PathVariable Long id)
    {
        InventoryDto inventoryDto=productService.getProductStock(id);
        return new ResponseEntity<>(inventoryDto,HttpStatus.OK);
    }
    @PutMapping("/refill/productId/{id}/quantity/{quantity}")
    public ResponseEntity<ProductDto> refillProduct(@PathVariable Long id,@PathVariable Integer quantity)
    {
        ProductDto productDto=productService.refillProduct(id,quantity);
        return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
    }
}
