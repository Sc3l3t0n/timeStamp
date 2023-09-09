package timetracker.API;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import timetracker.data.*;

/**
 * This class in an extension of the {@link DatabaseConnection}.
 * This class is used to write data to the database.
 * The DataWriter connects to the database and writes or updates the data.
 *
 * @author Marlon Rosenberg
 * @version 0.4
 */
public class DataWriter extends DatabaseConnection{

    /**
     * Creates a new DataWriter.
     * A DataWriter connects to the database.
     * Super constructor: {@link DatabaseConnection#DatabaseConnection()}
     */
    public DataWriter() {
        super();
    }

    // Project

    /**
     * Writes a project to the database.
     * Only use this method if the project is not already in the database.
     * An error will be thrown if the project is already in the database.
     *
     * @param project The project to be written to the database.
     */
    public void writeProject(Project project) {

        // SQL INSERT statement
        String sql = "INSERT INTO projects(project_id, name, description, parent_id) VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, project.getID());
            preparedStatement.setString(2, project.getName());
            preparedStatement.setString(3, project.getDescription());
            if (project.getParent() != null)
                preparedStatement.setInt(4, project.getParent().getID());
            else
                preparedStatement.setInt(4, -1);


            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Project added!");
            } else {
                System.out.println("Error adding the project!");
            }

            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println("Error when connecting to the database!");
        }
        updateProjectID();
    }

    /**
     * Updates a project in the database.
     * Only use this method if the project is already in the database.
     * The project will not be added to the database if it is not already in it.
     *
     * @param project The project to be updated in the database.
     */
    public void updateProject(Project project) {

        // SQL INSERT statement
        String sql = "UPDATE projects SET name = ?, description = ?, parent_id = ? WHERE project_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            if (project.getParent() != null)
                preparedStatement.setInt(3, project.getParent().getID());
            else
                preparedStatement.setInt(3, -1);


            preparedStatement.setInt(4, project.getID());

            // check if something was changed
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Project updated!");
            } else {
                System.out.println("Nothing from the project was updated!");
            }

            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println("Error when connecting to the database!");
        }
        updateProjectID();
    }

    // Tag

    /**
     * Writes a tag to the database.
     * Only use this method if the tag is not already in the database.
     * An error will be thrown if the tag is already in the database.
     *
     * @param tag The tag to be written to the database.
     */
    public void writeTag(Tag tag) {

        // SQL INSERT statement
        String sql = "INSERT INTO tags(tag_id, name, parent_id, color) VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, tag.getID());
            preparedStatement.setString(2, tag.getName());
            if (tag.getParent() != null)
                preparedStatement.setInt(3, tag.getParent().getID());
            else
                preparedStatement.setInt(3, -1);


            preparedStatement.setString(4, tag.getColor().toString());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Tag added!");
            } else {
                System.out.println("Error adding the tag");
            }

            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println("Error when connecting to the database!");
            throw new RuntimeException(e);
        }
        updateTagID();
    }

    /**
     * Updates a tag in the database.
     * Only use this method if the tag is already in the database.
     * The tag will not be added to the database if it is not already in it.
     *
     * @param tag The tag to be updated in the database.
     */
    public void updateTag(Tag tag) {

        // SQL INSERT statement
        String sql = "UPDATE tags SET name = ?, parent_id = ?, color = ? WHERE tag_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, tag.getName());
            // check if tag has a parent
            if (tag.getParent() != null)
                preparedStatement.setInt(2, tag.getParent().getID());
            else
                preparedStatement.setInt(2, -1);

            preparedStatement.setString(3, tag.getColor().toString());

            preparedStatement.setInt(4, tag.getID());

            // check if something was changed
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Tag updated!");
            } else {
                System.out.println("Nothing from the tag was updated!");
            }

            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println("Error when connecting to the database!");
            throw new RuntimeException(e);
        }
        updateTagID();
    }

    // Task

    /**
     * Writes a task to the database.
     * Only use this method if the task is not already in the database.
     * An error will be thrown if the task is already in the database.
     *
     * @param task The task to be written to the database.
     */
    public void writeTask(Task task) {

        // SQL INSERT statement
        String sql = "INSERT INTO tasks(task_id, name, project_id, tags) VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, task.getID());
            preparedStatement.setString(2, task.getName());

            if (task.getProject() != null)
                preparedStatement.setInt(3, task.getProject().getID());
            else
                preparedStatement.setInt(3, -1);

            StringBuilder sb = new StringBuilder();
            for (Tag tag : task.getTags()) {
                sb.append(tag.getID());
                sb.append(",");
            }
            if (!sb.isEmpty()) sb.deleteCharAt(sb.length() - 1);
            preparedStatement.setString(4, sb.toString());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Task added!");
            } else {
                System.out.println("Error adding the task!");
            }

            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println("Error when connecting to the database!");
        }
        updateTaskID();
    }

    /**
     * Updates a task in the database.
     * Only use this method if the task is already in the database.
     * The task will not be added to the database if it is not already in it.
     *
     * @param task The task to be updated in the database.
     */
    public void updateTask(Task task) {

        // SQL INSERT statement
        String sql = "UPDATE tasks SET name = ?, project_id = ? WHERE task_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, task.getName());
            if (task.getProject() != null)
                preparedStatement.setInt(2, task.getProject().getID());
            else
                preparedStatement.setInt(2, -1);

            preparedStatement.setInt(3, task.getID());

            // check if something was changed
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Task updated!");
            } else {
                System.out.println("Nothing from the task was updated!");
            }

            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println("Error when connecting to the database!");
        }
        updateTaskID();
    }

    // TimeIntervals

    /**
     * Writes a time interval to the database.
     * Only use this method if the time interval is not already in the database.
     * An error will be thrown if the time interval is already in the database.
     *
     * @param timeInterval The time interval to be written to the database.
     */
    public void writeTimeInterval(TimeInterval timeInterval) {

        if (timeInterval.getEndTime() == null)
            throw new IllegalArgumentException("The time interval must have an end time!");

        // SQL INSERT statement
        String sql = "INSERT INTO timeintervals(interval_id, task_id, start_time, end_time) VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, timeInterval.getID());
            preparedStatement.setInt(2, timeInterval.getTask().getID());
            preparedStatement.setString(3, timeInterval.getStartTime().toString());
            preparedStatement.setString(4, timeInterval.getEndTime().toString());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Interval added!");
            } else {
                System.out.println("Error adding the interval");
            }

            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println("Error when connecting to the database!");
            throw new RuntimeException(e);
        }
        updateIntervalID();
    }

    /**
     * Updates a time interval in the database.
     * Only use this method if the time interval is already in the database.
     * The time interval will not be added to the database if it is not already in it.
     *
     * @param timeInterval The time interval to be updated in the database.
     */
    public void updateTimeInterval(TimeInterval timeInterval) {

        // SQL INSERT statement
        String sql = "UPDATE timeintervals SET task_id =?, start_time = ?, end_time = ? WHERE interval_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, timeInterval.getTask().getID());
            preparedStatement.setString(2, timeInterval.getStartTime().toString());
            preparedStatement.setString(3, timeInterval.getEndTime().toString());
            preparedStatement.setInt(4, timeInterval.getID());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Interval updated!");
            } else {
                System.out.println("Nothing from the interval was updated!");
            }

            preparedStatement.close();

        } catch (SQLException e) {
            System.err.println("Error when connecting to the database!");
            throw new RuntimeException(e);
        }
        updateIntervalID();
    }

    // MaxIDs

    /**
     * Updates every max IDs to the database.
     * <p>
     * Performances issues may occur.
     * Use {@link DataWriter#updateProjectID()}, {@link DataWriter#updateTagID()},
     * {@link DataWriter#updateTaskID()} or {@link DataWriter#updateIntervalID()} instead for better performances.
     */
    public void updateMaxIDs() {

        // SQL INSERT statement
        String sql = "UPDATE maxids SET max_id = ? WHERE data_type = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Project_ID
            preparedStatement.setInt(1, GlobalVariables.getLastProjectId() + 1);
            preparedStatement.setString(2, "project_id");

            preparedStatement.executeUpdate();

            // Tag_ID
            preparedStatement.setInt(1, GlobalVariables.getLastTagId() + 1);
            preparedStatement.setString(2, "tag_id");

            preparedStatement.executeUpdate();

            // Task_ID
            preparedStatement.setInt(1, GlobalVariables.getLastTaskId() + 1);
            preparedStatement.setString(2, "task_id");

            preparedStatement.executeUpdate();

            // Interval_ID
            preparedStatement.setInt(1, GlobalVariables.getLastTimeIntervalId() + 1);
            preparedStatement.setString(2, "interval_id");

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates ProjectID to the database.
     * Better performances than {@link DataWriter#updateMaxIDs()}.
     */
    public void updateProjectID() {
        String sql = "UPDATE maxids SET max_id = ? WHERE data_type = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, GlobalVariables.getLastProjectId() + 1);
            preparedStatement.setString(2, "project_id");

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates TagID to the database.
     * Better performances than {@link DataWriter#updateMaxIDs()}.
     */
    public void updateTagID() {
        String sql = "UPDATE maxids SET max_id = ? WHERE data_type = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, GlobalVariables.getLastTagId() + 1);
            preparedStatement.setString(2, "tag_id");

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates TaskID to the database.
     * Better performances than {@link DataWriter#updateMaxIDs()}.
     */
    public void updateTaskID() {
        String sql = "UPDATE maxids SET max_id = ? WHERE data_type = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, GlobalVariables.getLastTaskId() + 1);
            preparedStatement.setString(2, "task_id");

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates IntervalID to the database.
     * Better performances than {@link DataWriter#updateMaxIDs()}.
     */
    public void updateIntervalID() {
        String sql = "UPDATE maxids SET max_id = ? WHERE data_type = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, GlobalVariables.getLastTimeIntervalId() + 1);
            preparedStatement.setString(2, "interval_id");

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
