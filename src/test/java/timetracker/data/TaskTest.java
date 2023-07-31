package timetracker.data;

import org.junit.jupiter.api.Test;
import timetracker.API.DataReader;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void startStop() {
        Task task = new Task(-2, "JUnitTest", null);
        task.start();
        assertNotNull(task.getTimeIntervals().get(0).getStartTime());
        task.stop();
        assertNotNull(task.getTimeIntervals().get(0).getEndTime());
    }

    @Test
    void remove() {
        Project parent = new Project(-1, "JUnitTestParent", "An Test Parent Project", null);
        Task task = new Task(-3, "JUnitTest", parent);
        task.start();
        task.stop();
        task.remove();
        task.getTimeIntervals().forEach(timeInterval -> assertNull(timeInterval.getTask()));
        task.getTimeIntervals().forEach(TimeInterval::removeDatabase);
        assertFalse(parent.getTasks().contains(task));
        assertNull(GlobalVariables.ID_TO_TASK_MAP.get(task.getID()));
    }

    @Test
    void Global() {
        Task task = new Task(-2, "JUnitTest", null);
        task.addGlobal();
        assertTrue(GlobalVariables.ID_TO_TASK_MAP.containsValue(task));
        assertTrue(GlobalVariables.NAME_TO_TASK_MAP.containsKey(task.getName()));

        task.removeGlobal();
        assertFalse(GlobalVariables.ID_TO_TASK_MAP.containsValue(task));
        assertFalse(GlobalVariables.NAME_TO_TASK_MAP.containsKey(task.getName()));
    }

    @Test
    void Database() {
        Task task = new Task(-2, "JUnitTest", null);
        DataReader dataReader = new DataReader();
        task.writeDatabase();
        assertTrue(dataReader.readAllTasks().contains(task));
        task.setName("JUnitTestUpdate");
        task.updateDatabase();
        dataReader.readInTasksToGlobal();
        assertTrue(GlobalVariables.NAME_TO_TASK_MAP.containsKey("JUnitTestUpdate"));
        task.removeDatabase();
        task.remove();
        assertFalse(dataReader.readAllTasks().contains(task));
    }

    @Test
    void getDuration() {
        Task task = new Task(-2, "JUnitTest", null);
        task.start();
        task.stop();
        assertNotNull(task.getDuration());
    }

    @Test
    void Name() {
        Task task = new Task(-2, "JUnitTest", null);
        assertEquals("JUnitTest", task.getName());
        task.setName("JUnitTestUpdate");
        assertEquals("JUnitTestUpdate", task.getName());
    }

    @Test
    void Project() {
        Project project = new Project(-2, "JUnitTest", "An Test Project", null);
        Task task = new Task(-3, "JUnitTest", project);
        assertEquals(project, task.getProject());
        task.setProject(null);
        assertNull(task.getProject());
    }

    @Test
    void getTags() {
        Task task = new Task(-2, "JUnitTest", null);
        Tag tag = new Tag(-3, "JUnitTest", null, null);
        task.addTag(tag);
        assertTrue(task.getTags().contains(tag));
    }

    @Test
    void TimeInterval() {
        Task task = new Task(-2, "JUnitTest", null);
        TimeInterval timeInterval = new TimeInterval(-3, task);
        assertTrue(task.getTimeIntervals().contains(timeInterval));
        task.removeTimeInterval(timeInterval);
        assertFalse(task.getTimeIntervals().contains(timeInterval));
    }

    @Test
    void Tag() {
        Task task = new Task(-2, "JUnitTest", null);
        Tag tag = new Tag(-3, "JUnitTest", null, null);
        task.addTag(tag);
        assertTrue(task.getTags().contains(tag));
        task.removeTag(tag);
        assertFalse(task.getTags().contains(tag));
    }
}