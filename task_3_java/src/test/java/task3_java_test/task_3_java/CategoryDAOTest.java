package task3_java_test.task_3_java;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import com.dao.CategoryDAO;
import com.models.Category;

public class CategoryDAOTest {

    private CategoryDAO categoryDAO;

    @Before
    public void setUp() {
        categoryDAO = new CategoryDAO();
    }

    @Test
    public void testInsertCategory() {
        Category category = new Category();
        category.setId("3");
        category.setProjectId("8"); 
        category.setCategoryName("Sample Category");
        category.setStatus(true);
        category.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        category.setCreatedBy("Admin");
        category.setUpdatedAt(null);
        category.setUpdatedBy(null);

        categoryDAO.insertCategory(category);

        Category loadedCategory = categoryDAO.loadCategoryById("1");

        assertNotNull(loadedCategory);

        assertEquals("1", loadedCategory.getId());
        assertEquals("2", loadedCategory.getProjectId());
        assertEquals("Sample Category", loadedCategory.getCategoryName());
        assertEquals(true, loadedCategory.isStatus());
        assertEquals("Admin", loadedCategory.getCreatedBy());
    }

    @Test
    public void testDeleteCategoryById() {
        categoryDAO.deleteCategoryById("3");

        Category deletedCategory = categoryDAO.loadCategoryById("1");

        assertEquals(null, deletedCategory);
    }

    @Test
    public void testUpdateCategory() {
        Category category = new Category();
        category.setId("1");
        category.setProjectId("2"); 
        category.setCategoryName("Updated Category");
        category.setStatus(false);
        category.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        category.setUpdatedBy("Admin");

        categoryDAO.updateCategory(category);

        Category updatedCategory = categoryDAO.loadCategoryById("1");

        assertNotNull(updatedCategory);

        assertEquals("1", updatedCategory.getId());
        assertEquals("2", updatedCategory.getProjectId());
        assertEquals("Updated Category", updatedCategory.getCategoryName());
        assertEquals(false, updatedCategory.isStatus());
        assertEquals("Admin", updatedCategory.getUpdatedBy());
    }
}
