package timetracker.API;

import timetracker.data.Project;
import timetracker.data.Tag;
import timetracker.data.Task;
import timetracker.data.TimeInterval;

import java.sql.*;

/**
 * This class in an extension of the {@link DatabaseConnection}.
 * This class is responsible for removing data from the database.
 * The DataRemover connects to the database and removes the data.
 *
 * @author Marlon Roseneberg
 * @version 0.2
 */
public class DataRemover extends DatabaseConnection{


    /**
     * Creates a new DataRemover.
     * A DataRemover connects to the database.
     * Super constructor: {@link DatabaseConnection#DatabaseConnection()}
     */
    public DataRemover() {
        super();
    }

    // Project

    /**
     * Removes the given project from the database.
     *
     * @param project The project to be removed.
     * @return true if the project was removed successfully, false otherwise.
     */
    public boolean removeProject(Project project) {

        String sql = "DELETE FROM Projects WHERE project_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, project.getID());

            int rowRemoved = preparedStatement.executeUpdate();

            if (rowRemoved > 0) {
                System.out.println("Project removed!");
                return true;
            } else {
                System.out.println("Project could not be removed!");
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Tag

    /**
     * Removes the given tag from the database.
     *
     * @param tag The tag to be removed.
     * @return true if the tag was removed successfully, false otherwise.
     */
    public boolean removeTag(Tag tag) {

        String sql = "DELETE FROM Tags WHERE tag_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, tag.getID());

            int rowRemoved = preparedStatement.executeUpdate();

            if (rowRemoved > 0) {
                System.out.println("Tag removed!");
                return true;
            } else {
                System.out.println("Tag could not be removed!");
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Task

    /**
     * Removes the given task from the database.
     *
     * @param task The task to be removed.
     * @return true if the task was removed successfully, false otherwise.
     */
    public boolean removeTask(Task task) {

        String sql = "DELETE FROM Tasks WHERE task_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, task.getID());

            int rowRemoved = preparedStatement.executeUpdate();

            if (rowRemoved > 0) {
                System.out.println("Task removed!");
                return true;
            } else {
                System.out.println("Task could not be removed!");
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // TimeInterval

    /**
     * Removes the given time interval from the database.
     *
     * @param timeInterval The time interval to be removed.
     * @return true if the time interval was removed successfully, false otherwise.
     */
    public boolean removeTimeInterval(TimeInterval timeInterval) {

        String sql = "DELETE FROM TimeIntervals WHERE interval_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, timeInterval.getID());

            int rowRemoved = preparedStatement.executeUpdate();

            if (rowRemoved > 0) {
                System.out.println("Time interval removed!");
                return true;
            } else {
                System.out.println("Time interval could not be removed!");
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
