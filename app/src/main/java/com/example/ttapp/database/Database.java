package com.example.ttapp.database;

public class Database {

    private ConcreteDatabase database;
    private static Database instance;

    private Database() {

    }

    public static void setConcreteDatabase(ConcreteDatabase database) {
        assertInitialized();
        instance.database = database;
    }

    public static void initialize(ConcreteDatabase database) {
        if (instance == null) {
            instance = new Database();
        }
        instance.database = database;
    }

    public static Database getInstance() {
        assertInitialized();
        return instance;
    }

    public void getDeviceIdTask(String identifier, final Task task) {
        database.getDeviceIdTask(identifier, task);
    }

    private static void assertInitialized() {
        if (instance == null) {
            throw new IllegalStateException(Database.class.getSimpleName() + " is not initialized," +
                    "call initialize(...) first");
        }
    }
}
