package timetracker.gui.task;

import timetracker.data.GlobalVariables;
import timetracker.data.Project;
import timetracker.data.Tag;
import timetracker.data.Task;

import javax.swing.*;
import java.awt.event.*;

/**
 * Dialog for creating a new task.
 * The user can enter a name, a project and tags.
 * The task is created when the user clicks the OK button.
 *
 */
public class CreateTaskDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;
    private JPanel namePanel;
    private JTextField projectTextField;
    private JPanel tagPane;
    private JPanel projectPane;
    private JPanel inputPanel;
    private JTextField tagTextField;

    private Task task;

    public CreateTaskDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.setSize(450, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setVisible(true);
    }

    private void onOK() {
        createTask();
        if (task != null) dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * Creates a task from the input fields.
     * If the input is invalid, a message dialog is shown.
     * If the input is valid, the task is created and the dialog is closed.
     */
    private void createTask() {
        String name = nameTextField.getText();
        Project project = null;
        String tag = tagTextField.getText();

        // Check if name and project are valid
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name for the task.");
            return;
        }
        if (!projectTextField.getText().isEmpty()) {
            if (!GlobalVariables.NAME_TO_PROJECT_MAP.containsKey(projectTextField.getText())) {
                JOptionPane.showMessageDialog(this, "Please enter a valid project.");
                return;
            }
            project = GlobalVariables.NAME_TO_PROJECT_MAP.get(projectTextField.getText());
        }

        task = new Task(GlobalVariables.getNextTaskId(), name, project);

        // Tags
        if (!tag.isEmpty()) {
            String[] tags = tag.split(" ");
            for (String t : tags) {
                if (GlobalVariables.NAME_TO_TAG_MAP.containsKey(t)) {
                    task.addTag(GlobalVariables.NAME_TO_TAG_MAP.get(t));
                } else {
                    Tag newTag = new Tag(GlobalVariables.getNextTagId(), t, null, null); //TODO: Add color functionality
                    newTag.addGlobal();
                    //newTag.writeDatabase(); TODO: Enable database writing
                    task.addTag(newTag);
                }
            }
        }
        task.addGlobal();
        // task.writeDatabase(); TODO: Enable database writing
    }
}
