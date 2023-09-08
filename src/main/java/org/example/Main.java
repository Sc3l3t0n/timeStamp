package org.example;


import timetracker.console.CLI;
import timetracker.data.GlobalVariables;
import timetracker.data.Task;
import timetracker.gui.Application;
import timetracker.gui.MainForm;

/**
 * This class is the main class of the program.
 */
public class Main {
    public static void main(String[] args){

        // Create test task
        Task task1 = new Task(GlobalVariables.getNextTaskId(), "This is a test task",  null);
        task1.addGlobal();

        Application application = new Application();

        // CLI cli = new CLI();

    }
}