package com.webkop.coffeshopweb.services.categories;

import com.webkop.coffeshopweb.models.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);
    List<Category> getCategories();
    Category getCategory(Long id);

}
