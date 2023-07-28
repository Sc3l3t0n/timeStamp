package timetracker.data;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a task.
 * A task has a name, a project, time intervals and tags.
 *
 * @author Marlon Rosenberg
 * @version 0.2
 */
public class Task {

    // Attributes

    /**
     * The unique ID of the task.
     */
    final private int taskID;

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
     * The tags of the task.
     */
    final private List<Tag> tags;

    // Constructors

    /**
     * Creates a new task with the given name, project, time intervals and tags.
     *
     * @param taskID The unique ID of the task.
     * @param name The name of the task.
     * @param project The project of the task.

     */
    public Task (int taskID, String name, Project project) {
        this.taskID = taskID;
        GlobalVariables.ID_TO_TASK_MAP.put(taskID, this);
        GlobalVariables.NAME_TO_TASK_MAP.put(name, this);

        if (project != null) project.addTask(this);

        this.name = name;
        this.project = project;
        this.timeIntervals = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    // Methods



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
     * Returns the unique ID of the task.
     *
     * @return The unique ID of the task.
     */
    public int getTaskID() {
        return taskID;
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
        this.name = name;
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
    public void setProject(Project project) {
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
     * @return True if the tag was removed, false otherwise.
     */
    public boolean removeTag(Tag tag) {
        return this.tags.remove(tag);
    }

    // Utility

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskID == task.taskID;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskID=" + taskID +
                ", name='" + name + '\'' +
                ", project=" + project +
                ", timeIntervals=" + timeIntervals +
                '}';
    }
}
