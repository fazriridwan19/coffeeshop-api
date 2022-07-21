package com.webkop.coffeshopweb.repositories;

import com.webkop.coffeshopweb.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
