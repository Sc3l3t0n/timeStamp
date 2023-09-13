package timetracker.gui.task;

import timetracker.data.GlobalVariables;
import timetracker.data.Task;
import timetracker.data.TimeInterval;
import timetracker.gui.MainForm;

import javax.swing.*;
import java.util.ArrayList;

public class TaskForm extends JFrame {
    private JPanel panel1;
    private JList<Task> taskList;
    private JPanel taskViewWrap;
    private JButton createButton;
    private JButton deleteButton;
    private JButton backButton;
    private JLabel taskName;
    private JPanel taskView;
    private JList<TimeInterval> timeIntervalsList;
    private JLabel projectLabel;
    private JLabel tagsLabel;
    private JLabel durationLabel;
    private JLabel timeIntervalsLabel;
    private JScrollPane timeIntervalsScrollPane;
    private JButton startButton;
    private JButton stopButton;
    private JLabel stoppwatchLabel;
    private JPanel stoppwatchPanel;
    private JButton deleteIntervalButton;
    private JButton editButton;
    private JButton editIntervalButton;
    private JButton createIntervalButton;

    DefaultListModel<Task> listModel = new DefaultListModel<>();
    DefaultListModel<TimeInterval> timeIntervalsListModel = new DefaultListModel<>();
    private StopwatchController stoppWatchController;

