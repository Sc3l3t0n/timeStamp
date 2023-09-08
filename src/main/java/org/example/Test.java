package org.example;

import timetracker.API.DataReader;
import timetracker.API.DataRemover;
import timetracker.API.DataWriter;
import timetracker.data.*;

import java.awt.*;
import java.time.*;

/**
 * This class is used to test the API.
 * It is not used in the final product.
 */
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

        dataWriter.writeTask(exampleTask);
        dataWriter.writeProject(project);
        dataWriter.writeTimeInterval(timeInterval);
        dataWriter.writeTag(tag);

        //dataWriter.updateTask(exampleTask);
        //dataWriter.updateProject(project);
        //dataWriter.updateTimeIntervals(timeInterval);
        //dataWriter.updateTag(tag);

        DataReader dataReader = new DataReader();

        dataReader.readAllProjects().forEach(System.out::println);
        dataReader.readAllTags().forEach(System.out::println);
        dataReader.readAllTasks().forEach(System.out::println);
        dataReader.readAllTimeIntervals().forEach(System.out::println);

        DataRemover dataRemover = new DataRemover();

        dataRemover.removeProject(project);
        dataRemover.removeTag(tag);
        dataRemover.removeTask(exampleTask);
        dataRemover.removeTimeInterval(timeInterval);

        dataReader.readAllProjects().forEach(System.out::println);
        dataReader.readAllTags().forEach(System.out::println);
        dataReader.readAllTasks().forEach(System.out::println);
        dataReader.readAllTimeIntervals().forEach(System.out::println);

    }

}
