import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Sviatoslav_H on 07.01.2018.
 */
public class TaskManagerView extends JFrame {
    private JButton loadFileButton;
    private JButton saveFileButton;
    private JTable TaskTable;
    private JButton editTaskButton;
    private JButton createNewTaskButton;
    private JPanel mainPanel;

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
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        loadFileButton = new JButton();
        loadFileButton.setText("Load file");
        mainPanel.add(loadFileButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        saveFileButton = new JButton();
        saveFileButton.setText("Save file");
        mainPanel.add(saveFileButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        editTaskButton = new JButton();
        editTaskButton.setText("Edit task");
        mainPanel.add(editTaskButton, new com.intellij.uiDesigner.core.GridConstraints(0, 3, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        createNewTaskButton = new JButton();
        createNewTaskButton.setText("Create new task");
        mainPanel.add(createNewTaskButton, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

        final JScrollPane scrollPane1 = new JScrollPane();
        mainPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane1.setBorder(BorderFactory.createTitledBorder("Tasks"));

        TaskTable = new JTable();
        TaskTable.setShowVerticalLines(true);
        TaskTable.setSurrendersFocusOnKeystroke(true);
        TaskTable.setUpdateSelectionOnSort(false);
        scrollPane1.setViewportView(TaskTable);

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

    public void setModelTable(DefaultTableModel model) {
        TaskTable.setModel(model);
    }

    public int getTaskTableSelectedRow() {
        return TaskTable.getSelectedRow();
    }
}
