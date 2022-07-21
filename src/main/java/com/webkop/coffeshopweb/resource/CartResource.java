package com.webkop.coffeshopweb.resource;

import com.webkop.coffeshopweb.dto.CartRequest;
import com.webkop.coffeshopweb.dto.CheckoutRequest;
import com.webkop.coffeshopweb.models.Cart;
import com.webkop.coffeshopweb.models.Coffe;
import com.webkop.coffeshopweb.models.User;
import com.webkop.coffeshopweb.services.carts.CartService;
import com.webkop.coffeshopweb.services.coffees.CoffeService;
import com.webkop.coffeshopweb.services.users.UserService;
import com.webkop.coffeshopweb.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartResource {
    private final CartService cartService;
    private final UserService userService;
    private final CoffeService coffeService;
    private final JwtUtil jwtUtil;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> createCart(HttpServletRequest request, @RequestBody(required = false) CartRequest cartRequest) throws Exception {
        try {
            final String authorizationHeader = request.getHeader(AUTHORIZATION);
            String refreshToken = authorizationHeader.substring("Bearer ".length());
            String username = jwtUtil.extractUsername(refreshToken);
            Map<String, String> map = new HashMap<>();

            User user = userService.getUserByUsername(username);
            Coffe coffe = coffeService.getCoffe(cartRequest.getCoffeeId()).get();

            Cart cart = new Cart();
            cart.setUser(user);
            cart.setCoffe(coffe);
            if (cartRequest.getQuantity() > coffe.getStock()) {
                map.put("message", "quantity more than stock");
                cart.setQuantity(coffe.getStock());
            } else {
                cart.setQuantity(cartRequest.getQuantity());
            }
            cart.setCreatedAt(new Date(System.currentTimeMillis()));
            cart.setSold(false);

            cartService.createCart(cart);
            map.put("Status", "Success");

            return new ResponseEntity<>(map, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    @GetMapping("/get")
    public ResponseEntity<?> getCarts(
            @RequestParam(value = "userId", required = false) Optional<Long> userId,
            @RequestParam(value = "coffeeId", required = false) Optional<Long> coffeeId
    ) {
        Cart cart = new Cart();
        userId.ifPresent(aLong -> cart.setUser(userService.getUserById(aLong)));
        coffeeId.ifPresent(aLong -> cart.setCoffe(coffeService.getCoffe(aLong).get()));
        if (userId.isPresent() && coffeeId.isPresent()) {
            cart.setUser(userService.getUserById(userId.get()));
            cart.setCoffe(coffeService.getCoffe(coffeeId.get()).get());
        }
        Example<Cart> example = Example.of(cart);
        return new ResponseEntity<>(cartService.getCarts(example), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCart(@PathVariable Long id) {
        return new ResponseEntity<>(cartService.getCart(id).get(), HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public ResponseEntity<Map<String, String>> checkoutCoffee(HttpServletRequest request, @RequestBody(required = false) CheckoutRequest checkoutRequest) throws Exception {
        Optional<Cart> cart = cartService.getCart(checkoutRequest.getCartId());

        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        String refreshToken = authorizationHeader.substring("Bearer ".length());
        String username = jwtUtil.extractUsername(refreshToken);
        User currentUser = userService.getUserByUsername(username);

        Coffe coffe = coffeService.getCoffe(checkoutRequest.getCoffeeId()).get();
        Map<String, String> map = new HashMap<>();

        if (cart.isEmpty()) {
            if (checkoutRequest.getQuantity() > coffe.getStock()) {
                map.put("message", "quantity more than stock");
                return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
            }
            Cart newCart = cartService.createCart(
                    new Cart(null, currentUser, coffe, checkoutRequest.getQuantity(), new Date(System.currentTimeMillis()), true)
            );
            // update stock coffe
            coffeService.patchCoffeStock(newCart.getCoffe().getId(), newCart.getCoffe().getStock() - newCart.getQuantity());

            map.put("message", "checkout success");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        if (currentUser != cart.get().getUser()) {
            map.put("message", cart.get().getUser().getUsername() + " was not authenticated");
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }

        if (cart.get().getCoffe().getStock() <= 0) {
            map.put("message", "coffee out of stock");
            return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
        }

        // set sold in cart to true
        cartService.updateSoldValue(cart.get().getId());
        // update stock coffe
        coffeService.patchCoffeStock(cart.get().getCoffe().getId(), cart.get().getCoffe().getStock() - cart.get().getQuantity());
        map.put("message", "checkout success");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Map<String, String>> updateCart(@PathVariable Long id, @RequestBody Integer quantity) {
        cartService.updateCart(id, quantity);
        Map<String, String> map = new HashMap<>();
        map.put("message", "cart updated");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteCart(@PathVariable Long id) {
        Cart cart = cartService.getCart(id).get();
        cartService.deleteCart(cart);
        Map<String, String> map = new HashMap<>();
        map.put("message", "cart deleted");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
