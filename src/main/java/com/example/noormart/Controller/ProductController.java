package com.example.noormart.Controller;

import com.example.noormart.Configuration.AppConstants;
import com.example.noormart.Payloads.InventoryDto;
import com.example.noormart.Payloads.ProductDto;
import com.example.noormart.Payloads.ProductPageableResponse;
import com.example.noormart.Payloads.SearchProductPageableResponse;
import com.example.noormart.Service.ProductService;
import com.example.noormart.Service.ServiceImpl.ImageUploadService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@Tag(name = "Product")
@SecurityRequirement(name = "JWT-Auth")
public class ProductController {
    @Value("${project.image}")
    String path;
    @Autowired
    private ProductService productService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ImageUploadService imageUploadService;

    @Operation(
            summary = "Get a single product.",
            description = "Get a single product by providing ID.")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) throws IOException {
        ProductDto productDto=productService.getProduct(id);
//        InputStream resource= imageUploadService.getSource(path,productDto.getImage());
//        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
//        StreamUtils.copy(resource,response.getOutputStream());
        return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
    }

    @Operation(
            summary = "Get all products",
            description = "Get all product in Page.")
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
    @Operation(
            summary = "Search product By name",
            description = "Search product By name.")
    @GetMapping("/search/{keyword}")
    public ResponseEntity<SearchProductPageableResponse> searchProduct(@PathVariable("keyword") String keyword,
                                                                       @RequestParam(value ="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                                       @RequestParam(value ="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                                       @RequestParam(value ="sortBy",defaultValue = "id",required = false) String sortBy,
                                                                       @RequestParam(value ="sortDirect",defaultValue = AppConstants.SORT_DIRECT,required = false) String sortDirect){
        SearchProductPageableResponse searchProductPageableResponse=productService.searchProducts(keyword,pageNumber,pageSize,sortBy,sortDirect);

        return new ResponseEntity<SearchProductPageableResponse>(searchProductPageableResponse,HttpStatus.OK);
    }

    @Operation(
            summary = "Get All products By category",
            description = "Provide a category ID to get all product in this category.")
    @GetMapping("/get/all/{categoryId}")
    public ResponseEntity<List<ProductDto>> getAllProductByCategory(@PathVariable Long categoryId)
    {
        List<ProductDto> productDtoList=productService.getAllProductByCategory(categoryId);
        return new ResponseEntity<List<ProductDto>>(productDtoList,HttpStatus.OK);
    }

    //Todo add all product stock featuchers
    @Operation(
            summary = "Get product's Stock",
            description = "Check products inventory.")
    @GetMapping("/get/stock/productId/{id}")
    public ResponseEntity<InventoryDto> getProductStock(@PathVariable Long id)
    {
        InventoryDto inventoryDto=productService.getProductStock(id);
        return new ResponseEntity<>(inventoryDto,HttpStatus.OK);
    }


    @Operation(
            summary = "Get product Image",
            description = "Get product image by providing product category.")
    @GetMapping(value = "/image/view/{productId}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(@PathVariable Long productId,
                         HttpServletResponse response) throws IOException {
        InputStream inputStream=productService.getImage(productId,path);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,response.getOutputStream());
    }
}
