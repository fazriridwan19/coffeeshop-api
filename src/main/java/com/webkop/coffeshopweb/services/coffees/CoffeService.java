package com.webkop.coffeshopweb.services.coffees;

import com.webkop.coffeshopweb.models.Category;
import com.webkop.coffeshopweb.models.Coffe;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Optional;

public interface CoffeService {

    Coffe createCoffe(Coffe coffe);
    List<Object[]> getCoffesWithQuery();
    List<Coffe> getCoffes();
    Optional<Coffe> getCoffe(Example<Coffe> example);
    Optional<Coffe> getCoffe(Long id);
    void patchCoffe(Long id,
                    Category category,
                    String name,
                    Double price,
                    Integer stock);
    void patchCoffeStock(Long id, Integer stock);
    void deleteCoffee(Long id);
}
