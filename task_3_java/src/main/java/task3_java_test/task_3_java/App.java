package task3_java_test.task_3_java;
import java.sql.Timestamp;
import com.models.Project;
import com.dao.ProjectDAO;
public class App 
{
    public static void main( String[] args )
    {
    	   // Inserting a project
        Project project = new Project();
        project.setId("1");
        project.setProjectName("Sample Project");
        project.setStatus(true);
        project.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        project.setCreatedBy("Admin");
        project.setUpdatedAt(null);
        project.setUpdatedBy(null);

        ProjectDAO projectDAO = new ProjectDAO();
        projectDAO.insertProject(project);

      
    }
}
