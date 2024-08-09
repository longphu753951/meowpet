package com.phutl.meowpet.modules.category.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phutl.meowpet.modules.category.CategoryRepository;
import com.phutl.meowpet.modules.category.CategoryService;
import com.phutl.meowpet.modules.category.dto.CreateCategoryDTO;
import com.phutl.meowpet.modules.category.dto.UpdateCategoryDTO;
import com.phutl.meowpet.modules.database.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(CreateCategoryDTO createCategoryDTO) {
        if (existsCategory(createCategoryDTO.getName())) {
            throw new IllegalArgumentException("Category with name " + createCategoryDTO.getName() + " already exists");
        }
        Category category = new Category().builder().name(createCategoryDTO.getName())
                .description(createCategoryDTO.getDescription()).build();
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(long id, UpdateCategoryDTO updateCategoryDTO) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new IllegalArgumentException("Category with id " + id + " does not exist");
        }
        category.setName(updateCategoryDTO.getName());
        category.setDescription(updateCategoryDTO.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategory(long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void deleteCategory(long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category with id " + id + " does not exist");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public boolean existsCategory(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public Category getCategoryByName(String name) {
        Category existingCategory = categoryRepository.findByName(name);
        if (existingCategory == null) {
            throw new IllegalArgumentException("Category with name " + name + " does not exist");
        }
        return existingCategory;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new IllegalArgumentException("No categories found");
        }
        return categories;
    }

}
