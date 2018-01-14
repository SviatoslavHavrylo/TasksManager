package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import com.intellij.uiDesigner.core.*;
import model.Task;
/**
 * Created by Sviatoslav_H on 07.01.2018.
 */
public class TaskView extends JFrame{
    private JButton save;
    private JCheckBox activeTaskCheckBox;
    private JFormattedTextField taskTitle;
    private JSpinner  startTime;
    private JCheckBox repetedTaskCheckBox;
    private JSpinner endTime;
    private JTextField interval;
    private JSpinner time;
    private JComboBox comboBoxInterval;
    private JPanel mainPanel;
    private Task task;

    public TaskView() {
        super("Task");
        createGUI();
        viewRepeatedTask();
        setInterval(0);
        setComboBoxInterval(0);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(mainPanel);
        this.pack(); //устанавливаем оптимальный размер окна
        this.setLocationRelativeTo(null);
        //this.setResizable(false);
        activeTaskCheckBox.setSelected(true);

        activeTaskCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        repetedTaskCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewRepeatedTask();
            }
        });
        comboBoxInterval.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selInterval = (String) comboBoxInterval.getSelectedItem();

                switch(selInterval) {
                    case "Every day" :
                        int secDay = 24;
                        interval.setText(Integer.toString(secDay));
                        break;
                    case "Every week" :
                        int secWeek = 24*7;
                        interval.setText(Integer.toString(secWeek));
                        break;
                    case "Every month" :
                        //TODO change int for month
                        int secMonth = 24*30;
                        interval.setText(Integer.toString(secMonth));
                        break;
                }
            }
        });
    }

    public Task getTask() {
        return task;
    }

    public TaskView(Task task) {

        this();
        this.task = task;
        this.setTitle("Edite task");
        setTaskTitle(task.getTitle());
        setStartTime(task.getStartTime());
        setRepetedTask(task.isRepeated());
        setEndTime(task.getEndTime());
        setInterval(task.getRepeatInterval());
        setTime(task.getTime());
        activeTaskCheckBox.setSelected(task.isActive());
        viewRepeatedTask();
       setComboBoxInterval(task.getRepeatInterval());
    }

    public void addSaveNewTaskButtonListener(ActionListener saveNewTaskButtonListener) {
        save.addActionListener(saveNewTaskButtonListener);
    }

    private void createGUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(7, 6, new Insets(0, 0, 0, 0), -1, -1));
        save = new JButton();
        save.setText("Save task");
        mainPanel.add(save, new GridConstraints(6, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        taskTitle = new JFormattedTextField();
        taskTitle.setText("");
        mainPanel.add(taskTitle, new GridConstraints(0, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        startTime = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(startTime);
        startTime.setEditor(timeEditor);
        startTime.setValue(new Date()); // will only show the current time

        mainPanel.add(startTime, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        repetedTaskCheckBox = new JCheckBox();
        repetedTaskCheckBox.setText("Repeated task");
        mainPanel.add(repetedTaskCheckBox, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endTime = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor2 = new JSpinner.DateEditor(endTime);
        endTime.setEditor(timeEditor2);
        endTime.setValue(new Date()); // will only show the current time

        mainPanel.add(endTime, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Title");
        mainPanel.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Start time");
        mainPanel.add(label2, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        interval = new JTextField();
        interval.setText("");
        mainPanel.add(interval, new GridConstraints(5, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("end time");
        mainPanel.add(label3, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        time = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor3 = new JSpinner.DateEditor(time);
        time.setEditor(timeEditor3);
        time.setValue(new Date()); // will only show the current time

        mainPanel.add(time, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Time");
        mainPanel.add(label4, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        activeTaskCheckBox = new JCheckBox();
        activeTaskCheckBox.setText("Active task");
        mainPanel.add(activeTaskCheckBox, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        String[] items = {
                "Every day",
                "Every week",
                "Every month"
        };
        comboBoxInterval = new JComboBox(items);
        mainPanel.add(comboBoxInterval, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("interval");
        mainPanel.add(label5, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("interval hours");
        mainPanel.add(label6, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    public void viewRepeatedTask() {
        //setVisible
        boolean taskIsRepeated = repetedTaskCheckBox.isSelected();
        startTime.setEnabled(taskIsRepeated);
        endTime.setEnabled(taskIsRepeated);
        comboBoxInterval.setEnabled(taskIsRepeated);
        interval.setEnabled(taskIsRepeated);
        time.setEnabled(!taskIsRepeated);
    }

    public String getTaskTitle() {
        return taskTitle.getText();
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle.setText(taskTitle);
    }

    public Date getStartTime() {
        return (Date) startTime.getValue();
    }

    public void setStartTime(Date startTime) {
        this.startTime.setValue(startTime);
    }

    public boolean getIsActiveTask() {
        return activeTaskCheckBox.isSelected();
    }

    public boolean getRepetedTask() {
        return repetedTaskCheckBox.isSelected();
    }

    public void setRepetedTask(boolean repetedTaskCheckBox) {
        this.repetedTaskCheckBox.setSelected(repetedTaskCheckBox);
    }

    public Date getEndTime() {
        return (Date) endTime.getValue();
    }

    public void setEndTime(Date endTime) {
        this.endTime.setValue(endTime);
    }

    public int getInterval() {
        return Integer.parseInt(interval.getText());
    }

    public void setInterval(int interval) {
       String intervalValue  = "Every day";
        switch(interval) {
            case 24 :
                intervalValue = "Every day";
                break;
            case 24*7 :
               intervalValue = "Every week";
                break;
            case 24*30 :
                intervalValue = "Every month";
                break;
        }
        this.comboBoxInterval.setSelectedItem (intervalValue);
    }

    public void setComboBoxInterval(int interval) {
        this.interval.setText( Integer.toString(interval));
    }

    public Date getTime() {
        return (Date) time.getValue();
    }

    public void setTime(Date time) {
        this.time.setValue(time);
    }
}
