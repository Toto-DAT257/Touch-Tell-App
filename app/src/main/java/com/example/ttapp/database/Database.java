package com.example.ttapp.database;

/**
 * This is the main interface for this package. This class provides no functionality by itself.
 * It should be supplied with an {@link IDatabase} that implements the wanted functionality.
 * <p>
 * How to use:
 * <ol>
 *    <li>Supply the instance by calling setConcreteDatabase and passing an {@link IDatabase}</li>
 *    <li>Call getInstance() and then call any further desired methods on the instance.</li>
 * </ol>
 */
public class Database implements IDatabase {

    private IDatabase database;
    private static Database instance;

    private Database() { }

    /**
     * Sets the this class' {@link IDatabase} object to the supplied database. It is the supplied object
     * which provides functionality to this class.
     *
     * @param database Object which implements desired behaviour.
     */
    public static void setConcreteDatabase(IDatabase database) {
        if (instance == null) {
            instance = new Database();
        }
        instance.database = database;
    }

    /**
     * Gets the singleton instance.
     * @return the singleton instance.
     */
    public static Database getInstance() {
        assertInitialized();
        return instance;
    }

    @Override
    public void getDeviceIdTask(String identifier, final Task task) {
        database.getDeviceIdTask(identifier, task);
    }

    private static void assertInitialized() {
        if (instance == null) {
            throw new IllegalStateException(Database.class.getSimpleName() + " has no concrete database, " +
                    "set database with setConcreteDatabase(...) first");
        }
    }
}
