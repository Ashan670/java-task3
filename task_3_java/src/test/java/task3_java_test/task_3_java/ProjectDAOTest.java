package task3_java_test.task_3_java;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import com.models.Project;
import com.dao.ProjectDAO;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;


public class ProjectDAOTest {

	private ProjectDAO projectDAO;

    @Before
    public void setUp() {
        projectDAO = new ProjectDAO();
    }

    @Test
    public void testInsertProject() {
        Project project = new Project();
        project.setId("3");
        project.setProjectName("Sample Project");
        project.setStatus(true);
        project.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        project.setCreatedBy("Admin");
        project.setUpdatedAt(null);
        project.setUpdatedBy(null);

        projectDAO.insertProject(project);

        Project loadedProject = projectDAO.loadProjectById("1");

        assertNotNull(loadedProject);

        assertEquals("1", loadedProject.getId());
        assertEquals("Sample Project", loadedProject.getProjectName());
        assertEquals(true, loadedProject.isStatus());
        assertEquals("Admin", loadedProject.getCreatedBy());
    }
}
