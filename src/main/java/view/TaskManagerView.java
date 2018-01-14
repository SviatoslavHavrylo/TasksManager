package view;

import com.intellij.uiDesigner.core.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Created by Sviatoslav_H on 07.01.2018.
 */
public class TaskManagerView extends JFrame {
    private JButton loadFileButton;
    private JButton saveFileButton;
    private JButton deleteTaskButton;
    private JTable TaskTable;
    private JButton editTaskButton;
    private JButton createNewTaskButton;
    private JPanel mainPanel;
    private JSpinner dateFrom;
    private JSpinner dateTo;
    private JButton showTasksSelectionButton;
    private JButton cancelSelectionButton;
    private JLabel labelFrom;
    private JLabel labelTo;
    private final JScrollPane scrollPane1 = new JScrollPane();;

    public TaskManagerView() {

        super("Task manager app");
        createGUI();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainPanel);
        this.pack(); //устанавливаем оптимальный размер окна
        this.setLocationRelativeTo(null);
    }

    private void createGUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(3, 6, new Insets(0, 0, 0, 0), -1, -1));
        loadFileButton = new JButton();
        loadFileButton.setText("Load file");
        mainPanel.add(loadFileButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        saveFileButton = new JButton();
        saveFileButton.setText("Save file");
        mainPanel.add(saveFileButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        editTaskButton = new JButton();
        editTaskButton.setText("Edit task");
        mainPanel.add(editTaskButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createNewTaskButton = new JButton();
        createNewTaskButton.setText("Create new task");
        mainPanel.add(createNewTaskButton, new GridConstraints(0, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        deleteTaskButton = new JButton();
        deleteTaskButton.setText("Delete task");
        mainPanel.add(deleteTaskButton, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        labelFrom = new JLabel();
        labelFrom.setText("Show tasks from");
        mainPanel.add(labelFrom, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dateFrom = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(dateFrom);
        dateFrom.setEditor(timeEditor);
        dateFrom.setValue(new Date());
        mainPanel.add(dateFrom, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        labelTo = new JLabel();
        labelTo.setText("to");
        mainPanel.add(labelTo, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dateTo = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor2 = new JSpinner.DateEditor(dateTo);
        dateTo.setEditor(timeEditor2);
        dateTo.setValue(new Date());
        mainPanel.add(dateTo, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        showTasksSelectionButton = new JButton();
        showTasksSelectionButton.setText("Show selection");
        mainPanel.add(showTasksSelectionButton, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelSelectionButton = new JButton();
        cancelSelectionButton.setText("Cancel selection");
        mainPanel.add(cancelSelectionButton, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        mainPanel.add(scrollPane1, new GridConstraints(2, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setBorder(BorderFactory.createTitledBorder("All tasks"));

        TaskTable = new JTable();
        TaskTable.setShowVerticalLines(true);
        TaskTable.setSurrendersFocusOnKeystroke(true);
        TaskTable.setUpdateSelectionOnSort(false);
        scrollPane1.setViewportView(TaskTable);

    }

    public void setBorderTitle(String title) {
        scrollPane1.setBorder(BorderFactory.createTitledBorder(title));
    }

    public void addSaveFileButtonListener(ActionListener saveFileButtonListener) {
        saveFileButton.addActionListener(saveFileButtonListener);
    }

    public void addLoadFileButtonListener(ActionListener loadFileButtonListener) {
        loadFileButton.addActionListener(loadFileButtonListener);
    }

    public void addCreateNewTaskButtonListener(ActionListener createNewTaskButtonListener) {
        createNewTaskButton.addActionListener(createNewTaskButtonListener);
    }

    public void addEditTaskButtonListener(ActionListener editTaskButtonListener) {
        editTaskButton.addActionListener(editTaskButtonListener);
    }

    public void addDeleteTaskButtonListener(ActionListener deleteTaskButtonListener) {
        deleteTaskButton.addActionListener(deleteTaskButtonListener);
    }

    public void addShowTasksSelectionButtonListener(ActionListener editTaskButtonListener) {
        showTasksSelectionButton.addActionListener(editTaskButtonListener);
    }

    public void addCancelSelectionButtonListener(ActionListener editTaskButtonListener) {
        cancelSelectionButton.addActionListener(editTaskButtonListener);
    }

    public void setModelTable(TaskListTableModel model) {
        TaskTable.setModel(model);
    }

    public int getTaskTableSelectedRow() {
        return TaskTable.getSelectedRow();
    }

    public void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage);
    }

    public Date getDateFrom() {
        return (Date) dateFrom.getValue();
    }

    public Date getDateTo() {
        return (Date) dateTo.getValue();
    }

}
