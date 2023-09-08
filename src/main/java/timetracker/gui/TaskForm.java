package timetracker.gui;

import timetracker.data.GlobalVariables;
import timetracker.data.Task;

import javax.swing.*;

public class TaskForm extends JFrame {
    private JPanel panel1;
    private JList<Task> taskList;
    private JPanel jPane;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    DefaultListModel<Task> listModel = new DefaultListModel<>();

    public TaskForm() {
        super("TimeTracker");

        createTaskList();

        this.setContentPane(panel1);
        this.setMinimumSize(new java.awt.Dimension(500, 500));
        this.setSize(700, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void createTaskList() {
        taskList.setModel(listModel);
        GlobalVariables.ID_TO_TASK_MAP.forEach((k, v) -> listModel.addElement(v));
    }

}
