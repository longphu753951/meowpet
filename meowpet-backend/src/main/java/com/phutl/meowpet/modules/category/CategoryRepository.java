package com.phutl.meowpet.modules.category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phutl.meowpet.modules.database.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    Category findById(long id);
    void deleteById(long id);
    boolean existsByName(String name);
}
