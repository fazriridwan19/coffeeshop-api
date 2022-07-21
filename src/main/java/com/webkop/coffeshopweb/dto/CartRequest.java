package com.webkop.coffeshopweb.dto;

import com.webkop.coffeshopweb.models.Coffe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {
    private Long coffeeId;
    private Integer quantity = 1;
    public CartRequest(Long coffeeId) {
        this.coffeeId = coffeeId;
    }
}
