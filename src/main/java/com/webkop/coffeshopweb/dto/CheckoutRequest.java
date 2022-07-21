package com.webkop.coffeshopweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {
    private Long cartId = Long.valueOf(0);
    private Long coffeeId;
    private Integer quantity = 1;

    public CheckoutRequest(Long coffeeId) {
        this.coffeeId = coffeeId;
    }

    public CheckoutRequest(Long coffeeId, Long cartId) {
        this.coffeeId = coffeeId;
        this.cartId = cartId;
    }
}
