package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import model.ArrayTaskList;
import model.Task;
import view.TaskView;

import static java.util.concurrent.TimeUnit.*;


/**
 * Created by Sviatoslav_H on 14.01.2018.
 */

public class ScheduledReminderThread {
    private ArrayTaskList taskList;

    public ScheduledReminderThread(ArrayTaskList taskList) {
        this.taskList = taskList;
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        Runnable pinger = new Runnable() {
            long oneMinute = MILLISECONDS.convert(1, MINUTES);

            public void run() {
                System.out.println("running scheduler");
                Date currentDate = new Date();
                for (Task task : ScheduledReminderThread.this.taskList) {
                    long duration = task.nextTimeAfter(currentDate).getTime() - currentDate.getTime();

                    if (duration <= oneMinute & duration > 0) {
                        TaskView taskView = new TaskView(task);
                        taskView.changeSaveButton();
                        taskView.addOkButtonListener(new okButtonListener(taskView));
                        taskView.setTitle("You have new task NOW !");
                        taskView.setVisible(true);
                    }
                }
            }
        };
        ses.scheduleWithFixedDelay(pinger, 60, 60, TimeUnit.SECONDS);
    }

    class okButtonListener implements ActionListener {
        private TaskView taskView;
        public okButtonListener(TaskView taskView) {
            this.taskView = taskView;
        }

        public void actionPerformed(ActionEvent e) {
            taskView.dispose();
        }
    }
}
