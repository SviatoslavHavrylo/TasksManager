import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sviatoslav_H on 15.10.2017.
 */
public class Task implements Cloneable, Serializable {
    private String title;
    private Date time;
    private boolean isActive;
    private Date startTime;
    private Date endTime;
    private int repeatInterval;
    private boolean isRepeated;
    private final int HASHNUMBER = 31;
    private DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");

    /**
     * @param title String
     * @param time  int
     * @throws IllegalArgumentException
     */

    public Task(String title, Date time) throws IllegalArgumentException {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Give a name to new task");
        }
        if (time == null) {
            throw new IllegalArgumentException("Time cannot be NULL");
        }
        this.title = title;
        this.time = time;
    }

    /**
     * @param title          string
     * @param startTime      int
     * @param endTime        int
     * @param repeatInterval int
     * @throws IllegalArgumentException
     */
    public Task(String title, Date startTime, Date endTime, int repeatInterval) throws IllegalArgumentException {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Give a name to new task");
        }
        if (startTime == null) {
            throw new IllegalArgumentException("Start time cannot be NULL");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("End time cannot be NULL");
        }
        if (repeatInterval < 0) {
            throw new IllegalArgumentException("Repeat interval cannot be negative number");
        }
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeatInterval = repeatInterval;
        this.isRepeated = true;
    }

    /**
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return boolean
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @param active boolean
     */
    public void setActive(boolean active) {
        this.isActive = active;
    }

    public void setRepeatInterval(int repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    /**
     * @return time int
     */
    public Date getTime() {
        if (isRepeated) {
            return startTime;
        } else {
            return time;
        }
    }

    /**
     * @return time int
     */
    public Date getStartTime() {
        if (isRepeated) {
            return startTime;
        } else {
            return time;
        }
    }

    /**
     * @return time int
     */
    public Date getEndTime() {
        if (isRepeated) {
            return endTime;
        } else {
            return time;
        }
    }

    /**
     * @param time int
     * @throws IllegalArgumentException
     */
    public void setTime(Date time) throws IllegalArgumentException {
        if (time == null) {
            throw new IllegalArgumentException("Time cannot be NULL");
        }
        this.time = time;
        if (isRepeated) {
            setRepeated(false);
        }
    }

    /**
     * @param start    int
     * @param end      int
     * @param interval int
     * @throws IllegalArgumentException
     */
    public void setTime(Date start, Date end, int interval) throws IllegalArgumentException {
        if (start == null) {
            throw new IllegalArgumentException("Start time cannot be NULL");
        }
        if (end == null) {
            throw new IllegalArgumentException("End time cannot be NULL");
        }
        this.startTime = start;
        this.endTime = end;
        this.repeatInterval = interval;
        if (!isRepeated) {
            setRepeated(true);
        }
    }

    /**
     * @param startTime int
     * @throws IllegalArgumentException
     */
    public void setStartTime(Date startTime) throws IllegalArgumentException {
        if (startTime == null) {
            throw new IllegalArgumentException("Start time cannot be NULLr");
        }
        this.startTime = startTime;
    }

    /**
     * @param endTime int
     * @throws IllegalArgumentException
     */
    public void setEndTime(Date endTime) throws IllegalArgumentException {
        if (endTime == null) {
            throw new IllegalArgumentException("End time cannot be NULL");
        }
        this.endTime = endTime;
    }

    /**
     * @return repeatInterval int
     */
    public int getRepeatInterval() {
        if (isRepeated) {
            return repeatInterval;
        } else {
            return 0;
        }
    }

    /**
     * @param repeated boolean
     */
    public void setRepeated(boolean repeated) {
        isRepeated = repeated;
    }

    /**
     * @return repeatInterval int
     */
    public boolean isRepeated() {
        return isRepeated;
    }

    /**
     * @param current int
     * @return repeatInterval int
     */
    public Date nextTimeAfter(Date current) {
        Date result = null;
        if (!isActive) {
            return result;
        }
        if (!isRepeated) {
            return (current.getTime() >= time.getTime()) ? result : time;
        }
        if (isRepeated && endTime.after(current)) {
            Date countTime = (Date) startTime.clone();

            while (countTime.getTime() <= current.getTime() && endTime.after(current)) {
                countTime.setTime(countTime.getTime() + repeatInterval * 1000);
            }
            return (endTime.getTime() >= countTime.getTime()) ? countTime : result;
        }
        return null;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (isActive != task.isActive) return false;
        if (isRepeated != task.isRepeated) return false;

        if (isRepeated) {
            if (!startTime.equals(task.startTime)) return false;
            if (!endTime.equals(task.endTime)) return false;
            if (repeatInterval != task.repeatInterval) return false;
        } else {
            if (!time.equals(task.time)) return false;
        }

        return title.equals(task.title);
    }


    @Override
    public int hashCode() {
        int result = title.hashCode();

        result = HASHNUMBER * result + (isActive ? 1 : 0);
        result = HASHNUMBER * result + (isRepeated ? 1 : 0);

        if (isRepeated) {
            result = HASHNUMBER * result + startTime.hashCode();
            result = HASHNUMBER * result + endTime.hashCode();
            result = HASHNUMBER * result + (int) (repeatInterval ^ (repeatInterval >>> HASHNUMBER));
        } else {
            result = HASHNUMBER * result + time.hashCode();
        }

        return result;
    }

    @Override
    public String toString() {
        String str = "Task title: " +'\''+ title + '\''+" Active:" + this.isActive() ;
        if(this.isRepeated()) {
            str += " Repeated task: start time: " + dateFormat.format(this.getStartTime()) + ", end time: " + dateFormat.format(this.getEndTime()) + ", interval: " + this.getRepeatInterval() + "\n";
        }
        else {
            str += " Unrepeated task: start time: " + dateFormat.format(this.getStartTime()) + "\n";
        }
        return str;
    }

    @Override
    public Task clone() {
        Task taskClone = null;
        try {
            taskClone = (Task) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        taskClone.title = this.title;
        taskClone.time = this.time;
        taskClone.isActive = this.isActive;
        taskClone.startTime = this.startTime;
        taskClone.endTime = this.endTime;
        taskClone.repeatInterval = this.repeatInterval;
        taskClone.isRepeated = this.isRepeated;
        return taskClone;
    }
}
