import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Sviatoslav_H on 08.01.2018.
 */
public class MainController {
private ArrayTaskList taskList;
    private TaskManagerView taskManagerView;
    private TaskView taskView;
    private Task newTask;
    private DefaultTableModel model;

    public MainController(TaskManagerView taskManagerView) {
        this.taskManagerView = taskManagerView;
        this.taskManagerView.addSaveFileButtonListener(new saveFileButtonListener());
        this.taskManagerView.addLoadFileButtonListener(new loadFileButtonListener());
        this.taskManagerView.addCreateNewTaskButtonListener(new createNewTaskButtonListener());
        this.taskManagerView.addEditTaskButtonListener(new editTaskButtonListener());
        Object[] colomns = {"Title","Next time","Repeated","Active"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(colomns);
        this.taskManagerView.setModelTable(model);
        this.taskList = new ArrayTaskList();
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
                } catch (IOException e1) {
                    e1.printStackTrace();
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
                    TaskIO.readText(taskList, file);
                    Object[] row = new Object[4];
                    for (Task next : taskList) {
                        model.addRow(new Object[]{next.getTitle(),next.getTime()});
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ParseException e1) {
                    e1.printStackTrace();
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
            Object value = model.getValueAt(selIndex, 0);
        }
    }

    class saveNewTaskButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String newTitle = taskView.getTaskTitle();
            Date newDate = taskView.getTime();
            newTask = new Task(newTitle, newDate);
            taskView.dispose();
            taskList.add(newTask);
            model.addRow(new Object[]{newTask, newTask.getTime()});
        }
    }
}
