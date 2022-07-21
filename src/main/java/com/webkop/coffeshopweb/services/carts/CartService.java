package com.webkop.coffeshopweb.services.carts;

import com.webkop.coffeshopweb.models.Cart;
import com.webkop.coffeshopweb.models.Coffe;
import com.webkop.coffeshopweb.models.User;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

public interface CartService {
    Cart createCart(Cart cart);
    List<Cart> getCarts(Example<Cart> example);
    Optional<Cart> getCart(Example<Cart> example);
    Optional<Cart> getCart(Long id);
    void updateSoldValue(Long id);
    void updateCart(Long id, Integer quantity);
    void deleteCart(Cart cart);
}
