package timetracker.data;

import timetracker.API.*;

/**
 * This class represents a data type.
 * A data type has a unique ID.
 * A data type can be added to the global variables, removed from the global variables and updated in the global variables.
 * A data type can be written to the database, updated in the database and removed from the database.
 *
 * @author Marlon Rosenberg
 * @version 0.1
 */
public abstract class DataType {

    /**
     * The unique ID of the data type.
     */
    private final int id;

    /**
     * Creates a new data type with the given ID.
     *
     * @param id The unique ID of the data type.
     */
    DataType(int id) {
        this.id = id;
    }

    /**
     * Removes the data type from the global variables but not from the database.
     * Removes every reference to the data type.
     * See {@link #removeGlobal()}.
     */
    abstract void remove();

    // Global

    /**
     * Adds the data type to the global variables.
     * See {@link GlobalVariables}.
     */
    abstract void addGlobal();

    /**
     * Removes the data type from the global variables.
     * See {@link GlobalVariables}.
     */
    abstract void removeGlobal();

    // Database

    /**
     * Writes the data type to the database.
     * See {@link DataWriter}.
     */
    abstract void writeDatabase();

    /**
     * Updates the data type in the database.
     * See {@link DataWriter}.
     */
    abstract void updateDatabase();

    /**
     * Removes the data type from the database.
     * See {@link DataRemover}.
     */
    abstract void removeDatabase();

    // Getter and Setter

    /**
     * Returns the unique ID of the data type.
     *
     * @return The unique ID of the data type.
     */
    public int getID() {
        return id;
    }

    // Override

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataType dataType = (DataType) o;
        return this.id == dataType.getID();
    }

    @Override
    abstract public String toString();

}
