package com.webkop.coffeshopweb.repositories;

import com.webkop.coffeshopweb.models.Category;
import com.webkop.coffeshopweb.models.Coffe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CoffeRepo extends JpaRepository<Coffe, Long> {

    @Query("SELECT id,name,price FROM Coffe")
    List<Object[]> findAllCoffeIdNamePrice();

    @Transactional
    @Modifying
    @Query("UPDATE Coffe c SET c.name=:name, c.price=:price, c.stock=:stock, c.category=:category WHERE c.id=:id")
    void patch(
            @Param(value = "id") Long id,
            @Param(value = "category") Category category,
            @Param(value = "name") String name,
            @Param(value = "price") Double price,
            @Param(value = "stock") Integer stock
    );

    @Transactional
    @Modifying
    @Query("UPDATE Coffe c SET c.stock=:stock WHERE c.id=:id")
    void patchStock(@Param(value = "id") Long id, @Param(value = "stock") Integer stock);
}
