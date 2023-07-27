package org.example;
import timetracker.API.DataWriter;
import timetracker.data.GlobalVariables;
import timetracker.data.Project;
import timetracker.data.Task;

import java.time.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        GlobalVariables.PROJECT_ID = 1;
        Project project = new Project("Example", "An Example one", null, new ArrayList<>());

        GlobalVariables.TAG_ID = 1;
        Task exampleTask = new Task("Example", project, new ArrayList<>(), new ArrayList<>());

        DataWriter dataWriter = new DataWriter();

        dataWriter.writeTask(exampleTask);
        dataWriter.writeProject(project);

    }
}