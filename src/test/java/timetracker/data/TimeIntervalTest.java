package timetracker.data;

import org.junit.jupiter.api.Test;
import timetracker.API.DataReader;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeIntervalTest {

    @Test
    void startStop() {
        Task task = new Task(-1, "JUnitTest", null);
        TimeInterval timeInterval = new TimeInterval(-2, task);
        timeInterval.start();
        assertTrue(timeInterval.getRunning());
        timeInterval.stop();
        assertFalse(timeInterval.getRunning());
        timeInterval.removeDatabase();
    }

    @Test
    void Global() {
        Task task = new Task(-1, "JUnitTest", null);
        TimeInterval timeInterval = new TimeInterval(-2, task);
        timeInterval.addGlobal();
        assertTrue(GlobalVariables.ID_TO_TIME_INTERVAL_MAP.containsValue(timeInterval));

        timeInterval.removeGlobal();
        assertFalse(GlobalVariables.ID_TO_TIME_INTERVAL_MAP.containsValue(timeInterval));
    }

    @Test
    void Database() {
        Task task = new Task(-1, "JUnitTest", null);
        TimeInterval timeInterval = new TimeInterval(-5, task);
        DataReader dataReader = new DataReader();
        timeInterval.start();
        timeInterval.stop();
        assertTrue(dataReader.readAllTimeIntervals().contains(timeInterval));
        LocalDateTime now = LocalDateTime.now();
        timeInterval.setEndTime(now);
        timeInterval.updateDatabase();
        dataReader.readInTimeIntervalsToGlobal();
        assertEquals(GlobalVariables.ID_TO_TIME_INTERVAL_MAP.get(-5).getEndTime(), now);
        timeInterval.removeDatabase();
        assertFalse(dataReader.readAllTimeIntervals().contains(timeInterval));

    }

    @Test
    void getDuration() {
        Task task = new Task(-1, "JUnitTest", null);
        TimeInterval timeInterval = new TimeInterval(-3, task);
        timeInterval.start();
        timeInterval.stop();
        assertNotNull(timeInterval.getDuration());
        timeInterval.removeDatabase();
    }

    @Test
    void Task() {
        Task task = new Task(-1, "JUnitTest", null);
        Task task2 = new Task(-2, "JUnitTest2", null);
        TimeInterval timeInterval = new TimeInterval(-2, task);
        assertEquals(task, timeInterval.getTask());
        timeInterval.setTask(task2);
        assertEquals(task2, timeInterval.getTask());
    }

    @Test
    void StartTime() {
        Task task = new Task(-1, "JUnitTest", null);
        TimeInterval timeInterval = new TimeInterval(-2, task);
        timeInterval.setStartTime(LocalDateTime.now());
        assertNotNull(timeInterval.getStartTime());
    }

    @Test
    void EndTime() {
        Task task = new Task(-1, "JUnitTest", null);
        TimeInterval timeInterval = new TimeInterval(-2, task);
        timeInterval.setEndTime(LocalDateTime.now());
        assertNotNull(timeInterval.getEndTime());
    }

    @Test
    void getRunning() {
        Task task = new Task(-1, "JUnitTest", null);
        TimeInterval timeInterval = new TimeInterval(-2, task);
        timeInterval.start();
        assertTrue(timeInterval.getRunning());
        timeInterval.stop();
        assertFalse(timeInterval.getRunning());
        timeInterval.removeDatabase();
    }
}