package controller;

import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.ArrayTaskList;
import model.Task;
import model.TaskIO;
import view.TaskView;
import view.TaskManagerView;
import view.TaskListTableModel;

/**
 * Created by Sviatoslav_H on 08.01.2018.
 */
public class MainController {
    private ArrayTaskList taskList;
    private TaskManagerView taskManagerView;
    private TaskView taskView;
    private Task newTask;
    private TaskListTableModel model;
    private DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
    private static final Logger log = Logger.getLogger(Task.class);

    public MainController(TaskManagerView taskManagerView) {
        this.taskManagerView = taskManagerView;
        this.taskManagerView.addSaveFileButtonListener(new saveFileButtonListener());
        this.taskManagerView.addLoadFileButtonListener(new loadFileButtonListener());
        this.taskManagerView.addCreateNewTaskButtonListener(new createNewTaskButtonListener());
        this.taskManagerView.addEditTaskButtonListener(new editTaskButtonListener());
        this.taskManagerView.addDeleteTaskButtonListener(new deleteTaskButtonListener());
        this.taskManagerView.addShowTasksSelectionButtonListener(new showTasksSelectionButtonListener());
        this.taskManagerView.addCancelSelectionButtonListener(new cancelSelectionButtonListener());
        this.taskList = new ArrayTaskList();
        model = new TaskListTableModel(taskList);
        this.taskManagerView.setModelTable(model);
        ScheduledReminderThread reminderThread = new ScheduledReminderThread(taskList);
    }

    class saveFileButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileSave = new JFileChooser();
            FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("txt files (*.txt)", "txt");
            // add filters
            fileSave.addChoosableFileFilter(txtFilter);
            fileSave.setFileFilter(txtFilter);

            int ret = fileSave.showDialog(null, "Save file");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileSave.getSelectedFile();
                try {
                    TaskIO.writeText(taskList, file);
                    log.info("File: "+file+" was successfully saved");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    taskManagerView.displayErrorMessage("Something gone wrong");
                }
            }
        }
    }

    class loadFileButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showDialog(null, "Open file");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileopen.getSelectedFile();
                try {
                    model.setRowCount(0);
                    TaskIO.readText(taskList, file);
                    Object[] row = new Object[4];
                    for (Task next : taskList) {
                        model.addRow(new Object[]{next.getTitle(), dateFormat.format(next.getTime()), next.isRepeated(), next.isActive()});
                    }
                    log.info("File: "+file+" was successfully loaded to app");
                } catch (IOException e1) {
                    e1.printStackTrace();
                    taskManagerView.displayErrorMessage("Something gone wrong");
                } catch (ParseException e1) {
                    e1.printStackTrace();
                    taskManagerView.displayErrorMessage("Something gone wrong");
                }
            }
        }
    }

    class createNewTaskButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            taskView = new TaskView();
            taskView.addSaveNewTaskButtonListener(new saveNewTaskButtonListener());
            taskView.setVisible(true);
        }
    }

    class editTaskButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selIndex = taskManagerView.getTaskTableSelectedRow();
            if (selIndex < 0) {
                taskManagerView.displayErrorMessage("Select task first");
            }
            Task value = model.getValueAt(selIndex);
            taskView = new TaskView(value);
            taskView.addSaveNewTaskButtonListener(new saveNewTaskButtonListener());
            taskView.setVisible(true);
        }
    }

    class deleteTaskButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selIndex = taskManagerView.getTaskTableSelectedRow();
            model.removeRow(selIndex);
            taskList.remove(taskList.getTask(selIndex));
        }
    }

    class showTasksSelectionButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            taskManagerView.setBorderTitle("Tasks from "+dateFormat.format(taskManagerView.getDateFrom())+" to "+dateFormat.format(taskManagerView.getDateTo()));
        }
    }

    class cancelSelectionButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            taskManagerView.setBorderTitle("All tasks");
        }
    }

    class saveNewTaskButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String newTitle = taskView.getTaskTitle();
            Date newDate = taskView.getTime();
            Date newStartTime = taskView.getStartTime();
            Date newEndTime = taskView.getEndTime();
            boolean newIsRepeated = taskView.getRepetedTask();
            boolean newIsActive = taskView.getIsActiveTask();
            int newInterval = taskView.getInterval();
            if (taskView.getTask() == null) {
                if (newTitle.isEmpty()) {
                    taskManagerView.displayErrorMessage("Give a name to a new task");
                }
                if (newIsRepeated) {
                    newTask = new Task(newTitle, newStartTime, newEndTime, newInterval);
                } else {
                    newTask = new Task(newTitle, newDate);
                }
                newTask.setActive(newIsActive);
                taskView.dispose();
                taskList.add(newTask);
                model.addRow(new Object[]{newTask.getTitle(), dateFormat.format(newTask.getTime()), newTask.isRepeated(), newTask.isActive()});
                log.info("New task was created. "+newTask.toString());

            } else {
                Task editTask = taskView.getTask();
                editTask.setTitle(newTitle);
                editTask.setTime(newDate);
                editTask.setStartTime(newStartTime);
                editTask.setEndTime(newEndTime);
                editTask.setRepeated(newIsRepeated);
                editTask.setActive(newIsActive);
                editTask.setRepeatInterval(newInterval);
                taskView.dispose();
                log.info("Task was edited. "+editTask.toString());
            }
            model.fireTableDataChanged();
        }
    }
}
