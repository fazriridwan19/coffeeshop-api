package com.webkop.coffeshopweb.resource;

import com.webkop.coffeshopweb.dto.CoffeRequest;
import com.webkop.coffeshopweb.models.Coffe;
import com.webkop.coffeshopweb.services.coffees.CoffeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/v1/coffees")
@RequiredArgsConstructor
@Slf4j
public class CoffeResource {

    private final CoffeService coffeService;

    @PostMapping("/create")
    public ResponseEntity<?> createCoffe(@RequestBody Coffe coffe) {
        return new ResponseEntity<>(coffeService.createCoffe(coffe), HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCoffes() {
        List<Object[]> coffes = coffeService.getCoffesWithQuery();
        List<Map<String, Object>> results = new ArrayList<>();

        coffes.forEach(coffe -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", coffe[0]);
            map.put("name", coffe[1]);
            map.put("price", coffe[2]);
            results.add(map);
        });
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/get/details")
    public ResponseEntity<List<Coffe>> getCoffesDetail() {
        return new ResponseEntity<>(coffeService.getCoffes(), HttpStatus.OK);
    }

    @GetMapping("/get/details/{id}")
    public ResponseEntity<Coffe> getCoffe(@PathVariable Long id) {
        return new ResponseEntity<>(coffeService.getCoffe(id).get(), HttpStatus.OK);
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<?> patchCoffe(@PathVariable Long id, @RequestBody CoffeRequest coffeRequest) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            coffeService.patchCoffe(id, coffeRequest.getCategory(), coffeRequest.getName(), coffeRequest.getPrice(), coffeRequest.getStock());
            map.put("Status", "Success");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteCoffee(@PathVariable Long id) {
        coffeService.deleteCoffee(id);
        Map<String, String> map = new HashMap<>();
        map.put("Status", "item deleted");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
