package com.example.noormart.Exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException{
    private String fieldName;
    private String resourceName;
    private Long fieldValue;

    public ResourceNotFoundException(String fieldName, String resourceName, Long fieldValue) {
        super(String.format("%s is not found with the %s : %d",fieldName,resourceName,fieldValue));
        this.fieldName = fieldName;
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }

}
