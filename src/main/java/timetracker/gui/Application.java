package timetracker.gui;

import timetracker.API.DataReader;
import timetracker.data.GlobalVariables;

public class Application{

    public Application() {
        collect();
        MainForm mainForm = new MainForm();

        // End all running tasks when the program is closed
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            GlobalVariables.ID_TO_TASK_MAP.forEach((id, task) -> {
                if (task.isRunning()) {
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
