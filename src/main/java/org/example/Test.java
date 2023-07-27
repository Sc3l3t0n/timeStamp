package org.example;

import timetracker.API.DataWriter;
import timetracker.data.*;

import java.awt.*;
import java.time.*;
import java.util.ArrayList;

public class Test {

    public static void test() {

        Project project = new Project(GlobalVariables.getNextProjectId(), "Update", "An Example one", null);

        Task exampleTask = new Task(GlobalVariables.getNextTaskId(), "Update", project);

        TimeInterval timeInterval = new TimeInterval(GlobalVariables.getNextTimeIntervalId(), exampleTask);

        Tag tag = new Tag(GlobalVariables.getNextTagId(), "Update", null, Color.BLUE);

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
