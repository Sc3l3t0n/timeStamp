package timetracker.data;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * This class represents a time interval.
 * A time interval has a start time and an end time.
 *
 * @author Marlon Rosenberg
 * @version 0.2
 */
public class TimeInterval{

    // Attributes

    /**
     * The unique ID of the time interval.
     */
    private final int intervalID;

    /**
     * The task of the time interval.
     */
    private Task task;

    /**
     * The start time of the time interval.
     */
    private LocalDateTime startTime;

    /**
     * The end time of the time interval.
     */
    private LocalDateTime endTime;

    /**
     * The play state of the time interval.
     */
    private Boolean play;

    /**
     * Creates a new time interval with the given task.
     * If the task is not null, the time interval will be added to the task's time intervals.
     *
     * @param intervalID The unique ID of the time interval.
     * @param task The task of the time interval.
     */
    public TimeInterval(int intervalID, Task task) {
        this.intervalID = intervalID;
        GlobalVariables.TIME_INTERVAL_MAP.put(intervalID, this);
        this.task = task;

        if (task != null) task.addTimeInterval(this);

        this.startTime = null;
        this.endTime = null;
        this.play = false;
    }

    /**
     * Returns the duration of the time interval.
     *
     * @return The duration of the time interval.
     */
    Duration getDuration() {
        return Duration.between(startTime, endTime);
    }

    /**
     * Starts the time interval.
     * If the time interval is already started, nothing happens.
     */
    public void start() {
        if(!play) {
            if (startTime == null) {
               this.startTime = LocalDateTime.now();
            }
            play = true;
        }
    }

    /**
     * Stops the time interval.
     * If the time interval is already stopped, nothing happens.
     */
    public void stop() {
        if (play) {
            this.endTime = LocalDateTime.now();
            play = false;
        }
    }

    // Getter and Setter

    /**
     * Returns the task of the time interval.
     *
     * @return The task of the time interval.
     */
    public Task getTask() {
        return task;
    }

    /**
     * Sets the task of the time interval.
     *
     * @param task The task of the time interval.
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * Returns the unique ID of the time interval.
     *
     * @return The unique ID of the time interval.
     */
    public int getIntervalID() {
        return intervalID;
    }

    /**
     * Sets the start time of the time interval.
     *
     * @param startTime The start time of the time interval.
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the start time of the time interval.
     *
     * @return The start time of the time interval.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the end time of the time interval.
     *
     * @param endTime The end time of the time interval.
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Returns the end time of the time interval.
     *
     * @return The end time of the time interval.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * Returns the play state of the time interval.
     *
     * @return The play state of the time interval.
     */
    public Boolean getPlay() {
        return play;
    }

    @Override
    public String toString() {
        return "TimeInterval{" +
                "intervalID=" + intervalID +
                ", task=" + task +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", play=" + play +
                '}';
    }
}
