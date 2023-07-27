package org.example;

import timetracker.API.DataWriter;
import timetracker.data.*;

import java.awt.*;
import java.time.*;
import java.util.ArrayList;

public class Test {

    public static void test() {

        GlobalVariables.PROJECT_ID = 1;
        Project project = new Project("Update", "An Example one", null, new ArrayList<>());

        GlobalVariables.TAG_ID = 1;
        Task exampleTask = new Task("Update", project, new ArrayList<>(), new ArrayList<>());

        TimeInterval timeInterval = new TimeInterval(exampleTask);

        Tag tag = new Tag("Update", null, Color.BLUE);

        timeInterval.start();
        timeInterval.stop();

        timeInterval.setStartTime(LocalDateTime.parse("1990-07-27T22:36:14.959956900"));

        DataWriter dataWriter = new DataWriter();

        // dataWriter.writeTask(exampleTask);
        // dataWriter.writeProject(project);
        // dataWriter.writeTimeIntervals(timeInterval);
        // dataWriter.writeTag(tag);

        dataWriter.updateTask(exampleTask);
        dataWriter.updateProject(project);
        dataWriter.updateTimeIntervals(timeInterval);
        dataWriter.updateTag(tag);
    }

}
