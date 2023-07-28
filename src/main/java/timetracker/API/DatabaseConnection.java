package timetracker.API;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is responsible for connecting to the database.
 * The DatabaseConnection connects to the database.
 *
 * @author Marlon Rosenberg
 * @version 0.1
 */
public abstract class DatabaseConnection {

    /**
     * The connection to the database.
     */
    final protected Connection connection;

    /**
     * Creates a new DatabaseConnection.
     * The DatabaseConnection connects to the database.
     *
     * @throws RuntimeException if the DatabaseConnection could not connect to the database.
     */
    public DatabaseConnection() {

        try{

            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:database.sqlite");

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(this.toString() + " could not connect to the database!");
        }
    }

    /**
     * Closes the connection to the database.
     *
     * @throws RuntimeException if the DataConnection could not close the connection to the database.
     */
    protected void close(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
