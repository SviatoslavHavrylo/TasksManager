package view;

import javax.swing.table.DefaultTableModel;
import model.ArrayTaskList;
import model.Task;

/**
 * Created by Sviatoslav_H on 09.01.2018.
 */
public class TaskListTableModel extends DefaultTableModel {
    private ArrayTaskList taskList;
    private String[] columnsNames = new String[] {"Title", "Next time","Repeated","Active"};

    public TaskListTableModel(ArrayTaskList taskList) {
        super();
        this.taskList = taskList;
        this.setColumnIdentifiers(columnsNames);
    }

    @Override
    public int getColumnCount() {
        return columnsNames.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Boolean.class;
            case 3:
                return Boolean.class;
            default:
                return String.class;
        }
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Task getValueAt(int rowIndex) {
        return taskList.getTask(rowIndex);
    }
   }
