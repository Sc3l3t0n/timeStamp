package timetracker.gui;

import timetracker.API.DataReader;
import timetracker.data.GlobalVariables;

public class Application{

    public Application() {
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

    private void collect() {
        DataReader dataReader = new DataReader();
        dataReader.readInAllToGlobal();
        dataReader.close();
    }

}
