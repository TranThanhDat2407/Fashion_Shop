package com.example.Fashion_Shop.service.category;

import com.example.Fashion_Shop.dto.CategoryDTO;
import com.example.Fashion_Shop.exception.DataNotFoundException;
import com.example.Fashion_Shop.model.Category;
import com.example.Fashion_Shop.model.Product;
import com.example.Fashion_Shop.repository.CategoryRepository;
import com.example.Fashion_Shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Category createCategory(CategoryDTO categoryDTO) throws DataNotFoundException {
        Category parentCategory = null;

        if (categoryDTO.getParentId() != null) {
            parentCategory = categoryRepository
                    .findById(categoryDTO.getParentId())
                    .orElseThrow(() -> {
                        String errorMessage = "Category not found with Id: " + categoryDTO.getParentId();
                        return new DataNotFoundException(errorMessage);
                    });
        }

        Category newCategory = Category
                .builder()
                .name(categoryDTO.getName())
                .parentCategory(parentCategory)
                .build();
        return categoryRepository.save(newCategory);
    }

    public Category getCategoryById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public List<Category> getAllCategories()
    {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category updateCategory(long categoryId,
                                   CategoryDTO categoryDTO) {
        Category existingCategory = getCategoryById(categoryId);
        existingCategory.setName(categoryDTO.getName());
        categoryRepository.save(existingCategory);
        return existingCategory;
    }

    @Transactional
    public Category deleteCategory(long id) throws Exception {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        List<Product> products = productRepository.findByCategory(category);
        if (!products.isEmpty()) {
            throw new IllegalStateException("Cannot delete category with associated products");
        } else {
            categoryRepository.deleteById(id);
            return  category;
        }
    }

    public List<Category> getCategorizedTree() {
        List<Category> categories = getAllCategories();
        Map<Long, Category> categoryMap = new HashMap<>();

        // Đặt tất cả các category vào một Map với key là ID
        for (Category category : categories) {
            categoryMap.put(category.getId(), category);
        }

        // Duyệt qua categories và tạo cây phân cấp
        List<Category> categoryTree = new ArrayList<>();

        for (Category category : categories) {
            if (category.getParentCategory() == null) {
                // Nếu không có parent, đây là root node của cây
                categoryTree.add(category);
            } else {
                // Nếu có parent, thêm vào danh sách sub-categories của parent
                Category parent = categoryMap.get(category.getParentCategory().getId());
                if (parent != null) {
                    parent.getSubCategories().add(category);
                }
            }
        }

        return categoryTree;
    }
}
