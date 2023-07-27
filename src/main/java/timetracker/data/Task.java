package timetracker.data;

import java.util.List;

/**
 * This class represents a task.
 * A task has a name, a project, time intervals and tags.
 *
 * @version 0.1
 */
public class Task {

    // Attributes

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
     * @param name The name of the task.
     * @param project The project of the task.
     * @param timeInterval The time intervals of the task.
     * @param tags The tags of the task.
     */
    public Task (String name, Project project, List<TimeInterval> timeInterval, List<Tag> tags) {
        this.taskID = GlobalVariables.TASK_ID;
        GlobalVariables.TASK_ID += 1;
        this.name = name;
        this.project = project;
        this.timeIntervals = timeInterval;
        this.tags = tags;
    }

    // Setter and Getter

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


}
