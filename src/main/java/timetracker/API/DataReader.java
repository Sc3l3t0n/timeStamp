package timetracker.API;

import timetracker.data.*;

import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class in an extension of the {@link DatabaseConnection}.
 * This class is responsible for reading data from the database.
 * The DataReader connects to the database and reads the data.
 *
 * @author Marlon Rosenberg
 * @version 0.2
 */
public class DataReader extends DatabaseConnection {

    /**
     * Creates a new DataReader.
     * A DataReader connects to the database.
     * Super constructor: {@link DatabaseConnection#DatabaseConnection()}
     */
    public DataReader() {
        super();
    }

    // Projects

    /**
     * Reads all projects from the database.
     *
     * @return A list of all projects.
     * @throws RuntimeException if the DataReader could not read the projects from the database.
     */
    public List<Project> readAllProjects() {

        ArrayList<Project> projects = new ArrayList<>();
        String sql = "SELECT project_id, name, description, parent_id FROM Projects ORDER BY project_id";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int projectID = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                int parentID = resultSet.getInt(4);
                Project parent = parentID > -1 ? GlobalVariables.PROJECT_MAP.get(parentID) : null;

                projects.add(new Project(projectID, name, description, parent));
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return projects;
    }

    // Tags

    /**
     * Reads all tags from the database.
     *
     * @return A list of all tags.
     * @throws RuntimeException if the DataReader could not read the tags from the database.
     */
    public List<Tag> readAllTags() {

        ArrayList<Tag> tags = new ArrayList<>();
        String sql = "SELECT tag_id, name, parent_id, color FROM Tags ORDER BY tag_id";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int tagID = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int parentID = resultSet.getInt(3);
                Tag parent = parentID > -1 ? GlobalVariables.TAG_MAP.get(parentID) : null;
                Color color = Color.getColor(resultSet.getString(4));


                tags.add(new Tag(tagID, name, parent, color));
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tags;
    }

    // Tasks

    /**
     * Reads all tasks from the database.
     *
     * @return A list of all tasks.
     * @throws RuntimeException if the DataReader could not read the tasks from the database.
     */
    public List<Task> readAllTasks() {

        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "SELECT task_id, name, project_id FROM Tasks ORDER BY task_id";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int taskID = resultSet.getInt(1);
                String name = resultSet.getString(2);
                int projectID = resultSet.getInt(3);
                Project project = projectID > -1 ? GlobalVariables.PROJECT_MAP.get(projectID) : null;

                tasks.add(new Task(taskID, name, project));
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tasks;
    }

    // TimeIntervals

    /**
     * Reads all time intervals from the database.
     *
     * @return A list of all time intervals.
     * @throws RuntimeException if the DataReader could not read the time intervals from the database.
     */
    public List<TimeInterval> readAllTimeIntervals() {

        ArrayList<TimeInterval> timeIntervals = new ArrayList<>();
        String sql = "SELECT interval_id, task_id, start_time, end_time FROM TimeIntervals ORDER BY interval_id";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int intervalID = resultSet.getInt(1);
                int task_id = resultSet.getInt(2);
                Task task = task_id > -1 ? GlobalVariables.TASK_MAP.get(task_id) : null;
                LocalDateTime startTime = LocalDateTime.parse(resultSet.getString(3));
                LocalDateTime endTime = LocalDateTime.parse(resultSet.getString(4));

                TimeInterval timeInterval = new TimeInterval(intervalID, task);
                timeInterval.setStartTime(startTime);
                timeInterval.setEndTime(endTime);

                timeIntervals.add(timeInterval);
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return timeIntervals;
    }

    // MaxIDs

    /**
     * Reads the max IDs from the database.
     * The max IDs are the highest IDs of the projects, tags, tasks and time intervals.
     * The max IDs are used to set the ID in the GlobalVariables.
     *
     * @throws RuntimeException if the DataReader could not read the max IDs from the database.
     */
    public void readMaxIDs() {

        // interval_id, project_id, tag_id, task_id
        String sql = "SELECT data_type, max_id FROM MaxIDs ORDER BY data_type";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            // interval
            resultSet.next();
            GlobalVariables.setTimeIntervalId(resultSet.getInt(2));

            // project
            resultSet.next();
            GlobalVariables.setProjectId(resultSet.getInt(2));

            // tag
            resultSet.next();
            GlobalVariables.setTagId(resultSet.getInt(2));

            // task
            resultSet.next();
            GlobalVariables.setTaskId(resultSet.getInt(2));

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