    public TaskForm() {
        super("TimeTracker");

        taskList.setModel(listModel);
        timeIntervalsList.setModel(timeIntervalsListModel);

        updateTaskList();
        addListener();

        taskView.setVisible(false);

        // Disable buttons
        deleteButton.setEnabled(false);
        editIntervalButton.setEnabled(false);
        deleteIntervalButton.setEnabled(false);


        this.setContentPane(panel1);
        this.setMinimumSize(new java.awt.Dimension(800, 600));
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void updateTaskList() {
        listModel.clear();
        GlobalVariables.ID_TO_TASK_MAP.forEach((k, v) -> listModel.addElement(v));
    }

    private void updateTimeIntervalList() {
        timeIntervalsListModel.clear();
        Task task = taskList.getSelectedValue();
        if (task != null) {
            task.getTimeIntervals().forEach(timeInterval -> {
                        if (timeInterval.getEndTime() != null) {
                            timeIntervalsListModel.addElement(timeInterval);
                        }
                    }
            );
        }
    }

    private void addListener() {

        // Exit listener
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ArrayList<Task> runningTasks = new ArrayList<>();
                GlobalVariables.ID_TO_TASK_MAP.forEach((id, task) -> {
                    if (task.isRunning()) {
                        runningTasks.add(task);
                    }
                });

                if (!runningTasks.isEmpty()) {
                    int dialogResult = JOptionPane.showConfirmDialog(
                            null,
                            "There are still running tasks. Are you sure you want to exit? \r\n All running tasks will be stopped.",
                            "Warning",
                            JOptionPane.YES_NO_OPTION);
                    if (dialogResult != JOptionPane.YES_OPTION) {
                        return;
                    } else {
                        runningTasks.forEach(Task::stop);
                    }
                }

                System.exit(0);
            }
        });

        // List selection listener
        taskList.getSelectionModel().addListSelectionListener(e -> {
            Task task = taskList.getSelectedValue();
            if (task != null) {
                updateTaskView(task);
                deleteButton.setEnabled(true);
            }
        });

        // Time interval list selection listener
        timeIntervalsList.getSelectionModel().addListSelectionListener(e -> {
            TimeInterval timeInterval = timeIntervalsList.getSelectedValue();
            if (timeInterval != null) {
                editIntervalButton.setEnabled(true);
                deleteIntervalButton.setEnabled(true);
            }
        });

        // Create button listener
        createButton.addActionListener(e -> {
            CreateTaskDialog createTaskDialog = new CreateTaskDialog();
            createTaskDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    listModel.add(listModel.getSize(), createTaskDialog.getTask());
                    taskList.setSelectedIndex(listModel.getSize() - 1);
                    updateTaskView(createTaskDialog.getTask());
                }
            });
        });

        // Delete button listener
        deleteButton.addActionListener(e -> {
            Task task = taskList.getSelectedValue();
            if (task != null) {
                // Confirm delete
                int dialogResult = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this task?",
                        "Warning",
                        JOptionPane.YES_NO_OPTION);
                if (dialogResult != JOptionPane.YES_OPTION) {
                    return;
                }
                task.remove();
                task.removeDatabase();
                listModel.removeElement(task);
                taskView.setVisible(false);
                deleteButton.setEnabled(false);
            }
        });

        // Back button listener
        backButton.addActionListener(e -> {
            new MainForm();
            this.dispose();
        });

        // Start button listener
        startButton.addActionListener(e -> {
            Task task = taskList.getSelectedValue();
            if (task != null) {
                task.start();
                stoppWatchController = new StopwatchController(task, stoppwatchLabel);
                stoppWatchController.start();
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
            }
        });

        // Stop button listener
        stopButton.addActionListener(e -> {
            Task task = taskList.getSelectedValue();
            if (task != null) {
                task.stop();
                stoppWatchController.end();
                updateTimeIntervalList();
                durationLabel.setText("Duration: " + String.format("%d:%02d:%02d", task.getDuration().getSeconds() / 3600, (task.getDuration().getSeconds() % 3600) / 60, (task.getDuration().getSeconds() % 60)));
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
            }
        });

        // Delete interval button listener
        deleteIntervalButton.addActionListener(e -> {
            TimeInterval timeInterval = timeIntervalsList.getSelectedValue();
            if (timeInterval != null) {
                // Confirm delete
                int dialogResult = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to delete this time interval?",
                        "Warning",
                        JOptionPane.YES_NO_OPTION);
                if (dialogResult != JOptionPane.YES_OPTION) {
                    return;
                }
                timeInterval.remove();
                updateTimeIntervalList();

                // Disable buttons
                editIntervalButton.setEnabled(false);
                deleteIntervalButton.setEnabled(false);
            }
        });

        // Edit interval button listener
        editIntervalButton.addActionListener(e -> {
            TimeInterval timeInterval = timeIntervalsList.getSelectedValue();
            if (timeInterval != null) {

                EditTimeIntervalDialog editTimeIntervalDialog = new EditTimeIntervalDialog(timeInterval);
                editTimeIntervalDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        updateTimeIntervalList();
                        updateTaskView(taskList.getSelectedValue());
                    }
                });
            }
        });

        // Create interval button listener
        createIntervalButton.addActionListener(e -> {
            Task task = taskList.getSelectedValue();
            if (task != null) {
                CreateTimeIntervalDialog createTimeIntervalDialog = new CreateTimeIntervalDialog(task);
                createTimeIntervalDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        updateTimeIntervalList();
                        updateTaskView(task);
                    }
                });
            }
        });

        // Edit button listener
        editButton.addActionListener(e -> {
            Task task = taskList.getSelectedValue();
            if (task != null) {
                EditTaskDialog editTaskDialog = new EditTaskDialog(task);
                editTaskDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        updateTaskView(task);
                    }
                });
            }
        });
    }

    private void updateTaskView(Task task) {
        long duration = task.getDuration().getSeconds();
        taskName.setText(task.getName());
        projectLabel.setText("Project: " + (task.getProject() == null ? "None" : task.getProject().getName()));
        tagsLabel.setText("Tags: " + task.getTagsAsString());
        durationLabel.setText("Duration: " + String.format("%d:%02d:%02d", duration / 3600, (duration % 3600) / 60, (duration % 60)));
        timeIntervalsListModel.clear();
        stoppwatchLabel.setText("0:00:00");
        updateTimeIntervalList();

        // Stop stopwatch if it is running
        if (stoppWatchController != null && stoppWatchController.isAlive()) {
            stoppWatchController.end();
        }
        stoppWatchController = new StopwatchController(task, stoppwatchLabel);

        // Enable/disable buttons
        if (task.isRunning()) {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            stoppWatchController.start();
        } else {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }

        taskView.setVisible(true);
    }

}
