package timetracker.gui;

import timetracker.API.DataReader;
import timetracker.data.GlobalVariables;

public class Application{

    public Application() {
        collect();
        MainForm mainForm = new MainForm();
    }

    private void collect() {
        DataReader dataReader = new DataReader();
        dataReader.readInAllToGlobal();
        dataReader.close();
    }

}
