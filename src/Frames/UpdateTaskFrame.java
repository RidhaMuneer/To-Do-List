package Frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Database.DBConnection;

public class UpdateTaskFrame extends JFrame {

    private JTextField titleField;
    private JSpinner dueDateSpinner;
    private JButton updateButton;
    private JLabel titleLabel;
    private JLabel dueDateLabel;
    private DBConnection connection = new DBConnection();

    public UpdateTaskFrame(DefaultTableModel tableModel, JTable taskTable, String value1, Date value2) {
        initComponents(taskTable, tableModel, value1, value2);
        createLayout();
        setTitle("Update Task");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //we learned about the JSpinner and its model from these resources:
    //https://www.javatpoint.com/java-jspinner
    //http://www.java2s.com/Tutorial/Java/0240__Swing/SpinnerDateData.htm
    private void initComponents(JTable taskTable, DefaultTableModel tableModel, String value1, Date value2) {
        titleLabel = new JLabel("Title:");
        titleField = new JTextField(20);
        titleField.setText(value1);
        dueDateLabel = new JLabel("Due Date:");

        LocalDate localDate = value2.toLocalDate();

        SpinnerDateModel dateModel = new SpinnerDateModel(
            Date.valueOf(localDate), null, null, Calendar.DAY_OF_MONTH
        );

        dueDateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dueDateSpinner, "yyyy-MM-dd");
        dueDateSpinner.setEditor(dateEditor);
        dueDateSpinner.setValue(Date.valueOf(localDate));

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    String clickedRowValue = (String) taskTable.getValueAt(selectedRow, 0);
                    int id = connection.getTheID(clickedRowValue);
                    if (id != -1) {
                        String title = titleField.getText();
                        LocalDate localDate = ((java.util.Date) dueDateSpinner.getValue())
                            .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        Date dueDate = Date.valueOf(localDate);

                        connection.updateTaskById(id, title, dueDate);

                        tableModel.setValueAt(title, selectedRow, 0);
                        tableModel.setValueAt(dueDate, selectedRow, 1);
                        tableModel.fireTableCellUpdated(selectedRow, 0);
                        tableModel.fireTableCellUpdated(selectedRow, 1);
                    }
                    setVisible(false);
                }
            }
        });
    }

    private void createLayout() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(titleLabel)
                        .addComponent(dueDateLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(titleField)
                        .addComponent(dueDateSpinner)
                        .addComponent(updateButton)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(titleLabel)
                        .addComponent(titleField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(dueDateLabel)
                        .addComponent(dueDateSpinner))
                .addComponent(updateButton));

        pack();
    }
}
