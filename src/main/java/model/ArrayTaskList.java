package model;

import org.apache.log4j.Logger;
import java.util.Iterator;
/**
 * Created by Sviatoslav_H on 11.11.2017.
 */
public class ArrayTaskList extends TaskList {
    private final int INITSIZE = 5;
    private final int CUTRATE = 2;
    private Task[] taskList = new Task[INITSIZE];
    private int pointer = 0;
    private static final Logger log = Logger.getLogger(ArrayTaskList.class);
    /**
     * @param task Task
     */
    public void add(Task task) {
        if (task == null) {
            throw new NullPointerException();
        }
        if (pointer == taskList.length - 1)
            resize(taskList.length * CUTRATE);
        taskList[pointer++] = task;
    }

    /**
     * @param task Task
     * @return boolean remove
     */
    public boolean remove(Task task) {
        boolean haveFound = false;
        for (int i = 0; i < size(); i++) {
            if (taskList[i].equals(task)) {
                for (int j = i; j < size() - 1; j++)
                    taskList[j] = taskList[j + 1];
                pointer--;
                haveFound = true;
                log.info("Task was deleted from list. "+task.toString());
            }
        }
        if (taskList.length > INITSIZE && pointer < taskList.length / CUTRATE)
            resize(taskList.length / CUTRATE);
        return haveFound;
    }

    /**
     * @return int size
     */
    public int size() {
        return pointer;
    }

    /**
     * @param index int
     * @return Task
     * @throws IllegalArgumentException 
     */
    public Task getTask(int index) throws IllegalArgumentException {
        if (index > pointer) throw new IllegalArgumentException("Index out of list");
        return taskList[index];
    }

    /**
     * @param newLength
     */
    private void resize(int newLength) {
        Task[] newArray = new Task[newLength];
        System.arraycopy(taskList, 0, newArray, 0, pointer);
        taskList = newArray;
    }
@Override
    public Iterator<Task> iterator() {
        return new ArrayTaskListIterator();
    }
    public class ArrayTaskListIterator implements Iterator<Task> {
        private int nextIndex = 0;

        @Override
        public boolean hasNext() {
            return nextIndex < pointer;
        }

        @Override
        public Task next() {
            Task result = taskList[nextIndex];
            nextIndex++;
            return result;
        }
        public void remove() {
        if (nextIndex == 0) throw new IllegalStateException();
            if (nextIndex < 0)
                throw new IllegalStateException();
            try {
                ArrayTaskList.this.remove(getTask(--nextIndex));
                      } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * @param o Object
     * @return boolean 
    */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayTaskList equalsTaskList = (ArrayTaskList) o;

        if (size() != equalsTaskList.size()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Iterator iter = this.iterator();
        while (iter.hasNext()) {
            result += iter.next().hashCode() / 7;
        }
        return result;
    }
}
