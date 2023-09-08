package timetracker.gui.task;

import timetracker.data.GlobalVariables;
import timetracker.data.Task;
import timetracker.gui.MainForm;

import javax.swing.*;

public class TaskForm extends JFrame {
    private JPanel panel1;
    private JList<Task> taskList;
    private JPanel taskView;
    private JButton createButton;
    private JButton deleteButton;
    private JButton backButton;
    private JLabel taskName;

    DefaultListModel<Task> listModel = new DefaultListModel<>();

    public TaskForm() {
        super("TimeTracker");

        createTaskList();
        addListener();

        this.setContentPane(panel1);
        this.setMinimumSize(new java.awt.Dimension(500, 500));
        this.setSize(700, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void createTaskList() {
        taskList.setModel(listModel);
        GlobalVariables.ID_TO_TASK_MAP.forEach((k, v) -> listModel.addElement(v));
    }

    private void updateTaskList() {
        listModel.clear();
        GlobalVariables.ID_TO_TASK_MAP.forEach((k, v) -> listModel.addElement(v));
    }

    private void addListener() {
        // List selection listener
        taskList.getSelectionModel().addListSelectionListener(e -> {
            Task task = taskList.getSelectedValue();
            if (task != null) {
                taskName.setText(task.getName());
            }
        });

        // Create button listener
        createButton.addActionListener(e -> {
            CreateTaskDialog createTaskDialog = new CreateTaskDialog();
            createTaskDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    updateTaskList();
                }
            });
        });

        // Delete button listener
        deleteButton.addActionListener(e -> {
            Task task = taskList.getSelectedValue();
            if (task != null) {
                task.remove();
                listModel.removeElement(task);
            }
        });

        // Back button listener
        backButton.addActionListener(e -> {
            new MainForm();
            this.dispose();
        });
    }

}
