package timetracker.gui.task;

import timetracker.data.GlobalVariables;
import timetracker.data.Task;
import timetracker.data.TimeInterval;

import javax.swing.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CreateTimeIntervalDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox dayEndComboBox;
    private JComboBox monthEndComboBox;
    private JComboBox yeahEndComboBox;
    private JSpinner hoursEndSpinner;
    private JSpinner secondsEndSpinner;
    private JSpinner minutesEndSpinner;
    private JComboBox dayBeginComboBox;
    private JComboBox yearBeginComboBox;
    private JComboBox monthBeginComboBox;
    private JSpinner hoursBeginSpinner;
    private JSpinner secondsBeginSpinner;
    private JSpinner minutesBeginSpinner;
    private JLabel durationLabel;

    private final TimeInterval timeInterval;
    private final Task task;
    
    public CreateTimeIntervalDialog(Task task) {
        
        this.task = task;
        this.timeInterval = new TimeInterval(GlobalVariables.getNextTimeIntervalId(), null);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        this.setSize(600, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        startUp();
        addListener();


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

    private void addListener() {

        // Duration
        hoursBeginSpinner.addChangeListener(e -> updateDuration());
        minutesBeginSpinner.addChangeListener(e -> updateDuration());
        secondsBeginSpinner.addChangeListener(e -> updateDuration());
        hoursEndSpinner.addChangeListener(e -> updateDuration());
        minutesEndSpinner.addChangeListener(e -> updateDuration());
        secondsEndSpinner.addChangeListener(e -> updateDuration());

        dayBeginComboBox.addActionListener(e -> updateDuration());
        monthBeginComboBox.addActionListener(e -> updateDuration());
        yearBeginComboBox.addActionListener(e -> updateDuration());
        dayEndComboBox.addActionListener(e -> updateDuration());
        monthEndComboBox.addActionListener(e -> updateDuration());
        yeahEndComboBox.addActionListener(e -> updateDuration());

        // Monthly Day Update
        monthBeginComboBox.addActionListener(e -> {
            System.out.println("Month Begin: " + monthBeginComboBox.getSelectedIndex());
            int month = monthBeginComboBox.getSelectedIndex() + 1;
            int year = yearBeginComboBox.getSelectedIndex() + 2000;
            int days = LocalDate.of(year, month, 1).lengthOfMonth();
            DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
            addDays(days, model);
            dayBeginComboBox.setModel(model);
        });

        monthEndComboBox.addActionListener(e -> {
            int month = monthEndComboBox.getSelectedIndex() + 1;
            int year = yeahEndComboBox.getSelectedIndex() + 2000;
            int days = LocalDate.of(year, month, 1).lengthOfMonth();
            DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
            addDays(days, model);
            dayEndComboBox.setModel(model);
        });
    }

    private int[] addDays(int days, DefaultComboBoxModel<Integer> model) {
        int[] dayArray = new int[days];
        for (int i = 0; i < days; i++) {
            dayArray[i] = i + 1;
            model.addElement(i + 1);
        }
        return dayArray;
    }

    private void onOK() {
        // add your code here
        LocalDateTime begin = getBegin();
        LocalDateTime end = getEnd();
        if (isInputValid(begin, end)) {
            timeInterval.setStartTime(begin);
            timeInterval.setEndTime(end);
            task.addTimeInterval(timeInterval);
            timeInterval.setTask(task);
            timeInterval.writeDatabase();
            timeInterval.addGlobal();
            updateDuration();
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private LocalDateTime getBegin() {
        LocalDate beginDate = LocalDate.of(yearBeginComboBox.getSelectedIndex() + 2000, monthBeginComboBox.getSelectedIndex() + 1, dayBeginComboBox.getSelectedIndex() + 1);
        LocalTime beginTime = LocalTime.of((int) hoursBeginSpinner.getValue(), (int) minutesBeginSpinner.getValue(), (int) secondsBeginSpinner.getValue());
        return LocalDateTime.of(beginDate, beginTime);
    }

    private LocalDateTime getEnd() {
        LocalDate endDate = LocalDate.of(yeahEndComboBox.getSelectedIndex() + 2000, monthEndComboBox.getSelectedIndex() + 1, dayEndComboBox.getSelectedIndex() + 1);
        LocalTime endTime = LocalTime.of((int) hoursEndSpinner.getValue(), (int) minutesEndSpinner.getValue(), (int) secondsEndSpinner.getValue());
        return LocalDateTime.of(endDate, endTime);
    }

    private boolean isInputValid(LocalDateTime begin, LocalDateTime end) {
        if (begin.isAfter(end)) {
            JOptionPane.showMessageDialog(null, "Begin date is after end date!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void updateDuration() {
        long duration = Duration.between(getBegin(), getEnd()).toSeconds();
        durationLabel.setText("Duration: " + String.format("%02d:%02d:%02d", duration / 3600, (duration % 3600) / 60, (duration % 60)));
    }

    private void startUp() {

        LocalDateTime now = LocalDateTime.now();

        int days = LocalDate.of(now.getYear(), now.getMonthValue(), 1).lengthOfMonth();
        DefaultComboBoxModel<Integer> model = new DefaultComboBoxModel<>();
        addDays(days, model);
        dayBeginComboBox.setModel(model);
        dayEndComboBox.setModel(model);

        // Begin

        // Date
        dayBeginComboBox.setSelectedIndex(now.getDayOfMonth() - 1);
        monthBeginComboBox.setSelectedIndex(now.getMonthValue() - 1);
        yearBeginComboBox.setSelectedIndex(now.getYear() - 2000);

        // Time
        hoursBeginSpinner.setModel(new SpinnerNumberModel(now.getHour(), 0, 23, 1));
        minutesBeginSpinner.setModel(new SpinnerNumberModel(now.getMinute(), 0, 59, 1));
        secondsBeginSpinner.setModel(new SpinnerNumberModel(now.getSecond(), 0, 59, 1));

        // End

        // Date
        dayEndComboBox.setSelectedIndex(now.getDayOfMonth() - 1);
        monthEndComboBox.setSelectedIndex(now.getMonthValue() - 1);
        yeahEndComboBox.setSelectedIndex(now.getYear() - 2000);

        // Time
        hoursEndSpinner.setModel(new SpinnerNumberModel(now.getHour(), 0, 23, 1));
        minutesEndSpinner.setModel(new SpinnerNumberModel(now.getMinute(), 0, 59, 1));
        secondsEndSpinner.setModel(new SpinnerNumberModel(now.getSecond(), 0, 59, 1));
    }
}
