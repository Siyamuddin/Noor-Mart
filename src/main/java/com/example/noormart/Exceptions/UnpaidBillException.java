package com.example.noormart.Exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnpaidBillException extends RuntimeException{
    private Integer numberOfUnpaid;

    public UnpaidBillException(Integer numberOfUnpaid) {
        super(String.format("You have %d unpaid payment please pay first before deleting the account.",numberOfUnpaid));
        this.numberOfUnpaid = numberOfUnpaid;
    }
}
