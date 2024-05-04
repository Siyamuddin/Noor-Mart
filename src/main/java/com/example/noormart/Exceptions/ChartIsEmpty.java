package com.example.noormart.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@Component
public class ChartIsEmpty extends RuntimeException {
    private Long userId;

    public ChartIsEmpty(Long userId) {
        super(String.format("User with user ID:%d has an empty chart.",userId));
        this.userId = userId;
    }
}
