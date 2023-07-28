package timetracker.data;

import java.util.HashMap;

/**
 * This class contains global variables.
 *
 * @version 0.1
 */
public class GlobalVariables {

    // IDs

    /**
     * The next ID of a project.
     */
    private static int PROJECT_ID = 0;

    /**
     * The next ID of a tag.
     */
    private static int TAG_ID = 0;

    /**
     * The next ID of a task.
     */
    private static int TASK_ID = 0;

    /**
     * The next ID of a time interval.
     */
    private static int TIME_INTERVAL_ID = 0;

    // Maps

    // Project
    /**
     * The map of all projects.
     * The key is the ID of the project.
     * The value is the project.
     */
    public static final HashMap<Integer, Project> ID_TO_PROJECT_MAP = new HashMap<>();

    /**
     * The map of all projects.
     * The key is the name of the project.
     * The value is the project.
     */
    public static final HashMap<String, Project> NAME_TO_PROJECT_MAP = new HashMap<>();

    // Tag

    /**
     * The map of all tags.
     * The key is the ID of the tag.
     * The value is the tag.
     */
    public static final HashMap<Integer, Tag> ID_TO_TAG_MAP = new HashMap<>();

    /**
     * The map of all tags.
     * The key is the name of the tag.
     * The value is the tag.
     */
    public static final HashMap<String, Tag> NAME_TO_TAG_MAP = new HashMap<>();

    // Task

    /**
     * The map of all tasks.
     * The key is the ID of the task.
     * The value is the task.
     */
    public static final HashMap<Integer, Task> ID_TO_TASK_MAP = new HashMap<>();

    /**
     * The map of all tasks.
     * The key is the name of the task.
     * The value is the task.
     */
    public static final HashMap<String, Task> NAME_TO_TASK_MAP = new HashMap<>();

    // TimeInterval

    /**
     * The map of all time intervals.
     * The key is the ID of the time interval.
     * The value is the time interval.
     */
    public static final HashMap<Integer, TimeInterval> ID_TO_TIME_INTERVAL_MAP = new HashMap<>();


    // Getter and Setter

    /**
     * Returns the last project ID.
     *
     * @return The last project ID.
     */
    public static int getLastProjectId() {
        return PROJECT_ID - 1;
    }

    /**
     * Returns the next project ID.
     *
     * @return The next project ID.
     */
    public static int getNextProjectId() {

        return PROJECT_ID++;
    }

    /**
     * Returns the last tag ID.
     *
     * @return The last tag ID.
     */
    public static int getLastTagId() {
        return TAG_ID - 1;
    }

    /**
     * Returns the next tag ID.
     *
     * @return The next tag ID.
     */
    public static int getNextTagId() {
        return TAG_ID++;
    }

    /**
     * Returns the last task ID.
     *
     * @return The last task ID.
     */
    public static int getLastTaskId() {
        return TASK_ID - 1;
    }

    /**
     * Returns the next task ID.
     *
     * @return The next task ID.
     */
    public static int getNextTaskId() {
        return TASK_ID++;
    }

    /**
     * Returns the last time interval ID.
     *
     * @return The last time interval ID.
     */
    public static int getLastTimeIntervalId() {
        return TIME_INTERVAL_ID - 1;
    }

    /**
     * Returns the next time interval ID.
     *
     * @return The next time interval ID.
     */
    public static int getNextTimeIntervalId() {
        return TIME_INTERVAL_ID++;
    }

    // Should not be used

    /**
     * Sets the project ID.
     * This method should not be used.
     * Only in the DataReader.
     *
     * @param projectId The project ID.
     */
    public static void setProjectId(int projectId) {
        PROJECT_ID = projectId;
    }

    /**
     * Sets the tag ID.
     * This method should not be used.
     * Only in the DataReader.
     *
     * @param tagId The tag ID.
     */
    public static void setTagId(int tagId) {
        TAG_ID = tagId;
    }

    /**
     * Sets the task ID.
     * This method should not be used.
     * Only in the DataReader.
     *
     * @param taskId The task ID.
     */
    public static void setTaskId(int taskId) {
        TASK_ID = taskId;
    }

    /**
     * Sets the time interval ID.
     * This method should not be used.
     * Only in the DataReader.
     *
     * @param timeIntervalId The time interval ID.
     */
    public static void setTimeIntervalId(int timeIntervalId) {
        TIME_INTERVAL_ID = timeIntervalId;
    }
}
