package task3_java_test.task_3_java;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import com.dao.ActivityDAO;
import com.models.Activity;

public class ActivityDAOTest {

    private ActivityDAO activityDAO;

    @Before
    public void setUp() {
        activityDAO = new ActivityDAO();
    }

    @Test
    public void testInsertActivity() {
        Activity activity = new Activity();
        activity.setId("1");
        activity.setProjectId("5");
        activity.setCategoryId("3");
        activity.setActivityName("Sample Activity");
        activity.setStatus(true);
        activity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        activity.setCreatedBy("Admin");
        activity.setUpdatedAt(null);
        activity.setUpdatedBy(null);

        activityDAO.insertActivity(activity);

        Activity loadedActivity = activityDAO.loadActivityById("1");

        assertNotNull(loadedActivity);

        assertEquals("1", loadedActivity.getId());
        assertEquals("5", loadedActivity.getProjectId());
        assertEquals("10", loadedActivity.getCategoryId());
        assertEquals("Sample Activity", loadedActivity.getActivityName());
        assertEquals(true, loadedActivity.isStatus());
        assertEquals("Admin", loadedActivity.getCreatedBy());
    }

    @Test
    public void testLoadActivityById() {
        // Assuming activity with ID "1" exists
        Activity loadedActivity = activityDAO.loadActivityById("1");

        assertNotNull(loadedActivity);
        assertEquals("1", loadedActivity.getId());
    }

    @Test
    public void testDeleteActivityById() {
         activityDAO.deleteActivityById("1");

        Activity deletedActivity = activityDAO.loadActivityById("1");

         assertEquals(null, deletedActivity);
    }
    
    @Test
    public void testUpdateActivity() {
        Activity activity = new Activity();
        activity.setId("1");
        activity.setProjectId("2");  
        activity.setCategoryId("11");  
        activity.setActivityName("Updated Activity");
        activity.setStatus(false);
        activity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        activity.setUpdatedBy("Admin");

        activityDAO.updateActivity(activity);

        Activity updatedActivity = activityDAO.loadActivityById("1");

        assertNotNull(updatedActivity);

        assertEquals("1", updatedActivity.getId());
        assertEquals("2", updatedActivity.getProjectId());
        assertEquals("11", updatedActivity.getCategoryId());
        assertEquals("Updated Activity", updatedActivity.getActivityName());
        assertEquals(false, updatedActivity.isStatus());
        assertEquals("Admin", updatedActivity.getUpdatedBy());
    }
}
