package timetracker.console;

import timetracker.API.DataReader;
import timetracker.API.DataRemover;
import timetracker.API.DataWriter;
import timetracker.data.GlobalVariables;
import timetracker.data.Task;
import timetracker.data.TimeInterval;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is the main class of the cli prototype.
 * It is used to run the program.
 *
 * @author Marlon Rosenberg
 * @version 0.1
 */
public class CLI {

    // Attributes

    /**
     * The active task.
     */
    private Task activeTask = null;

    /**
     * The active time interval.
     */
    private TimeInterval activeTimeInterval = null;

    /**
     * The scanner used to read user input.
     */
    private final Scanner scanner = new Scanner(System.in);;

    /**
     * The current status of the program.
     */
    private Status status = Status.START;

    // API

    /**
     * The data reader.
     */
    private final DataReader dataReader = new DataReader();
    /**
     * The data writer.
     */
    private final DataWriter dataWriter = new DataWriter();
    /**
     * The data remover.
     */
    private final DataRemover dataRemover = new DataRemover();

    /**
     * The constructor of the CLI class.
     * It starts the program.
     */
    public CLI() {
        start();
        while (status != Status.STOP) {
            mainMenu();

            switch (status) {
                case PROJECT_MENU -> projectMenu();
                case TASK_MENU -> taskMenu();
                case TAG_MENU -> tagMenu();
                case STOP -> this.status = Status.STOP;
                default -> status = Status.MAIN_MENU;
            };

        }
        stop();
    }

    // Methods

    /**
     * This method is used when the program is started.
     * It reads all data from the files.
     */
    public void start() {
        dataReader.readAllProjects();
        dataReader.readAllTasks();
        dataReader.readAllTags();
        dataReader.readAllTimeIntervals();
        dataReader.readMaxIDs();
    }

    /**
     * This method is used when the program is stopped.
     * It closes the scanner.
     */
    public void stop() {
        this.scanner.close();
    }

    /**
     * This method simulates the main menu.
     * It is used to select the next menu.
     */
    private void mainMenu(){
        clearConsole();

        clearConsole();

        System.out.println("Welcome to TimeTracker!");
        System.out.println("This is a prototype.");
        System.out.println("Please select an option:");
        System.out.println("1. Projects (not working)");
        System.out.println("2. Tasks");
        System.out.println("3. Tags (not working)");
        System.out.println("0. Exit");

        System.out.print("Your input: ");
        int input = this.scanner.nextInt();

        switch (input) {
            case 1 -> {
                //System.out.println("You selected Projects.");
                System.out.println("Projects are not implemented yet.");
                //this.status = Status.PROJECT_MENU;
                this.status = Status.MAIN_MENU;
            }
            case 2 -> {
                System.out.println("You selected Tasks.");
                this.status = Status.TASK_MENU;
            }
            case 3 -> {
                //System.out.println("You selected Tags.");
                System.out.println("Tags are not implemented yet.");
                //this.status = Status.TAG_MENU;
                this.status = Status.MAIN_MENU;
            }
            case 0 -> {
                System.out.println("You selected Exit.");
                this.status = Status.STOP;
            }
            default -> {
                System.out.println("Invalid input!");
                this.status = Status.MAIN_MENU;
            }
        }

    }

    // Project

    private void projectMenu() {

    }

    // Task

    /**
     * This method simulates the task menu.
     * It used to check if there is an active task.
     */
    private void taskMenu() {

        if (this.activeTask == null) {
            taskMenuNoActiveTask();
        } else {
            taskMenuActiveTask();
        }

    }

    /**
     * This method simulates the task menu when there is an active task.
     * It is used to select the next menu.
     */
    private void taskMenuActiveTask() {
        clearConsole();

        int input = 0;

        System.out.println("This is the Task Menu.");
        System.out.println("Please select an option:");
        System.out.println("1. Stop active task");
        System.out.println("2. List all tasks");
        System.out.println("0. Exit");

        System.out.print("Your input: ");

        try {
            input = this.scanner.nextInt();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }

        switch (input) {
            case 0 -> {
                System.out.println("You selected Exit.");
                this.status = Status.MAIN_MENU;
            }
            case 1 -> {
                System.out.println("You selected stop active task.");
                stopTask();
            }
            case 2 -> {
                System.out.println("You selected List all tasks.");
                listTasks();
            }
            default -> {
                System.out.println("Invalid input!");
                return;
            }
        }
    }

