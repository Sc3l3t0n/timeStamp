package timetracker.data;

import timetracker.API.DataRemover;
import timetracker.API.DataWriter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class represents a time interval.
 * A time interval has a start time and an end time.
 *
 * @author Marlon Rosenberg
 * @version 0.9
 */
public class TimeInterval extends DataType{

    // Attributes

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
    private Boolean running;

    /**
     * Creates a new time interval with the given task.
     * If the task is not null, the time interval will be added to the task's time intervals.
     *
     * @param id The unique ID of the time interval.
     * @param task The task of the time interval.
     */
    public TimeInterval(int id, Task task) {
        super(id);

        if (task != null) {
            task.addTimeInterval(this);
        }

        this.task = task;
        this.startTime = null;
        this.endTime = null;
        this.running = false;
    }

    // Methods

    /**
     * Starts the time interval.
     * If the time interval is already started, nothing happens.
     */
    public void start() {
        if(!running) {
            if (startTime == null) {
               this.startTime = LocalDateTime.now();
            }
            running = true;
        }
    }

    /**
     * Stops the time interval.
     * The end time of the time interval will be set to the current time.
     * The time interval will be written to the database.
     * If the time interval is already stopped, nothing happens.
     */
    public void stop() {
        if (running) {
            this.endTime = LocalDateTime.now();
            running = false;
            writeDatabase();
        }
    }

    /**
     * Removes the time interval from the global variables and from the database.
     * The time interval will be removed from the task's time intervals.
     */
    @Override
    public void remove() {
        removeGlobal();
        removeDatabase();
        task.removeTimeInterval(this);
    }

    // Global methods

    @Override
    public void addGlobal() {
        GlobalVariables.ID_TO_TIME_INTERVAL_MAP.put(getID(), this);
    }

    @Override
    public void removeGlobal() {
        GlobalVariables.ID_TO_TIME_INTERVAL_MAP.remove(getID());
    }

    // Database methods

    @Override
    public void writeDatabase() {
        DataWriter dataWriter = new DataWriter();
        dataWriter.writeTimeInterval(this);
        dataWriter.close();
    }

    @Override
    public void updateDatabase() {
        DataWriter dataWriter = new DataWriter();
        dataWriter.updateTimeInterval(this);
        dataWriter.close();
    }

    @Override
    public void removeDatabase() {
        DataRemover dataRemover = new DataRemover();
        dataRemover.removeTimeInterval(this);
        dataRemover.close();
    }

    // Getter and Setter

    /**
     * Returns the duration of the time interval.
     *
     * @return The duration of the time interval.
     */
    public Duration getDuration() {
        return Duration.between(startTime, endTime);
    }

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
    public Boolean getRunning() {
        return running;
    }

    // Utility

    @Override
    public String toString() {
        long s = getDuration().getSeconds();
        return "Start: " + startTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) +
                ", End: " + endTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")) +
                ", Duration: " + String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
    }
}
