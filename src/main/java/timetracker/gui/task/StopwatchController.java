package timetracker.gui.task;

import timetracker.data.Task;

import javax.swing.*;
import java.time.Duration;

public class StopwatchController extends Thread{

    private final Task task;
    private final JLabel label;
    private boolean running;

    public StopwatchController(Task task, JLabel label) {
        this.task = task;
        this.label = label;
    }

    public void run() {
        running = true;
        int length = task.getTimeIntervals().size() - 1;
        while (running) {
            try {
                long s = Duration.between(task.getTimeIntervals().get(length).getStartTime(), java.time.LocalDateTime.now()).getSeconds();
                label.setText(String.format("%d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60)));
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void end() {
        running = false;
    }
}
