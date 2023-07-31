package timetracker.data;

import timetracker.API.DataRemover;
import timetracker.API.DataWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a project.
 * A project is a {@link DataType}
 * A project has a name, a description, a parent, subprojects, tasks and tags.
 * A project can be written to the database, updated in the database and removed from the database.
 * A project can be added to the global variables, removed from the global variables and updated in the global variables.
 *
 * @author Marlon Rosenberg
 * @version 0.9
 */
public class Project extends DataType{

    // Attributes

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
     * If parent is not null, the project will be added to the parent's subprojects.
     *
     * @param id The unique id of the project.
     * @param name The name of the project.
     * @param description The description of the project.
     * @param parent The parent of the project.
     */
    public Project(int id, String name,String description, Project parent) {
        super(id);

        if (parent != null) parent.addSubProject(this);

        this.name = name;
        this.description = description;
        this.parent = parent;

        this.tags = new ArrayList<>();

        this.subProjects = new ArrayList<>();
        this.tasks = new ArrayList<>();
    }

    /**
     * Removes the project from the global variables but not from the database.
     * Removes the project from parent if it has one.
     * Removes the project as parent from all subprojects.
     */
    @Override
    public void remove() {
        if (parent != null) parent.removeSubProject(this);
        for (Project subProject : subProjects) {
            subProject.setParent(null);
        }
        for (Task task : tasks) {
            task.setProject(null);
        }
        removeGlobal();
    }

    // Global methods

    @Override
    public void addGlobal() {
        GlobalVariables.ID_TO_PROJECT_MAP.put(getID(), this);
        GlobalVariables.NAME_TO_PROJECT_MAP.put(name, this);
    }

    @Override
    public void removeGlobal() {
        GlobalVariables.ID_TO_PROJECT_MAP.remove(getID());
        GlobalVariables.NAME_TO_PROJECT_MAP.remove(name);
    }

    // Database methods

    @Override
    public void writeDatabase() {
        DataWriter dataWriter = new DataWriter();
        dataWriter.writeProject(this);
        dataWriter.close();
    }

    @Override
    public void updateDatabase() {
        DataWriter dataWriter = new DataWriter();
        dataWriter.updateProject(this);
        dataWriter.close();
    }

    @Override
    public void removeDatabase() {
        DataRemover dataRemover = new DataRemover();
        dataRemover.removeProject(this);
        dataRemover.close();
    }


    // Setter and Getter

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
     * Removes the old name from the name to project map and adds the new name to the map.
     * See {@link GlobalVariables#NAME_TO_PROJECT_MAP}.
     *
     * @param name The name of the project.
     */
    public void setName(String name) {
        GlobalVariables.NAME_TO_PROJECT_MAP.remove(this.name);
        this.name = name;
        GlobalVariables.NAME_TO_PROJECT_MAP.put(name, this);
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

    /**
     * Returns the tags of the project.
     *
     * @return The tags of the project.
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Returns the subprojects of the project.
     *
     * @return The subprojects of the project.
     */
    public List<Project> getSubProjects() {
        return subProjects;
    }

    /**
     * Returns the tasks of the project.
     *
     * @return The tasks of the project.
     */
    public List<Task> getTasks() {
        return tasks;
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
    public String toString() {
        return "Project{" +
                "projectID=" + getID() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parent=" + parent +
                ", tags=" + tags +
                '}';
    }
}
