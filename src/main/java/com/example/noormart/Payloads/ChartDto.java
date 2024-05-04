package com.example.noormart.Payloads;

//import com.example.noormart.Model.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChartDto {
    private List<ItemDto> items=new ArrayList<>();
    private Double totalAmount = 0.00;

}
