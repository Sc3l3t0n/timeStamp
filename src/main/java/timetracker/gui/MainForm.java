package timetracker.gui;

import timetracker.data.GlobalVariables;
import timetracker.data.Task;
import timetracker.gui.task.TaskForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainForm extends JFrame{
    private JPanel panel1;
    private JButton jbTasks;
    private JButton jbProjects;
    private JButton jbTags;
    private JLabel lTitel;

    public MainForm() {
        super("timeStamp");
        this.setContentPane(panel1);
        this.setMinimumSize(new Dimension(500,500));
        this.setSize(700, 1000);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        setLocationRelativeTo(null);

        addListener();

        this.setVisible(true);
    }

    private void openTaskForm() {
        TaskForm taskForm = new TaskForm();
        this.dispose();
    }

    private void addListener() {
        // Exit listener
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                boolean running = false;
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

        // Task button listener
        jbTasks.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                openTaskForm();
            }
        });
    }


}
