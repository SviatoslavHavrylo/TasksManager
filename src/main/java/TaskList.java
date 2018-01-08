
import java.util.Iterator;
import java.io.Serializable;

/**
 * Created by Sviatoslav_H on 19.11.2017.
 */
public abstract class TaskList implements Iterable<Task>, Cloneable, Serializable {
    private int pointer = 0;
    public abstract Iterator<Task> iterator();

    /**
     * @param task Task
     */
    public abstract void add(Task task);
    
    /**
     * @param task Task
     * @return boolean remove
     */
    public abstract boolean remove(Task task);
    
    /**
     * @return int size
     */
    public abstract int size();
    
    /**
     * @param index int
     * @return Task
     * @throws Exception 
     */
    public abstract Task getTask(int index) throws Exception;
    
    /**
     * @param from int
     * @param to int
     * @return TaskList toReturn
     * @throws Exception 
     
    public TaskList incoming(int from, int to) throws Exception {
        TaskList toReturn = this.getClass().newInstance();
                
        for (int i = 0; i < size(); i++) {
            int nextTime = getTask(i).nextTimeAfter(from);
            if (from <= nextTime & nextTime <= to) {
                toReturn.add(getTask(i));
            }
        }

        return toReturn;
    }
    */
    
    @Override
    public boolean equals(Object o) {
       if (o == null || getClass() != o.getClass()) return false;
        TaskList taskListToCheck = (TaskList) o;

        if (taskListToCheck.size() != this.size()) {
            return false;
        }
        Iterator<Task> first = taskListToCheck.iterator();
        Iterator<Task> second = this.iterator();
        while (first.hasNext()) {
            Task firstTask = first.next();
            Task secondTask = second.next();
            if (firstTask != secondTask) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (Task task: this) {
            hashCode += task.hashCode();
        }
        return hashCode;
    }

    @Override
    public String toString() {
        return getClass().getName() + " " + size() + " elements ";
    }

    @Override
    public TaskList clone() {
        TaskList resultTaskList = null;
        try {
            resultTaskList = this.getClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        for (Task task: this) {
            resultTaskList.add(task);
        }
        return resultTaskList;
    }

}
