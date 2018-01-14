import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * Created by Sviatoslav_H on 14.01.2018.
 */
public class ScheduledReminderThread {
    private ArrayTaskList taskList;
    private TaskView taskView;
    private Task newTask;

    public ScheduledReminderThread(ArrayTaskList taskList) {
        this.taskList = taskList;
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        Runnable pinger = new Runnable() {
            long oneMinute = MILLISECONDS.convert(1, MINUTES);

            public void run() {
                Date currentDate = new Date();
                for (Task task : ScheduledReminderThread.this.taskList) {
                    long duration = task.nextTimeAfter(currentDate).getTime() - currentDate.getTime();
                    if (duration <= oneMinute & duration > 0)
                        taskView = new TaskView(task);
                        taskView.setTitle("You have new task NOW !");
                        taskView.setVisible(true);
                }
            }
        };
        ses.scheduleAtFixedRate(pinger, 5, 60, TimeUnit.SECONDS);
    }
}
