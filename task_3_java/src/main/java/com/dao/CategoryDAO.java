package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.models.Category;
import com.models.Project;
import com.DatabaseConnection.DatabaseConnection;

public class CategoryDAO {

	public void insertCategory(Category category) {
	    String selectQuery = "SELECT * FROM t_project WHERE id = ?";
	    String insertQuery = "INSERT INTO t_category (id, pro_id, cat_name, status, created_at, created_by, updated_at, updated_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	    
	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {

	        selectStatement.setString(1, category.getProjectId());

	        try (ResultSet resultSet = selectStatement.executeQuery()) {
	            if (resultSet.next()) { // Check if project exists
	                try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
	                    insertStatement.setString(1, category.getId());
	                    insertStatement.setString(2, category.getProjectId());
	                    insertStatement.setString(3, category.getCategoryName());
	                    insertStatement.setBoolean(4, category.isStatus());
	                    insertStatement.setTimestamp(5, category.getCreatedAt());
	                    insertStatement.setString(6, category.getCreatedBy());
	                    insertStatement.setTimestamp(7, category.getUpdatedAt());
	                    insertStatement.setString(8, category.getUpdatedBy());

	                    int rowsAffected = insertStatement.executeUpdate();
	                    if (rowsAffected > 0) {
	                        System.out.println("Category data inserted successfully!");
	                    } else {
	                        System.err.println("Error inserting category data!");
	                    }
	                }
	            } else {
	                System.err.println("Error: Project with ID " + category.getProjectId() + " does not exist.");
	            }
	        }

	    } catch (SQLException e) {
	        System.err.println("Error inserting category data: " + e.getMessage());
	    }
	}


    public Category loadCategoryById(String id) {
        Category category = null;
        String query = "SELECT * FROM t_category WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    category = new Category();
                    category.setId(resultSet.getString("id"));
                    category.setProjectId(resultSet.getString("pro_id"));
                    category.setCategoryName(resultSet.getString("cat_name"));
                    category.setStatus(resultSet.getBoolean("status"));
                    category.setCreatedAt(resultSet.getTimestamp("created_at"));
                    category.setCreatedBy(resultSet.getString("created_by"));
                    category.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    category.setUpdatedBy(resultSet.getString("updated_by"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error loading category: " + e.getMessage());
        }

        return category;
    }

    public void deleteCategoryById(String id) {
        String queryCheckActivity = "SELECT COUNT(*) FROM t_activity WHERE cat_id = ?";
        String queryDeleteCategory = "DELETE FROM t_category WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatementCheckActivity = connection.prepareStatement(queryCheckActivity);
             PreparedStatement preparedStatementDeleteCategory = connection.prepareStatement(queryDeleteCategory)) {

            preparedStatementCheckActivity.setString(1, id);
            try (ResultSet resultSet = preparedStatementCheckActivity.executeQuery()) {
                if (resultSet.next()) {
                    int activityCount = resultSet.getInt(1);
                    if (activityCount > 0) {
                        System.err.println("Error deleting category: There are " + activityCount + " activities associated with this category. Cannot delete.");
                        return; 
                    }
                }
            }

             preparedStatementDeleteCategory.setString(1, id);
            int rowsAffected = preparedStatementDeleteCategory.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Category with ID " + id + " deleted successfully!");
            } else {
                System.err.println("Error deleting category: No category found with ID " + id);
            }

        } catch (SQLException e) {
            System.err.println("Error deleting category: " + e.getMessage());
        }
    }


    public void updateCategory(Category category) {
        String query = "UPDATE t_category SET pro_id = ?, cat_name = ?, status = ?, updated_at = ?, updated_by = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, category.getProjectId());
            preparedStatement.setString(2, category.getCategoryName());
            preparedStatement.setBoolean(3, category.isStatus());
            preparedStatement.setTimestamp(4, category.getUpdatedAt());
            preparedStatement.setString(5, category.getUpdatedBy());
            preparedStatement.setString(6, category.getId());

            preparedStatement.executeUpdate();
            System.out.println("Category with ID " + category.getId() + " updated successfully!");

        } catch (SQLException e) {
            System.err.println("Error updating category: " + e.getMessage());
        }
    }
}
