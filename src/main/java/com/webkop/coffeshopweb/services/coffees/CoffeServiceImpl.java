package com.webkop.coffeshopweb.services.coffees;

import com.webkop.coffeshopweb.models.Category;
import com.webkop.coffeshopweb.models.Coffe;
import com.webkop.coffeshopweb.repositories.CoffeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoffeServiceImpl implements CoffeService{

    private final CoffeRepo coffeRepo;

    @Override
    public Coffe createCoffe(Coffe coffe) {
        return coffeRepo.save(coffe);
    }

    @Override
    public List<Object[]> getCoffesWithQuery() {
        return coffeRepo.findAllCoffeIdNamePrice();
    }

    @Override
    public List<Coffe> getCoffes() {
        return coffeRepo.findAll();
    }

    @Override
    public Optional<Coffe> getCoffe(Example<Coffe> example) {
        return coffeRepo.findOne(example);
    }

    @Override
    public Optional<Coffe> getCoffe(Long id) {
        return coffeRepo.findById(id);
    }

    @Override
    public void patchCoffe(Long id, Category category,  String name, Double price, Integer stock) {
        coffeRepo.patch(id, category, name, price, stock);
    }

    @Override
    public void patchCoffeStock(Long id, Integer stock) {
        coffeRepo.patchStock(id, stock);
    }

    @Override
    public void deleteCoffee(Long id) {
        coffeRepo.deleteById(id);
    }

}
