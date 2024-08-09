package com.phutl.meowpet.modules.category;

import com.phutl.meowpet.modules.category.dto.CreateCategoryDTO;
import com.phutl.meowpet.modules.category.dto.UpdateCategoryDTO;
import com.phutl.meowpet.modules.database.Category;
import java.util.List;

public interface CategoryService {
    public Category createCategory(CreateCategoryDTO createCategoryDTO);
    public Category updateCategory(long id, UpdateCategoryDTO updateCategoryDTO);
    public Category getCategory(long id);
    public void deleteCategory(long id);
    public boolean existsCategory(String name);
    public Category getCategoryByName(String name);
    public List<Category> getAllCategories();
}
