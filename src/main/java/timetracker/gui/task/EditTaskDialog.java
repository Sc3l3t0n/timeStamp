package timetracker.gui.task;

import timetracker.data.GlobalVariables;
import timetracker.data.Project;
import timetracker.data.Tag;
import timetracker.data.Task;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
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
        tagTextField.setText(task.getTagsToString());

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


        List<Tag> tags = task.getTags();

        // Remove tags that are not in the text field anymore
        for (Tag tag : tags){
            if (!tagTextField.getText().contains(tag.getName())){
                task.removeTag(tag);
            }
        }



        // Add tags that are in the text field but not in the task
        if (!tagTextField.getText().isEmpty()) {
            for (String tag : tagTextField.getText().split(" ")) {
                if (!GlobalVariables.NAME_TO_TAG_MAP.containsKey(tag)) {
                    Tag newTag = new Tag(GlobalVariables.getNextTagId(), tag, null, null);
                    newTag.addGlobal();
                    newTag.writeDatabase();
                    task.addTag(newTag);
                } else {
                    if (!tags.contains(GlobalVariables.NAME_TO_TAG_MAP.get(tag))) {
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
