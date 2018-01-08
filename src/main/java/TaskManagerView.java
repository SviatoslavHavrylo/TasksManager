import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

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
    private DefaultTableModel model;

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

        ActionListener loadFileButtonListener = new loadFileButtonListener();
        loadFileButton.addActionListener(loadFileButtonListener);

        ActionListener saveFileButtonListener = new saveFileButtonListener();
        saveFileButton.addActionListener(saveFileButtonListener);

        ActionListener createNewTaskButtonListener = new createNewTaskButtonListener();
        createNewTaskButton.addActionListener(createNewTaskButtonListener);

        ActionListener editTaskButtonListener = new editTaskButtonListener();
        editTaskButton.addActionListener(editTaskButtonListener);

       Object[] colomns = {"Title","Next time","Repeated","Active"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(colomns);

        TaskTable.setModel(model);
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
                      TaskList tl = new ArrayTaskList();
                      try {
                          TaskIO.writeText(tl, file);
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
                TaskList tl = new ArrayTaskList();
                try {
                    TaskIO.readText(tl, file);
                    Object[] row = new Object[4];
                    for (Task next : tl) {
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
            TaskView theTaskView = new TaskView();
            theTaskView.setVisible(true);
        }
    }

    class editTaskButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selIndex = TaskTable.getSelectedRow();
            Object value = model.getValueAt(selIndex, 0);
        }
    }

}
