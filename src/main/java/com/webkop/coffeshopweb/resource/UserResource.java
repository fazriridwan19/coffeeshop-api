package com.webkop.coffeshopweb.resource;

import com.webkop.coffeshopweb.dto.LoginRequest;
import com.webkop.coffeshopweb.models.Cart;
import com.webkop.coffeshopweb.models.User;
import com.webkop.coffeshopweb.services.carts.CartService;
import com.webkop.coffeshopweb.services.users.MyUserDetailsService;
import com.webkop.coffeshopweb.services.users.UserService;
import com.webkop.coffeshopweb.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;
    private final CartService cartService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws Exception {
        userService.saveUser(user);
        return new ResponseEntity<>(userService.addRoleToUser(user.getUsername(), "ROLE_USER"), CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (Exception ex) {
//            Map<String, String> map = new HashMap<>();
//            map.put("message", "Incorrect username or password");
//            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
            throw new Exception(ex.getMessage());
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(loginRequest.getUsername());

        return new ResponseEntity<>(jwtUtil.generateToken(userDetails), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        Map<String, String> map = new HashMap<>();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                String username = jwtUtil.extractUsername(refreshToken);
                UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
                String accessToken = jwtUtil
                        .createAccessToken(userDetails);

                map.put("access_token", accessToken);
                map.put("refresh_token", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                return new ResponseEntity<>(map, CREATED);
            } catch (Exception ex) {
                response.setHeader("error", ex.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                map.put("message", ex.getMessage());
                return new ResponseEntity<>(map, FORBIDDEN);
            }
        } else {
            response.setContentType(APPLICATION_JSON_VALUE);
            map.put("message", "required refresh token");
            return new ResponseEntity<>(map, FORBIDDEN);
        }
    }

    @GetMapping("/carts")
    public ResponseEntity<List<Cart>> userCarts(HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        String refreshToken = authorizationHeader.substring("Bearer ".length());
        String username = jwtUtil.extractUsername(refreshToken);
        User user = userService.getUserByUsername(username);
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setSold(false);
        Example<Cart> example = Example.of(cart);
        return new ResponseEntity<>(cartService.getCarts(example), HttpStatus.OK);
    }

}
