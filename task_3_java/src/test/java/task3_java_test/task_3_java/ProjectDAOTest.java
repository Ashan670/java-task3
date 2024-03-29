package task3_java_test.task_3_java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.dao.ProjectDAO;
import com.models.Project;

public class ProjectDAOTest {

    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    private ProjectDAO projectDAO;

    @Before
    public void setUp() throws SQLException {
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        projectDAO = new ProjectDAO();
    }

    @Test
    public void testInsertProject() throws SQLException {
        Project project = new Project();
        project.setId("7");
        project.setProjectName("Sample Project");
        project.setStatus(true);
        project.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        project.setCreatedBy("Admin");
        project.setUpdatedAt(null);
        project.setUpdatedBy(null);

        String query = "INSERT INTO t_project (id, pro_name, status, created_at, created_by, updated_at, updated_by) VALUES (?, ?, ?, ?, ?, ?, ?)";
        when(connection.prepareStatement(query)).thenReturn(preparedStatement);

        projectDAO.insertProject(project);

        Project loadedProject = projectDAO.loadProjectById("2");

        assertNotNull(loadedProject);

        assertEquals("2", loadedProject.getId());
        assertEquals("Sample Project", loadedProject.getProjectName());
        assertEquals(true, loadedProject.isStatus());
        assertEquals("Admin", loadedProject.getCreatedBy());
    }

    @Test
    public void testLoadProjectById() throws SQLException {
        String id = "1";
        Project expectedProject = new Project();
        expectedProject.setId(id);
        expectedProject.setProjectName("Test Project");
        expectedProject.setStatus(true);
        expectedProject.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        expectedProject.setCreatedBy("Admin");

        String query = "SELECT * FROM t_project WHERE id = ?";
        when(connection.prepareStatement(query)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("id")).thenReturn(id);
        when(resultSet.getString("pro_name")).thenReturn(expectedProject.getProjectName());
        when(resultSet.getBoolean("status")).thenReturn(expectedProject.isStatus());
        when(resultSet.getTimestamp("created_at")).thenReturn(expectedProject.getCreatedAt());
        when(resultSet.getString("created_by")).thenReturn(expectedProject.getCreatedBy());

        Project loadedProject = projectDAO.loadProjectById(id);

        assertNotNull(loadedProject);

        assertEquals(id, loadedProject.getId());
        assertEquals(expectedProject.getProjectName(), loadedProject.getProjectName());
        assertEquals(expectedProject.isStatus(), loadedProject.isStatus());
        assertEquals(expectedProject.getCreatedAt(), loadedProject.getCreatedAt());
        assertEquals(expectedProject.getCreatedBy(), loadedProject.getCreatedBy());
    }

    @Test
    public void testDeleteProjectById() throws SQLException {
        String id = "1";
        String query = "DELETE FROM t_project WHERE id = ?";
        when(connection.prepareStatement(query)).thenReturn(preparedStatement);

        projectDAO.deleteProjectById(id);

         preparedStatement.executeUpdate();
    }

    @Test
    public void testUpdateProject() throws SQLException {
        Project project = new Project();
        project.setId("1");
        project.setProjectName("Updated Project");
        project.setStatus(false);
        project.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        project.setUpdatedBy("Admin");

        String query = "UPDATE t_project SET pro_name = ?, status = ?, updated_at = ?, updated_by = ? WHERE id = ?";
        when(connection.prepareStatement(query)).thenReturn(preparedStatement);

        projectDAO.updateProject(project);

        // Verify that executeUpdate() method was called once
        preparedStatement.executeUpdate();
    }
}
