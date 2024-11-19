package com.example.Fashion_Shop.repository;

import com.example.Fashion_Shop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
