package model;

import java.util.*;

/**
 * Created by Sviatoslav_H on 10.12.2017.
 */
public class Tasks {

    public static Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end) throws IllegalAccessException, InstantiationException {
        if (tasks == null || start == null || end == null)
            throw new IllegalArgumentException("Incorrect argument");
        if (start.after(end))
            throw new IllegalArgumentException("Incorrect argument");

        TaskList toReturn = new LinkedTaskList();

        for (Task currentTask : tasks) {
            Date nextTime = currentTask.nextTimeAfter(start);
            if (nextTime != null && nextTime.compareTo(end) <= 0) {
                toReturn.add(currentTask);
            }
        }
        return toReturn;
    }

    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end) {
        if (tasks == null || start == null || end == null)
            throw new IllegalArgumentException("Incorrect argument");
        if (start.after(end))
            return null;
        TreeMap<Date, Set<Task>> calendar = new TreeMap<Date, Set<Task>>();
        Iterable<Task> tmpList = null;
        try {
            tmpList = incoming(tasks, start, end);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        for (Task task : tmpList) {
            Date tmp = task.nextTimeAfter(start);
            while (tmp != null && tmp.compareTo(end) <= 0) {
                if (calendar.containsKey(tmp)) {
                    calendar.get(tmp).add(task);
                } else {
                    Set<Task> setOfTasks = new HashSet<Task>();
                    setOfTasks.add(task);
                    calendar.put(tmp, setOfTasks);
                }
                tmp = task.nextTimeAfter(tmp);
            }
        }
        return calendar;
    }
}
