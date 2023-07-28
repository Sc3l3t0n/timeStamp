package timetracker.API;

import org.junit.jupiter.api.Test;
import timetracker.data.*;

import java.awt.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    private final DataReader dataReader = new DataReader();
    private final DataWriter dataWriter = new DataWriter();
    private final DataRemover dataRemover = new DataRemover();

    @Test
    void testDataProjects() {

        Project project = new Project(-2, "JUnitTest", "An Test Project", null);
        GlobalVariables.PROJECT_MAP.remove(-2);

        // write
        dataWriter.writeProject(project);
        assertTrue(dataReader.readAllProjects().contains(project));
        assertTrue(GlobalVariables.PROJECT_MAP.containsValue(project));

        //  update
        project.setName("JUnitTestUpdate");
        dataWriter.updateProject(project);
        GlobalVariables.PROJECT_MAP.remove(-2);
        assertTrue(dataReader.readAllProjects().contains(project));
        assertEquals(project.getName(), GlobalVariables.PROJECT_MAP.get(-2).getName());

        // remove
        dataRemover.removeProject(project);
        GlobalVariables.PROJECT_MAP.remove(-2);
        assertFalse(dataReader.readAllProjects().contains(project));
        assertNull(GlobalVariables.PROJECT_MAP.get(-2));
    }

    @Test
    void testDataTags() {
        Tag tag = new Tag(-2, "JUnitTest", null, Color.BLUE);
        GlobalVariables.TAG_MAP.remove(-2);

        // write
        dataWriter.writeTag(tag);
        assertTrue(dataReader.readAllTags().contains(tag));
        assertTrue(GlobalVariables.TAG_MAP.containsValue(tag));

        // update
        tag.setName("JUnitTestUpdate");
        dataWriter.updateTag(tag);
        GlobalVariables.TAG_MAP.remove(-2);
        assertTrue(dataReader.readAllTags().contains(tag));
        assertEquals(tag.getName(), GlobalVariables.TAG_MAP.get(-2).getName());

        // remove
        dataRemover.removeTag(tag);
        GlobalVariables.TAG_MAP.remove(-2);
        assertFalse(dataReader.readAllTags().contains(tag));
        assertNull(GlobalVariables.TAG_MAP.get(-2));
    }

    @Test
    void testDataTasks() {
        Task task = new Task(-2, "JUnitTest", null);
        GlobalVariables.TASK_MAP.remove(-2);

        // write
        dataWriter.writeTask(task);
        assertTrue(dataReader.readAllTasks().contains(task));
        assertTrue(GlobalVariables.TASK_MAP.containsValue(task));

        // update
        task.setName("JUnitTestUpdate");
        dataWriter.updateTask(task);
        GlobalVariables.TASK_MAP.remove(-2);
        assertTrue(dataReader.readAllTasks().contains(task));
        assertEquals(task.getName(), GlobalVariables.TASK_MAP.get(-2).getName());

        // remove
        dataRemover.removeTask(task);
        GlobalVariables.TASK_MAP.remove(-2);
        assertFalse(dataReader.readAllTasks().contains(task));
        assertNull(GlobalVariables.TASK_MAP.get(-2));

    }

    @Test
    void testDataTimeIntervals() {
        TimeInterval timeInterval = new TimeInterval(-2, new Task(-2, "JUnitTest", null));
        GlobalVariables.TIME_INTERVAL_MAP.remove(-2);
        timeInterval.start();
        timeInterval.stop();

        // write
        dataWriter.writeTimeInterval(timeInterval);
        assertTrue(dataReader.readAllTimeIntervals().contains(timeInterval));
        assertTrue(GlobalVariables.TIME_INTERVAL_MAP.containsValue(timeInterval));

        // update
        timeInterval.setStartTime(LocalDateTime.now());
        dataWriter.updateTimeInterval(timeInterval);
        GlobalVariables.TIME_INTERVAL_MAP.remove(-2);
        assertTrue(dataReader.readAllTimeIntervals().contains(timeInterval));
        assertEquals(timeInterval.getStartTime(), GlobalVariables.TIME_INTERVAL_MAP.get(-2).getStartTime());

        // remove
        dataRemover.removeTimeInterval(timeInterval);
        GlobalVariables.TIME_INTERVAL_MAP.remove(-2);
        assertFalse(dataReader.readAllTimeIntervals().contains(timeInterval));
        assertNull(GlobalVariables.TIME_INTERVAL_MAP.get(-2));
    }

    @Test
    void readMaxIDs() {

        dataReader.readMaxIDs();
        System.out.println("Read Max IDs");

        int projectID = GlobalVariables.getLastProjectId() + 1;
        int tagID = GlobalVariables.getLastTagId() + 1;
        int taskID = GlobalVariables.getLastTaskId() + 1;
        int timeIntervalID = GlobalVariables.getLastTimeIntervalId() + 1;

        GlobalVariables.setProjectId(0);
        GlobalVariables.setTagId(0);
        GlobalVariables.setTaskId(0);
        GlobalVariables.setTimeIntervalId(0);
        System.out.println("Set Max IDs");

        dataWriter.updateMaxIDs();
        System.out.println("Update Max IDs");

        assertEquals(-1, GlobalVariables.getLastProjectId());
        assertEquals(-1, GlobalVariables.getLastTagId());
        assertEquals(-1, GlobalVariables.getLastTaskId());
        assertEquals(-1, GlobalVariables.getLastTimeIntervalId());

        GlobalVariables.setProjectId(projectID);
        GlobalVariables.setTagId(tagID);
        GlobalVariables.setTaskId(taskID);
        GlobalVariables.setTimeIntervalId(timeIntervalID);
        System.out.println("Reset Max IDs");

        dataReader.readMaxIDs();

        assertEquals(-1, GlobalVariables.getLastProjectId());
        assertEquals(-1, GlobalVariables.getLastTagId());
        assertEquals(-1, GlobalVariables.getLastTaskId());
        assertEquals(-1, GlobalVariables.getLastTimeIntervalId());

        GlobalVariables.setProjectId(projectID);
        GlobalVariables.setTagId(tagID);
        GlobalVariables.setTaskId(taskID);
        GlobalVariables.setTimeIntervalId(timeIntervalID);
        System.out.println("Reset Max IDs");

        dataWriter.updateMaxIDs();
    }
}