    /**
     * This method stops the active task and time interval.
     */
    private void stopTask() {
        clearConsole();

        this.activeTimeInterval.stop();
        dataWriter.writeTimeInterval(this.activeTimeInterval);
        dataWriter.updateIntervalID();

        System.out.println("Stopping active task.");
        System.out.println("Task: " + this.activeTask.getName());
        if (this.activeTask.getTimeIntervals().size() == 1) {
            System.out.println("Start: " + this.activeTimeInterval.getStartTime());
            System.out.println("End: " + this.activeTimeInterval.getEndTime());
        } else {
            AtomicInteger i = new AtomicInteger(1);
            this.activeTask.getTimeIntervals().forEach(timeInterval -> {
                System.out.println("--------------------");
                System.out.println("Interval " + i + ":");
                i.getAndIncrement();
                System.out.println("Start: " + timeInterval.getStartTime());
                System.out.println("End: " + timeInterval.getEndTime());
                System.out.println("Duration: " + timeInterval.getDuration().toDays() + " days, " +
                        timeInterval.getDuration().toHours() + " hours, " +
                        timeInterval.getDuration().toMinutes() + " minutes, " +
                        (int) timeInterval.getDuration().getSeconds() + " seconds.");
            });
            System.out.println("--------------------");
        }

        System.out.println("Duration: " + this.activeTask.getDuration().toDays() + " days, " +
                this.activeTask.getDuration().toHours() + " hours, " +
                this.activeTask.getDuration().toMinutes() + " minutes, " +
                (int) this.activeTask.getDuration().getSeconds() + " seconds.");

        try {
            System.out.println("Press Enter to continue.");
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }


        this.activeTimeInterval = null;
        this.activeTask = null;
    }

    /**
     * This method simulates the task menu when there is no active task.
     * It is used to select the next menu.
     */
    private void taskMenuNoActiveTask() {
        clearConsole();

        int input = 0;

        System.out.println("This is the Task Menu.");
        System.out.println("Please select an option:");
        System.out.println("1. Start a new task");
        System.out.println("2. Restart an existing task");
        System.out.println("3. List all tasks");
        System.out.println("0. Exit");


        System.out.print("Your input: ");

        try {
            input = this.scanner.nextInt();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }

        switch (input) {
            case 0 -> {
                System.out.println("You selected Exit.");
                this.status = Status.MAIN_MENU;
            }
            case 1 -> {
                System.out.println("You selected Create a new task.");
                createTask();
            }
            case 2 -> {
                System.out.println("You selected Restart an existing task.");
                restartTask();
            }
            case 3 -> {
                System.out.println("You selected List all tasks.");
                listTasks();
            }
            default -> {
                System.out.println("Invalid input!");
                return;
            }
        }
    }

    /**
     * This method creates a new task and start a new time interval.
     */
    private void createTask() {
        clearConsole();

        if (activeTask != null) {
            System.out.println("There is already an active task.");
            return;
        }

        System.out.println("Please enter the name of the task:");
        String name = this.scanner.next();
        System.out.println("Task started.");

        this.activeTask = new Task(GlobalVariables.getNextTaskId(), name, null);
        dataWriter.writeTask(this.activeTask);
        dataWriter.updateTaskID();

        this.activeTimeInterval = new TimeInterval(GlobalVariables.getNextTimeIntervalId(), this.activeTask);
        this.activeTimeInterval.start();

    }

    /**
     * This method restarts an existing task and start a new time interval.
     */
    private void restartTask() {
        clearConsole();

        System.out.println("Please enter the name of the task:");
        String name = this.scanner.next();
        System.out.println("Task restarted.");

        if (GlobalVariables.NAME_TO_TASK_MAP.containsKey(name)) {
            this.activeTask = GlobalVariables.NAME_TO_TASK_MAP.get(name);
        } else {
            System.out.println("Task not found.");
            restartTask();
            return;
        }

        this.activeTimeInterval = new TimeInterval(GlobalVariables.getNextTimeIntervalId(), this.activeTask);
        this.activeTimeInterval.start();
    }

    private void updateTask() {

    }

    private void deleteTask() {

    }

    /**
     * This method lists all tasks.
     */
    private void listTasks() {
        clearConsole();

        int input = 0;
        System.out.println("Listing all tasks:");
        GlobalVariables.ID_TO_TASK_MAP.forEach((id, task) -> {
            System.out.println("ID: " + id);
            System.out.println("Name: " + task.getName());
            System.out.println("Duration: " + task.getDuration().toDays() + " days, " +
                    task.getDuration().toHours() + " hours, " +
                    task.getDuration().toMinutes() + " minutes, " +
                    (int) task.getDuration().getSeconds() + " seconds.");
            System.out.println("--------------------");
        });
        System.out.println("Would you like to restart one of the tasks? (1 = yes, 0 = no)");
        input = this.scanner.nextInt();
        if (input == 1) {
            System.out.println("Please enter the ID of the task you would like to restart:");
            input = this.scanner.nextInt();
            if (GlobalVariables.ID_TO_TASK_MAP.containsKey(input)) {
                this.activeTask = GlobalVariables.ID_TO_TASK_MAP.get(input);
                this.activeTimeInterval = new TimeInterval(GlobalVariables.getNextTimeIntervalId(), this.activeTask);
                this.activeTimeInterval.start();
            } else {
                System.out.println("Task not found.");
                listTasks();
                return;
            }
        }
    }

    // Tag

    private void tagMenu() {

    }

    // Utility

    /**
     * This method clears the console.
     */
    public static void clearConsole()
    {
        try
        {
            final String os = System.getProperty("os.name");

            System.out.println(os);

            if (os.contains("Windows"))
            {
                System.out.println(os);
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }
}

