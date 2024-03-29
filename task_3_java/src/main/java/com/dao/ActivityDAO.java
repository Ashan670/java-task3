package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.models.Activity;
import com.DatabaseConnection.DatabaseConnection;

public class ActivityDAO {

	public void insertActivity(Activity activity) {
		String selectCategoryQuery = "SELECT * FROM t_category WHERE id = ?";
		String insertQuery = "INSERT INTO t_activity (id, pro_id, cat_id, act_name, status, created_at, created_by, updated_at, updated_by) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement selectCategoryStatement = connection.prepareStatement(selectCategoryQuery)) {
				selectCategoryStatement.setString(1, activity.getCategoryId());
				try (ResultSet categoryResultSet = selectCategoryStatement.executeQuery()) {
				if (!categoryResultSet.next()) {
					System.err.println("Error: Category with ID " + activity.getCategoryId() + " does not exist.");
					return;
				}
			}

			try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
				insertStatement.setString(1, activity.getId());
				insertStatement.setString(2, activity.getProjectId());
				insertStatement.setString(3, activity.getCategoryId());
				insertStatement.setString(4, activity.getActivityName());
				insertStatement.setBoolean(5, activity.isStatus());
				insertStatement.setTimestamp(6, activity.getCreatedAt());
				insertStatement.setString(7, activity.getCreatedBy());
				insertStatement.setTimestamp(8, activity.getUpdatedAt());
				insertStatement.setString(9, activity.getUpdatedBy());

				int rowsAffected = insertStatement.executeUpdate();
				if (rowsAffected > 0) {
					System.out.println("Activity data inserted successfully!");
				} else {
					System.err.println("Error inserting activity data!");
				}
			}

		} catch (SQLException e) {
			System.err.println("Error inserting activity data: " + e.getMessage());
		}
	}

	public Activity loadActivityById(String id) {
		Activity activity = null;
		String query = "SELECT * FROM t_activity WHERE id = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					activity = new Activity();
					activity.setId(resultSet.getString("id"));
					activity.setProjectId(resultSet.getString("pro_id"));
					activity.setCategoryId(resultSet.getString("cat_id"));
					activity.setActivityName(resultSet.getString("act_name"));
					activity.setStatus(resultSet.getBoolean("status"));
					activity.setCreatedAt(resultSet.getTimestamp("created_at"));
					activity.setCreatedBy(resultSet.getString("created_by"));
					activity.setUpdatedAt(resultSet.getTimestamp("updated_at"));
					activity.setUpdatedBy(resultSet.getString("updated_by"));
				}
			}

		} catch (SQLException e) {
			System.err.println("Error loading activity: " + e.getMessage());
		}

		return activity;
	}

	public void deleteActivityById(String id) {
		String query = "DELETE FROM t_activity WHERE id = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, id);
			preparedStatement.executeUpdate();
			System.out.println("Activity with ID " + id + " deleted successfully!");

		} catch (SQLException e) {
			System.err.println("Error deleting activity: " + e.getMessage());
		}
	}

	public void updateActivity(Activity activity) {
		String query = "UPDATE t_activity SET pro_id = ?, cat_id = ?, act_name = ?, status = ?, updated_at = ?, updated_by = ? WHERE id = ?";

		try (Connection connection = DatabaseConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, activity.getProjectId());
			preparedStatement.setString(2, activity.getCategoryId());
			preparedStatement.setString(3, activity.getActivityName());
			preparedStatement.setBoolean(4, activity.isStatus());
			preparedStatement.setTimestamp(5, activity.getUpdatedAt());
			preparedStatement.setString(6, activity.getUpdatedBy());
			preparedStatement.setString(7, activity.getId());

			preparedStatement.executeUpdate();
			System.out.println("Activity with ID " + activity.getId() + " updated successfully!");

		} catch (SQLException e) {
			System.err.println("Error updating activity: " + e.getMessage());
		}
	}
}
