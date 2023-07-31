package timetracker.API;

import org.junit.jupiter.api.Test;
import timetracker.data.*;

import java.awt.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionTest {

    private final DataReader dataReader = new DataReader();
    private final DataWriter dataWriter = new DataWriter();

    @Test
    void testDataProjects() {

        Project project = new Project(-2, "JUnitTest", "An Test Project", null);
        project.writeDatabase();

        // write
        assertTrue(dataReader.readAllProjects().contains(project));
        dataReader.readInProjectsToGlobal();
        assertTrue(GlobalVariables.ID_TO_PROJECT_MAP.containsValue(project));

        //  update
        project.setName("JUnitTestUpdate");
        project.updateDatabase();
        assertTrue(dataReader.readAllProjects().contains(project));
        dataReader.readInProjectsToGlobal();
        assertEquals(project.getName(), GlobalVariables.ID_TO_PROJECT_MAP.get(-2).getName());

        // remove
        project.remove();
        project.removeDatabase();
        assertFalse(dataReader.readAllProjects().contains(project));
        assertNull(GlobalVariables.ID_TO_PROJECT_MAP.get(-2));
    }

    @Test
    void testDataTags() {
        Tag tag = new Tag(-2, "JUnitTest", null, Color.BLUE);
        tag.writeDatabase();

        // write
        assertTrue(dataReader.readAllTags().contains(tag));
        dataReader.readInTagsToGlobal();
        assertTrue(GlobalVariables.ID_TO_TAG_MAP.containsValue(tag));

        // update
        tag.setName("JUnitTestUpdate");
        tag.updateDatabase();
        assertTrue(dataReader.readAllTags().contains(tag));
        dataReader.readInTagsToGlobal();
        assertEquals(tag.getName(), GlobalVariables.ID_TO_TAG_MAP.get(-2).getName());

        // remove
        tag.remove();
        tag.removeDatabase();
        assertFalse(dataReader.readAllTags().contains(tag));
        assertNull(GlobalVariables.ID_TO_TAG_MAP.get(-2));
    }

    @Test
    void testDataTasks() {
        Task task = new Task(-2, "JUnitTest", null);
        task.writeDatabase();

        // write
        assertTrue(dataReader.readAllTasks().contains(task));
        dataReader.readInTasksToGlobal();
        assertTrue(GlobalVariables.ID_TO_TASK_MAP.containsValue(task));

        // update
        task.setName("JUnitTestUpdate");
        task.updateDatabase();
        assertTrue(dataReader.readAllTasks().contains(task));
        dataReader.readInTasksToGlobal();
        assertEquals(task.getName(), GlobalVariables.ID_TO_TASK_MAP.get(-2).getName());

        // remove
        task.remove();
        task.removeDatabase();
        assertFalse(dataReader.readAllTasks().contains(task));
        assertNull(GlobalVariables.ID_TO_TASK_MAP.get(-2));

    }

    @Test
    void testDataTimeIntervals() {
        TimeInterval timeInterval = new TimeInterval(-2, new Task(-2, "JUnitTest", null));
        timeInterval.start();
        timeInterval.stop();

        // write
        assertTrue(dataReader.readAllTimeIntervals().contains(timeInterval));
        dataReader.readInAllToGlobal();
        assertTrue(GlobalVariables.ID_TO_TIME_INTERVAL_MAP.containsValue(timeInterval));

        // update
        timeInterval.setStartTime(LocalDateTime.now());
        timeInterval.updateDatabase();
        assertTrue(dataReader.readAllTimeIntervals().contains(timeInterval));
        dataReader.readInAllToGlobal();
        assertEquals(timeInterval.getStartTime(), GlobalVariables.ID_TO_TIME_INTERVAL_MAP.get(-2).getStartTime());

        // remove
        timeInterval.getTask().remove();
        timeInterval.remove();
        timeInterval.removeDatabase();
        assertFalse(dataReader.readAllTimeIntervals().contains(timeInterval));
        assertNull(GlobalVariables.ID_TO_TIME_INTERVAL_MAP.get(-2));
    }

    @Test
    void readMaxIDs() {

        dataReader.readInMaxIDsGlobal();
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

        dataReader.readInMaxIDsGlobal();

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