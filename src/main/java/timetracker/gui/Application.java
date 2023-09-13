package timetracker.gui;

import timetracker.API.DataReader;
import timetracker.API.DataWriter;
import timetracker.data.GlobalVariables;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class Application implements Runnable{

    @Override
    public void run() {
        checkForDatabase();
        collect();
        MainForm mainForm = new MainForm();

        // Shutdown hook if the application is closed while a task is running
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            GlobalVariables.ID_TO_TASK_MAP.forEach((id, task) -> {
                if (task.isRunning()){
                    System.out.println("Shutting down task " + id);
                    task.stop();
                }
            });
        }));
    }

    private void checkForDatabase() {
        System.out.println("Checking for database...");
        if (!isDatabaseExisting()) {
            System.out.println("Database not found. Creating new database...");
            new File(getFilePath()).getParentFile().mkdirs();
            DataWriter dataWriter = new DataWriter();
            dataWriter.initDatabase();
            dataWriter.close();
        }
    }

    public boolean isDatabaseExisting() {
        File file = new File(getFilePath());
        return file.exists();
    }

    public String getFilePath() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return System.getProperty("user.home") + "\\AppData\\Roaming\\timeStamp\\database.sqlite";
        } else if (os.contains("mac")) {
            return System.getProperty("user.home") + "/Library/Application Support/timeStamp/database.sqlite";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return System.getProperty("user.home") + "/.config/timeStamp/database.sqlite";
        } else {
            throw new RuntimeException("Could not check for os.");
        }
    }

    private void collect() {
        DataReader dataReader = new DataReader();
        dataReader.readInAllToGlobal();
        dataReader.close();
    }

}
