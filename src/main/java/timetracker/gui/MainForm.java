package timetracker.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame{
    private JPanel panel1;
    private JButton jbTasks;
    private JButton jbProjects;
    private JButton jbTags;
    private JLabel lTitel;

    public MainForm() {
        super("TimeTracker");
        this.setContentPane(panel1);
        this.setMinimumSize(new Dimension(500,500));
        this.setSize(700, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
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

        this.setVisible(true);
    }

    private void openTaskForm() {
        TaskForm taskForm = new TaskForm();
        taskForm.setVisible(true);
    }


}
