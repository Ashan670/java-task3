package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.models.Project;
import com.DatabaseConnection.DatabaseConnection;

public class ProjectDAO {
    public void insertProject(Project project) {
        String query = "INSERT INTO t_project (id, pro_name, status, created_at, created_by, updated_at, updated_by) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, project.getId());
            preparedStatement.setString(2, project.getProjectName());
            preparedStatement.setBoolean(3, project.isStatus());
            preparedStatement.setTimestamp(4, project.getCreatedAt());
            preparedStatement.setString(5, project.getCreatedBy());
            preparedStatement.setTimestamp(6, project.getUpdatedAt());
            preparedStatement.setString(7, project.getUpdatedBy());

            preparedStatement.executeUpdate();
            System.out.println("Project Data insert  successfully!");

        } catch (SQLException e) {
            System.err.println("Error inserting project data: " + e.getMessage());
        }
    }
    public Project loadProjectById(String id) {
        Project project = null;
        String query = "SELECT * FROM t_project WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    project = new Project();
                    project.setId(resultSet.getString("id"));
                    project.setProjectName(resultSet.getString("pro_name"));
                    project.setStatus(resultSet.getBoolean("status"));
                    project.setCreatedAt(resultSet.getTimestamp("created_at"));
                    project.setCreatedBy(resultSet.getString("created_by"));
                    project.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    project.setUpdatedBy(resultSet.getString("updated_by"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error loading project: " + e.getMessage());
        }

        return project;
    }
    
    public void deleteProjectById(String id) {
        String query = "DELETE FROM t_project WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Project with ID " + id + " deleted successfully!");

        } catch (SQLException e) {
            System.err.println("Error deleting project: " + e.getMessage());
        }
    }

    public void updateProject(Project project) {
        String query = "UPDATE t_project SET pro_name = ?, status = ?, updated_at = ?, updated_by = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setBoolean(2, project.isStatus());
            preparedStatement.setTimestamp(3, project.getUpdatedAt());
            preparedStatement.setString(4, project.getUpdatedBy());
            preparedStatement.setString(5, project.getId());

            preparedStatement.executeUpdate();
            System.out.println("Project with ID " + project.getId() + " updated successfully!");

        } catch (SQLException e) {
            System.err.println("Error updating project: " + e.getMessage());
        }
    
}


   
}


