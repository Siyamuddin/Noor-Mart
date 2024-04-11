package com.example.noormart.Payloads;

//import com.example.noormart.Model.Address;
import com.example.noormart.Model.LocalUser;
import com.example.noormart.Model.WebOrderQuantity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WebOrderDto {
    private LocalUserDto user;
//    private AddressDto address;
    List<WebOrderQuantityDto> quantities=new ArrayList<>();
}
