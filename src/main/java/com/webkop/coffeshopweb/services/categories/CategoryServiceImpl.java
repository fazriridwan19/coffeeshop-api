package com.webkop.coffeshopweb.services.categories;

import com.webkop.coffeshopweb.models.Category;
import com.webkop.coffeshopweb.repositories.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepo categoryRepo;

    @Override
    public Category createCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepo.getReferenceById(id);
    }

}
