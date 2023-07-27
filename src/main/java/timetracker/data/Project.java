package timetracker.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a project.
 * A project has a name, a description, a parent, subprojects, tasks and tags.
 *
 * @version 0.1
 */
public class Project {

    // Attributes

    /**
     * The unique ID of the project.
     */
    private final int projectID;

    /**
     * The name of the project.
     */
    private String name;

    /**
     * The description of the project.
     */
    private String description;

    /**
     * The parent of the project.
     */
    private Project parent;

    /**
     * The tags of the project.
     */
    final private List<Tag> tags;

    /**
     * The subprojects of the project.
     */
    final private List<Project> subProjects;

    /**
     * The tasks of the project.
     */
    final private List<Task> tasks;

    // Constructors

    /**
     * Creates a new project with the given name, description, parent and tags.
     *
     * @param name The name of the project.
     * @param description The description of the project.
     * @param parent The parent of the project.
     * @param tags The tags of the project.
     */
    public Project(String name,String description, Project parent, List<Tag> tags) {
        this.projectID = GlobalVariables.PROJECT_ID + 1;
        GlobalVariables.PROJECT_ID += 1;
        this.name = name;
        this.description = description;
        this.parent = parent;
        this.tags = tags;

        this.subProjects = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    // Setter and Getter

    /**
     * Returns the unique ID of the project.
     *
     * @return The unique ID of the project.
     */
    public int getProjectID() {
        return projectID;
    }

    /**
     * Returns the name of the project.
     *
     * @return The name of the project.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the project.
     *
     * @param name The name of the project.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the project.
     *
     * @return The description of the project.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the project.
     *
     * @param description The description of the project.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the tags of the project.
     *
     * @return The tags of the project.
     */
    public Project getParent() {
        return parent;
    }

    /**
     * Sets the parent of the project.
     *
     * @param parent The parent of the project.
     */
    public void setParent(Project parent) {
        this.parent = parent;
    }

    // Methods

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

    /**
     * Adds a subproject to the project.
     *
     * @param project The subproject to add.
     */
    public void addSubProject(Project project) {
        this.subProjects.add(project);
    }

    /**
     * Removes a subproject from the project.
     *
     * @param project The subproject to remove.
     * @return True if the subproject was removed, false otherwise.
     */
    public boolean removeSubProject(Project project) {
        return this.subProjects.remove(project);
    }

    /**
     * Adds a task to the project.
     *
     * @param task The task to add.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes a task from the project.
     *
     * @param task The task to remove.
     * @return True if the task was removed, false otherwise.
     */
    public boolean removeTask(Task task) {
        return this.tasks.remove(task);
    }


}
