package timetracker.API;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import timetracker.data.Project;
import timetracker.data.Tag;
import timetracker.data.Task;
import timetracker.data.TimeInterval;


//TODO: Update Methoden hinzufügen
public class DataWriter {

    final private Connection connection;

    public DataWriter() {
        Connection connection = null;

        try{

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("DataWrite could not connect to the database.");
        }

        this.connection = connection;
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeTask(Task task) {

        // SQL INSERT statement
        String sql = "INSERT INTO tasks(task_id, name, project_id) VALUES(?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, task.getTaskID());
            preparedStatement.setString(2, task.getName());

            if (task.getProject() != null)
                preparedStatement.setInt(3, task.getProject().getProjectID());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Task added!");
            } else {
                System.out.println("Error adding the task");
            }

        } catch (SQLException e) {
            System.err.println("Error when connecting to the database!");
        }
    }

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

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Project added!");
            } else {
                System.out.println("Error adding the project");
            }

        } catch (SQLException e) {
            System.err.println("Error when connecting to the database!");
        }

    }

    public void updateProject(Project project) {

    }

    public void writeTag(Tag tag) {

        String sql = "INSERT INTO tags(tag_id, name, parent_id, color) VALUES(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, tag.getTagID());
            preparedStatement.setString(2, tag.getName());
            if (tag.getParent() != null)
                preparedStatement.setInt(3, tag.getParent().getTagID());
            preparedStatement.setString(4, tag.getColor().toString());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Tag added!");
            } else {
                System.out.println("Error adding the tag");
            }

        } catch (SQLException e) {
            System.err.println("Error when connecting to the database!");
            throw new RuntimeException(e);
        }

    }

    public void writeTimeIntervals(TimeInterval timeInterval) {
        // SQL INSERT statement
        String sql = "INSERT INTO projects(interval_id, taks_id, start_time, end_time) VALUES(?,?,?,?)";

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

        } catch (SQLException e) {
            System.err.println("Error when connecting to the database!");
            throw new RuntimeException(e);
        }
    }
}
