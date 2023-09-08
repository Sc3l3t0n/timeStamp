package timetracker.gui;

import timetracker.API.DataReader;

public class Application{

    public Application() {
        collect();
        MainForm mainForm = new MainForm();
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                System.out.println("Shutdown hook ran!"); //TODO: Implement shutdown hook
            }
        });
    }

    private void collect() {
        DataReader dataReader = new DataReader();
        dataReader.readInAllToGlobal();
        dataReader.close();
    }

}
