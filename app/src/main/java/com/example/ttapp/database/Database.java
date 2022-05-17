package com.example.ttapp.database;

public class Database implements IDatabase {

    private IDatabase database;
    private static Database instance;

    private Database() {

    }

    public static void setConcreteDatabase(IDatabase database) {
        if (instance == null) {
            instance = new Database();
        }
        instance.database = database;
    }

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
            throw new IllegalStateException(Database.class.getSimpleName() + " has no concrete database," +
                    "set database with setConcreteDatabase(...) first");
        }
    }
}
