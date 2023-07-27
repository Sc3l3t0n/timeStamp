package timetracker.data;

import java.util.HashMap;

public class GlobalVariables {

    private static int PROJECT_ID = 0;

    private static int TAG_ID = 0;

    private static int TASK_ID = 0;

    private static int TIME_INTERVAL_ID = 0;

    public static final HashMap<Integer, Project> PROJECT_MAP = new HashMap<>();

    public static final HashMap<Integer, Tag> TAG_MAP = new HashMap<>();

    public static final HashMap<Integer, Task> TASK_MAP = new HashMap<>();

    public static final HashMap<Integer, TimeInterval> TIME_INTERVAL_MAP = new HashMap<>();

    public static int getLastProjectId() {
        return PROJECT_ID - 1;
    }

    public static int getNextProjectId() {

        return PROJECT_ID++;
    }

    public static int getLastTagId() {
        return TAG_ID - 1;
    }

    public static int getNextTagId() {
        return TAG_ID++;
    }


    public static int getLastTaskId() {
        return TASK_ID - 1;
    }

    public static int getNextTaskId() {
        return TASK_ID++;
    }

    public static int getLastTimeIntervalId() {
        return TIME_INTERVAL_ID - 1;
    }

    public static int getNextTimeIntervalId() {
        return TIME_INTERVAL_ID++;
    }
}
