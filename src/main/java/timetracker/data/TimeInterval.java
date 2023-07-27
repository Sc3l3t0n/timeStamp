package timetracker.data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;

/**
 * This class represents a time interval.
 * A time interval has a start time and an end time.
 *
 * @version 0.1
 */
public class TimeInterval{

    private final int intervalID;

    private Task task;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Boolean play;

    public TimeInterval(int intervalID, Task task) {
        this.intervalID = intervalID;
        GlobalVariables.TIME_INTERVAL_MAP.put(intervalID, this);
        this.task = task;

        if (task != null) task.addTimeInterval(this);

        this.startTime = null;
        this.endTime = null;
        this.play = false;
    }

    Duration getDuration() {
        return Duration.between(startTime, endTime);
    }

    public void start() {
        if(!play) {
            if (startTime == null) {
               this.startTime = LocalDateTime.now();
            }
            play = true;
        }
    }

    public void stop() {
        if (play) {
            this.endTime = LocalDateTime.now();
            play = false;
        }
    }

    // Getter and Setter

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getIntervalID() {
        return intervalID;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Boolean getPlay() {
        return play;
    }
}
