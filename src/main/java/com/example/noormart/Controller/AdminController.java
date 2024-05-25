package com.example.noormart.Controller;

import com.example.noormart.Configuration.AppConstants;
import com.example.noormart.Payloads.*;
import com.example.noormart.Payloads.Responses.ApiResponse;
import com.example.noormart.Payloads.Responses.GetAllUnpaidBuyResponse;
import com.example.noormart.Payloads.Responses.PageableResponse;
import com.example.noormart.Payloads.Responses.SellResponse;
import com.example.noormart.Service.BuyService;
import com.example.noormart.Service.CategoryService;
import com.example.noormart.Service.LocalUserService;
import com.example.noormart.Service.ProductService;
import com.example.noormart.Service.ServiceImpl.ImageUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin",description = "Only for admins")
@SecurityRequirement(name = "JWT-Auth")
public class AdminController {
    @Autowired
    private BuyService buyService;
    @Autowired
    private CategoryService categoryService;
    @Value("${project.image}")
    String path;
    @Autowired
    private ProductService productService;
    @Autowired
    private ImageUploadService imageUploadService;
    @Autowired
    private LocalUserService localUserService;

    @Operation(
            summary = "Get All Unpaid Payment Details",
            description = "It will fetch all the unpaid purchase details details including user and purchase time.")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/unpaid")
    public ResponseEntity<List<GetAllUnpaidBuyResponse>> getAllUnpaidBuy()
    {
        List<GetAllUnpaidBuyResponse> guabrList=buyService.getTotalUnpaidBuys();
        return new ResponseEntity<List<GetAllUnpaidBuyResponse>>(guabrList, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all Unpaid Payment By Date",
            description = "It will fetch all the purchase history after given date.")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all/byDate")
    public ResponseEntity<List<GetAllUnpaidBuyResponse>> getAllBuyByDate(@RequestParam(value = "time", required = false) String time)
    {
        // Handle cases where time parameter is missing or empty
        if (time == null || time.isEmpty()) {
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
        }

        // Use a date parsing library for reliable parsing
        try {
            // Replace "yyyy-MM-dd" with the expected format from your data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(time, formatter);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            List<GetAllUnpaidBuyResponse> guabr = buyService.getAllBuyByDate(date);
            return new ResponseEntity<>(guabr, HttpStatus.OK);
        } catch (ParseException e) {
            // Handle parsing exception (log the error or return a bad request response)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Get Total Sell Data by Date",
            description = "It will fetch all Sell history after given date.")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/sellData")
    public ResponseEntity<SellResponse> getAllSellData(@RequestParam(value = "time", required = false) String time) {
        // Handle cases where time parameter is missing or empty
//        if (time == null || time.isEmpty()) {
//            return new ResponseEntity<>(Collections.emptyList(),HttpStatus.OK);
//        }

        // Use a date parsing library for reliable parsing
        try {
            // Replace "yyyy-MM-dd" with the expected format from your data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(time, formatter);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

            SellResponse sellResponse=buyService.getTotalSellDataByDate(date);
            return new ResponseEntity<>(sellResponse, HttpStatus.OK);
        } catch (ParseException e) {
            // Handle parsing exception (log the error or return a bad request response)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Create a new Category",
            description = "Provide category information to create a new category."
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto)
    {
        CategoryDto categoryDto1=categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(categoryDto1, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update Category",
            description = "Provide category information to update the category."
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateDto(@RequestBody CategoryDto categoryDto,@PathVariable Long categoryId){
        CategoryDto categoryDto1=categoryService.updateCategory(categoryId,categoryDto);
        return new ResponseEntity<>(categoryDto1,HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Category",
            description = "Permanently delete the category."
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/category/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId)
    {
        categoryService.deleteCategory(categoryId);
        ApiResponse apiResponse=new ApiResponse("The category has beed deleted successfully",true);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @Operation(
            summary = "Create Product",
            description = "Provide Product information to Create a new product."
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add/category/{categoryId}/{quantity}")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto,
                                                    @PathVariable Long categoryId,
                                                    @PathVariable Integer quantity
    ) throws IOException {

        ProductDto newProductDto=productService.createNewProduct(productDto,categoryId,quantity);
        return new ResponseEntity<ProductDto>(newProductDto, HttpStatus.CREATED);
    }
    @Operation(
            summary = "Update Product",
            description = "Provide product information to update the product."
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/product/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
                                                    @PathVariable Long productId
    ) throws IOException {

        ProductDto newProductDto=productService.updateProduct(productId,productDto);
        return new ResponseEntity<ProductDto>(newProductDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete product",
            description = "Provide product ID to Delete the Product."
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/product/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws IOException {
        productService.deleteProduct(path,productId);
        ApiResponse apiResponse=new ApiResponse("Product deleted successfully",true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatusCode.valueOf(200));
    }
    @Operation(
            summary = "Refill Product",
            description = "Provide product ID and Quantity to refill the product."
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/refill/productId/{id}/quantity/{quantity}")
    public ResponseEntity<ProductDto> refillProduct(@PathVariable Long id,@PathVariable Integer quantity)
    {
        ProductDto productDto=productService.refillProduct(id,quantity);
        return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
    }

    @Operation(
            summary = "Upload Product Image",
            description = "Upload product image"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/upload/image/{proudctId}")
    public ResponseEntity<ProductDto> uploadImage(@RequestParam("file") MultipartFile file,
                                                  @PathVariable Long proudctId) throws IOException {
        ProductDto productDto=productService.uploadProductImage(proudctId,path,file);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }


    @Operation(
            summary = "Get all Users",
            description = "Get All users"
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get/all")
    public ResponseEntity<PageableResponse> getAllUsers(@RequestParam(value ="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                        @RequestParam(value ="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                        @RequestParam(value ="sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                        @RequestParam(value ="sortDirect",defaultValue = AppConstants.SORT_DIRECT,required = false) String sortDirect){
        PageableResponse pageableResponse=localUserService.getAllUsers(pageNumber,pageSize,sortBy,sortDirect);
        return new ResponseEntity<PageableResponse>(pageableResponse,HttpStatus.OK);
    }


    @Operation(
            summary = "Search an User",
            description = "Search user by user name."
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<LocalUserDto>> searchUser(@PathVariable("keyword") String keyword)
    {
        List<LocalUserDto> localUserDto=localUserService.searchUser(keyword);
        return new ResponseEntity<List<LocalUserDto>>(localUserDto,HttpStatus.OK);
    }
    @Operation(
            summary = "Authorize an User",
            description = "Admin can promote a normal user to admin."
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/authorize/{userId}/{roleId}")
    ResponseEntity<String> authorize(@PathVariable Long userId,@PathVariable int roleId)
    {
        String string= localUserService.authorizeUser(userId,roleId);
        return new ResponseEntity<>(string,HttpStatus.OK);
    }

}
//hlw