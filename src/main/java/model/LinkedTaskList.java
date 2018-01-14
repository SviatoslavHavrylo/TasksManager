package model;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Sviatoslav_H on 19.11.2017.
 */
public class LinkedTaskList extends TaskList {
    private int pointer = 0;
    Entry<Task> last;
    Entry<Task> first;

    private static class Entry<Task> {
        Task element;
        Entry<Task> next;
        Entry<Task> prev;

        public Entry(Task element) {
            this.element = element;
        }
    }

    /**
     * @param task Task
     */
    public void add(Task task) {
        if (task == null) {
            throw new NullPointerException();
        }
        Entry newEntry = new Entry(task);
        if (first == null) {
            first = newEntry;

        } else {
            last.next = newEntry;
        }

        last = newEntry;
        pointer++;
    }
    
    /**
     * @param task Task
     * @return boolean
     */
    public boolean remove(Task task) {
        if (task == null) {
            return false;
        } else {
            Entry currentEntry = first;
            Entry previous = null;
            while (currentEntry != null) {
                if (currentEntry.element.equals(task)) {
                    if (previous != null) {
                        previous.next = currentEntry.next;
                        if (currentEntry.next == null) {
                            last = previous;
                        }
                    } else {
                        first = first.next;
                        if (first == null) {
                            last = null;
                        }
                    }
                    currentEntry.element = null;
                    currentEntry.next = null; 
                    currentEntry.prev = null;
                    pointer--;
                    return true;
                }
                previous = currentEntry;
                currentEntry = currentEntry.next;
            }
            return false;
        }
    }

    /**
     * @return int size
     */
    public int size() {
        return pointer;
    }

    /**
     * @param index int
     * @return Task size
     * @throws Exception 
     */
    public Task getTask(int index) throws Exception {
        if (index > pointer) throw new Exception("Index out of list");
        Entry x = first;
        for (int i = 0; i < index; i++)
            x = x.next;
        return (Task) x.element;
    }
@Override
    public LinkedTaskListIterator iterator() {
        return new LinkedTaskListIterator();
    }

    private class LinkedTaskListIterator implements Iterator<Task> {
        private Entry nextEntry = first;
        private int cursor;     
        private int last = -1;

        public boolean hasNext() {
        if (nextEntry == null) return false;
            return nextEntry != null;           
        }

        public Task next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                Task result = (Task) nextEntry.element;
                nextEntry = nextEntry.next;
                last = cursor;
                cursor++;
                return result;
            }                        
        }

        public void remove() {
          if (nextEntry == null) throw new IllegalStateException();
          if (nextEntry == first) throw new IllegalStateException("can't call remove() before next()");
            try {
                LinkedTaskList.this.remove(LinkedTaskList.this.getTask(--cursor));               
            } catch (Exception e) {
                e.printStackTrace();
            }         
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkedTaskList tasks = (LinkedTaskList) o;

        if (size() != tasks.size()) return false;

        LinkedTaskList.Entry tasksEntry = tasks.first;
        for (LinkedTaskList.Entry thisEntry = this.first; thisEntry != null; thisEntry = thisEntry.next) {
            if (thisEntry != null ? !thisEntry.element.equals(tasksEntry.element) : tasksEntry != null) return false;
            tasksEntry = tasksEntry.next;
        }
        return true;
    }
    
     @Override
        public int hashCode() {
            int result = pointer;

            for (Task task : this) {
                result = 7 * result + task.hashCode();
            }
            return result;
        }
    
     @Override
    public TaskList clone() {
        LinkedTaskList list = new LinkedTaskList();
        list.pointer = 0;
        list.first = list.last = null;

        for (LinkedTaskList.Entry node = this.first; node != null; node = node.next) {
            list.add((Task) node.element);
        }

        return list;
    }

}
