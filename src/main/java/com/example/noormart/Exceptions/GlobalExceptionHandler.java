package com.example.noormart.Exceptions;

import com.example.noormart.Payloads.Responses.ApiResponse;
import org.hibernate.sql.ast.spi.SqlAliasBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex)
    {
        String message=ex.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex)
    {
        Map<String, String> resp= new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName=((FieldError)error).getField();
            String message=error.getDefaultMessage();
            resp.put(fieldName,message);
        });

        return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<Map<String,String>> typeMissMatchException(MethodArgumentTypeMismatchException ex)
    {
       Map<String,String> resp=new HashMap<>();
        String str= ex.getMessage();
        String str2=ex.getValue().toString();
        resp.put(str,str2);
        return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiResponse> userAlreadyExistException(UserAlreadyExistException ex)
    {
        String message=ex.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.ALREADY_REPORTED);
    }
    @ExceptionHandler(ChartIsEmpty.class)
    public ResponseEntity<ApiResponse> chartIsEmpty(ChartIsEmpty chartIsEmpty)
    {
        ApiResponse apiResponse=new ApiResponse(chartIsEmpty.getMessage(),false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<ApiResponse> productOutOfStock(ProductOutOfStockException ex)
    {
        ApiResponse apiResponse=new ApiResponse(ex.getMessage(),false);
        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UnpaidBillException.class)
    public ResponseEntity<ApiResponse> unpaidBillException(UnpaidBillException ube)
    {
        ApiResponse apiResponse=new ApiResponse(ube.getMessage(),false);
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
}
