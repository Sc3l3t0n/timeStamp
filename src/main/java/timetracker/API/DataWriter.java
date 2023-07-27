package timetracker.API;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import timetracker.data.*;

public class DataWriter {

    final private Connection connection;

    public DataWriter() {
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("DataWrite could not connect to the database!");
        }
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Task

    public void writeTask(Task task) {

        // SQL INSERT statement
        String sql = "INSERT INTO tasks(task_id, name, project_id) VALUES(?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, task.getTaskID());
            preparedStatement.setString(2, task.getName());

            if (task.getProject() != null)
                preparedStatement.setInt(3, task.getProject().getProjectID());
            else
                preparedStatement.setInt(3, -1);


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
    }

    public void updateTask(Task task) {

        // SQL INSERT statement
        String sql = "UPDATE tasks SET name = ?, project_id = ? WHERE task_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, task.getName());
            if (task.getProject() != null)
                preparedStatement.setInt(2, task.getProject().getProjectID());
            else
                preparedStatement.setInt(2, -1);

            preparedStatement.setInt(3, task.getTaskID());

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
    }

    // Project

    public void writeProject(Project project) {

        // SQL INSERT statement
        String sql = "INSERT INTO projects(project_id, name, description, parent_id) VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, project.getProjectID());
            preparedStatement.setString(2, project.getName());
            preparedStatement.setString(3, project.getDescription());
            if (project.getParent() != null)
                preparedStatement.setInt(4, project.getParent().getProjectID());
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
    }

    public void updateProject(Project project) {

        // SQL INSERT statement
        String sql = "UPDATE projects SET name = ?, description = ?, parent_id = ? WHERE project_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getDescription());
            if (project.getParent() != null)
                preparedStatement.setInt(3, project.getParent().getProjectID());
            else
                preparedStatement.setInt(3, -1);


            preparedStatement.setInt(4, project.getProjectID());

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
    }

    // Tag

    public void writeTag(Tag tag) {

        // SQL INSERT statement
        String sql = "INSERT INTO tags(tag_id, name, parent_id, color) VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, tag.getTagID());
            preparedStatement.setString(2, tag.getName());
            if (tag.getParent() != null)
                preparedStatement.setInt(3, tag.getParent().getTagID());
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

    }

    public void updateTag(Tag tag) {

        // SQL INSERT statement
        String sql = "UPDATE tags SET name = ?, parent_id = ?, color = ? WHERE tag_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, tag.getName());
            // check if tag has a parent
            if (tag.getParent() != null)
                preparedStatement.setInt(2, tag.getParent().getTagID());
            else
                preparedStatement.setInt(2, -1);

            preparedStatement.setString(3, tag.getColor().toString());

            preparedStatement.setInt(4, tag.getTagID());

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

    }

    // TimeIntervals

    public void writeTimeIntervals(TimeInterval timeInterval) {

        // SQL INSERT statement
        String sql = "INSERT INTO timeintervals(interval_id, task_id, start_time, end_time) VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, timeInterval.getIntervalID());
            preparedStatement.setInt(2, timeInterval.getTask().getTaskID());
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
    }

    public void updateTimeIntervals(TimeInterval timeInterval) {

        // SQL INSERT statement
        String sql = "UPDATE timeintervals SET task_id =?, start_time = ?, end_time = ? WHERE interval_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, timeInterval.getTask().getTaskID());
            preparedStatement.setString(2, timeInterval.getStartTime().toString());
            preparedStatement.setString(3, timeInterval.getEndTime().toString());
            preparedStatement.setInt(4, timeInterval.getIntervalID());

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
    }

    // MaxIDs

    public void updateMaxIDs() {

        // SQL INSERT statement
        String sql = "UPDATE maxids SET max_id = ? WHERE data_type = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Project_ID
            preparedStatement.setInt(1, GlobalVariables.getLastProjectId());
            preparedStatement.setString(2, "project_id");

            preparedStatement.executeUpdate();

            // Tag_ID
            preparedStatement.setInt(1, GlobalVariables.getLastTagId());
            preparedStatement.setString(2, "tag_id");

            preparedStatement.executeUpdate();

            // Task_ID
            preparedStatement.setInt(1, GlobalVariables.getLastTaskId());
            preparedStatement.setString(2, "task_id");

            preparedStatement.executeUpdate();

            // Interval_ID
            preparedStatement.setInt(1, GlobalVariables.getLastTimeIntervalId());
            preparedStatement.setString(2, "interval_id");

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
