package com.webkop.coffeshopweb.services.carts;

import com.webkop.coffeshopweb.models.Cart;
import com.webkop.coffeshopweb.repositories.CartRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepo cartRepo;

    @Override
    public Cart createCart(Cart cart) {
        return cartRepo.save(cart);
    }

    @Override
    public List<Cart> getCarts(Example<Cart> example) {
        return cartRepo.findAll(example);
    }

    @Override
    public Optional<Cart> getCart(Example<Cart> example) {
        return cartRepo.findOne(example);
    }

    @Override
    public Optional<Cart> getCart(Long id) {
        return cartRepo.findById(id);
    }

    @Override
    public void updateSoldValue(Long id) {
        cartRepo.updateCartSold(id);
    }

    @Override
    public void updateCart(Long id, Integer quantity) {
        cartRepo.updateCart(id, quantity);
    }

    @Override
    public void deleteCart(Cart cart) {
        cartRepo.delete(cart);
    }

}
