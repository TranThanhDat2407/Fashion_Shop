import com.example.Fashion_Shop.component.LocalizationUtils;
import com.example.Fashion_Shop.controller.CategoryController;
import com.example.Fashion_Shop.dto.CategoryDTO;
import com.example.Fashion_Shop.model.Category;
import com.example.Fashion_Shop.response.category.CategoryResponse;
import com.example.Fashion_Shop.service.category.CategoryService;
import com.example.Fashion_Shop.util.MessageKeys;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;


import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
public class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Mock
    private LocalizationUtils localizationUtils;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterMethod
    public void tearDown() {
        reset(categoryService, localizationUtils);
    }

    @Test
    public void testCreateCategorySuccess() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("test");

        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);

        Category category = new Category();
        category.setName("test");

        when(categoryService.createCategory(categoryDTO)).thenReturn(category);

        ResponseEntity<CategoryResponse> response = categoryController.createCategory(categoryDTO, mockBindingResult);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(Objects.requireNonNull(response.getBody()).getCategory().getName(), "test");
    }

    @Test
    public void testCreateCategoryFailure() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("test");

        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<CategoryResponse> response = categoryController.createCategory(categoryDTO, mockBindingResult);
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testGetAllCategorySuccess() throws Exception {
        Category category1 = new Category();
        category1.setName("Nam");
        Category category2 = new Category();
        category2.setName("Nữ");
        Category category3 = new Category();
        category3.setName("Trẻ em");

        when(categoryService.getCategorizedTree()).thenReturn(List.of(category1, category2, category3));

        ResponseEntity<List<Category>> response = categoryController.getAllCategories();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(Objects.requireNonNull(response.getBody()).size(), 3);
        assertEquals(response.getBody().getFirst().getName(), "Nam");
        for (Category category : response.getBody()) {
            System.out.println(category.getName());
        }
    }

    @Test
    public void testGetCategoryByIdSuccess() {
        Long id = 1L;
        Category category = new Category();
        category.setId(id);
        category.setName("test");

        when(categoryService.getCategoryById(id)).thenReturn(category);

        ResponseEntity<?> response = categoryController.getCategoryById(id);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), category);

    }

    @Test
    public void testGetCategoryByIdFailure() {
        Long id = 1L;

        when(categoryService.getCategoryById(id)).thenReturn(null);

        ResponseEntity<?> response = categoryController.getCategoryById(id);

        assertEquals(response.getBody(), null);
    }

    @Test
    public void testUpdateCategorySuccess() {
        Long id = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Category");

        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        Category updatedCategory = new Category();
        updatedCategory.setId(id);
        updatedCategory.setName("Updated category");

        when(categoryService.updateCategory(id, categoryDTO)).thenReturn(updatedCategory);

        ResponseEntity<CategoryResponse> response = categoryController.updateCategory(id,categoryDTO,result);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody().getCategory().getName(), "Updated category");
    }

    @Test
    public void testUpdateCategoryFailure() {
        Long id = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Category");

        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        Category updatedCategory = null;

        when(categoryService.updateCategory(id, categoryDTO)).thenReturn(updatedCategory);

        ResponseEntity<CategoryResponse> response = categoryController.updateCategory(id,categoryDTO,result);

        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        assertEquals(response.getBody().getCategory(), null);
    }

    @Test
    public void testDeleteCategorySuccess() throws Exception {
        // Tạo dữ liệu đầu vào
        Long id = 1L;

        // Gọi phương thức cần kiểm tra
        ResponseEntity<?> response = categoryController.deleteCategory(id);

        // Kiểm tra trạng thái HTTP
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testDeleteCategoryFailure() throws Exception {
        // Tạo dữ liệu đầu vào
        Long id = 1L;

        doThrow(new RuntimeException("Category not found")).when(categoryService).deleteCategory(id);

        // Gọi phương thức cần kiểm tra
        ResponseEntity<?> response = categoryController.deleteCategory(id);

        // Kiểm tra trạng thái HTTP
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
