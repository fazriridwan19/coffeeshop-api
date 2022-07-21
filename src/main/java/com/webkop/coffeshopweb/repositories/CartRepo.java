package com.webkop.coffeshopweb.repositories;

import com.webkop.coffeshopweb.models.Cart;
import com.webkop.coffeshopweb.models.Coffe;
import com.webkop.coffeshopweb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CartRepo extends JpaRepository<Cart, Long> {

    @Query("SELECT COUNT(*) FROM Cart WHERE user=:user AND coffe=:coffe")
    Integer countByUserAndCoffee(@Param(value = "user") User user, @Param(value = "coffe") Coffe coffe);

    @Transactional
    @Modifying
    @Query("UPDATE Cart c SET c.sold=true WHERE c.id=:id")
    void updateCartSold(@Param(value = "id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Cart c SET c.quantity=:quantity WHERE c.id=:id")
    void updateCart(@Param(value = "id") Long id, @Param(value = "quantity") Integer quantity);
}
