package timetracker.gui.task;

import timetracker.data.GlobalVariables;
import timetracker.data.Project;
import timetracker.data.Tag;
import timetracker.data.Task;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditTaskDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel inputPanel;
    private JPanel namePanel;
    private JTextField nameTextField;
    private JPanel projectPane;
    private JTextField projectTextField;
    private JPanel tagPane;
    private JTextField tagTextField;
    private Task task;


    public EditTaskDialog(Task task) {

        this.task = task;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        nameTextField.setText(task.getName());
        if (task.getProject() != null) {
            projectTextField.setText(task.getProject().getName());
        }
        tagTextField.setText(task.getTagsAsString());

        this.setSize(450, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        // Disable project field for now (not implemented yet)

        projectTextField.setEnabled(false);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        this.setVisible(true);
    }

    private void onOK() {
        // add your code here
        updateTask();
        if (task == null) dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void updateTask() {
        String name = nameTextField.getText();
        Project project = null;


        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid name of the Task.");
            return;
        }
        if (projectTextField.getText().isEmpty()) {
            if (task.getProject() != null) {
                task.getProject().removeTask(task);
            }
        } else {
            if (!GlobalVariables.NAME_TO_PROJECT_MAP.containsKey(projectTextField.getText())) {
                JOptionPane.showMessageDialog(this, "Please enter a valid project.");
                return;
            }
            project = GlobalVariables.NAME_TO_PROJECT_MAP.get(projectTextField.getText());
        }

        task.setName(name);
        if (project != null) {
            project.addTask(task);
        }

        List<Tag> taskTags = task.getTags();
        ArrayList<String> tags = new ArrayList<>(Arrays.asList(tagTextField.getText().split("#")));
        if (!tags.isEmpty()) tags.remove(0);
        tags.replaceAll(String::trim);

        // Remove taskTags that are not in the text field anymore
        for (Tag tag : taskTags){
            if (!tags.contains(tag.getName())){
                task.removeTag(tag);
            }
        }

        // Add taskTags that are in the text field but not in the task
        if (!tagTextField.getText().isEmpty()) {
            for (String tag : tags) {
                if (!GlobalVariables.NAME_TO_TAG_MAP.containsKey(tag)) {
                    Tag newTag = new Tag(GlobalVariables.getNextTagId(), tag, null, null);
                    newTag.addGlobal();
                    newTag.writeDatabase();
                    task.addTag(newTag);
                } else {
                    if (!taskTags.contains(GlobalVariables.NAME_TO_TAG_MAP.get(tag))) {
                        task.addTag(GlobalVariables.NAME_TO_TAG_MAP.get(tag));
                    }
                }
            }
        }
        task.updateDatabase();

        // End the dialog
        task = null;
    }
}
