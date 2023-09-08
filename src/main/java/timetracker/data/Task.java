package timetracker.data;

import timetracker.API.DataReader;
import timetracker.API.DataRemover;
import timetracker.API.DataWriter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a task.
 * A Task is a {@link DataType}
 * A task has a name, a project, time intervals and tags.
 * A task can be written to the database, updated in the database and removed from the database.
 * A task can be added to the global variables, removed from the global variables and updated in the global variables.
 *
 * @author Marlon Rosenberg
 * @version 0.9
 */
public class Task extends DataType{

    // Attributes

    /**
     * The name of the task.
     */
    private String name;

    /**
     * The project of the task.
     */
    private Project project;

    /**
     * The time intervals of the task.
     */
    final private List<TimeInterval> timeIntervals;

    /**
     * The current time interval of the task.
     */
    private TimeInterval currentInterval;

    /**
     * The tags of the task.
     */
    final private List<Tag> tags;

    // Constructors

    /**
     * Creates a new task with the given name, project, time intervals and tags.
     * If project is not null, the task will be added to the project's tasks.
     *
     * @param id The unique ID of the task.
     * @param name The name of the task.
     * @param project The project of the task.

     */
    public Task (int id, String name, Project project) {
        super(id);

        if (project != null) project.addTask(this);

        this.name = name;
        this.project = project;
        this.timeIntervals = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    // Methods

    /**
     * Starts the task with a new time interval.
     * If the task is already running, an exception is thrown.
     *
     * @throws IllegalStateException If the task is already running.
     */
    public void start() {
        if (currentInterval != null) {
            throw new IllegalStateException("Task already running");
        }
        this.currentInterval = new TimeInterval(GlobalVariables.getNextTimeIntervalId(), this);
        this.currentInterval.start();
    }

    /**
     * Stops the task by stopping the current time interval.
     * If the task is not running, an exception is thrown.
     *
     * @throws IllegalStateException If the task is not running.
     */
    public void stop() {
        if (currentInterval == null) {
            throw new IllegalStateException("Task not running");
        }
        this.currentInterval.stop();
        this.currentInterval = null;
    }

    /**
     * Removes the task from global variables but not from the database.
     * Removes the task from the project.
     * Removes all time intervals of the task.
     */
    @Override
    public void remove() {
        if (project != null) project.removeTask(this);
        for (TimeInterval timeInterval : timeIntervals) {
            timeInterval.remove();
        }
        timeIntervals.clear();
        removeGlobal();
    }

    // Global methods

    @Override
    public void addGlobal() {
        GlobalVariables.ID_TO_TASK_MAP.put(getID(), this);
        GlobalVariables.NAME_TO_TASK_MAP.put(name, this);
    }

    @Override
    public void removeGlobal() {
        GlobalVariables.ID_TO_TASK_MAP.remove(getID());
        GlobalVariables.NAME_TO_TASK_MAP.remove(name);
    }


    // Database methods

    @Override
    public void writeDatabase() {
    	DataWriter dataWriter = new DataWriter();
        dataWriter.writeTask(this);
        dataWriter.close();
    }

    @Override
    public void updateDatabase() {
        DataWriter dataWriter = new DataWriter();
        dataWriter.updateTask(this);
        dataWriter.close();
    }

    @Override
    public void removeDatabase() {
        DataRemover dataRemover = new DataRemover();
        dataRemover.removeTask(this);
        dataRemover.close();
    }


    // Setter and Getter

    /**
     * Returns the duration of all time intervals of the task.
     *
     * @return The duration of all time intervals of the task.
     */
    public Duration getDuration() {
    	Duration duration = Duration.ZERO;
    	for (TimeInterval timeInterval : timeIntervals) {
    		duration = duration.plus(timeInterval.getDuration());
    	}
    	return duration;
    }

    /**
     * Returns the name of the task.
     *
     * @return The name of the task.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task.
     *
     * @param name The name of the task.
     */
    public void setName(String name) {
        GlobalVariables.NAME_TO_TASK_MAP.remove(name);
        this.name = name;
        GlobalVariables.NAME_TO_TASK_MAP.put(name, this);
    }

    /**
     * Returns the project of the task.
     *
     * @return The project of the task.
     */
    public Project getProject() {
        return project;
    }

    /**
     * Sets the project of the task.
     *
     * @param project The project of the task.
     */
    protected void setProject(Project project) {
        this.project = project;
    }

    /**
     * Returns the time intervals of the task.
     *
     * @return The time intervals of the task.
     */
    public List<TimeInterval> getTimeIntervals() {
        return timeIntervals;
    }

    /**
     * Returns the tags of the task.
     *
     * @return The tags of the task.
     */
    public List<Tag> getTags() {
        return tags;
    }

    // Methods

    /**
     * Adds a time interval to the task.
     *
     * @param timeInterval The time interval to add.
     */
    public void addTimeInterval(TimeInterval timeInterval) {
        this.timeIntervals.add(timeInterval);
    }

    /**
     * Removes a time interval from the task.
     *
     * @param timeInterval The time interval to remove.
     * @return True if the time interval was removed, false otherwise.
     */
    public boolean removeTimeInterval(TimeInterval timeInterval) {
        return this.timeIntervals.remove(timeInterval);
    }

    /**
     * Adds a tag to the task.
     *
     * @param tag The tag to add.
     */
    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    /**
     * Removes a tag from the task.
     *
     * @param tag The tag to remove.
     */
    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }

    // Utility

    @Override
    public String toString() {
        return name;
    }
}
