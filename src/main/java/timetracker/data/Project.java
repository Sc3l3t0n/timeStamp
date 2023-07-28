package timetracker.data;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a project.
 * A project has a name, a description, a parent, subprojects, tasks and tags.
 *
 * @author Marlon Rosenberg
 * @version 0.2
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
     * @param projectID The unique id of the project.
     * @param name The name of the project.
     * @param description The description of the project.
     * @param parent The parent of the project.
     */
    public Project(int projectID, String name,String description, Project parent) {
        this.projectID = projectID;
        GlobalVariables.ID_TO_PROJECT_MAP.put(projectID, this);
        GlobalVariables.NAME_TO_PROJECT_MAP.put(name, this);

        this.name = name;
        this.description = description;
        this.parent = parent;
        if (parent != null) parent.addSubProject(this);

        this.tags = new ArrayList<>();

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

    // Utility

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return projectID == project.projectID;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectID=" + projectID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parent=" + parent +
                ", tags=" + tags +
                '}';
    }
}
