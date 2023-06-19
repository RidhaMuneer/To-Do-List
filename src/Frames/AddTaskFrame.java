package Frames;

import Database.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AddTaskFrame extends JFrame {
    private JTextField titleField;
    private JSpinner dueDateSpinner;
    private JButton addButton;
    private JLabel titleLabel;
    private JLabel dueDateLabel;

    DBConnection connection = new DBConnection();

    public AddTaskFrame(DefaultTableModel tableModel) {
        initComponents(tableModel);
        createLayout();
        setTitle("Add Task");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //we learned about the JSpinner and its model from these resources:
    //https://www.javatpoint.com/java-jspinner
    //http://www.java2s.com/Tutorial/Java/0240__Swing/SpinnerDateData.htm
    private void initComponents(DefaultTableModel tableModel) {
        titleLabel = new JLabel("Title:"); 
        titleField = new JTextField(20);
        dueDateLabel = new JLabel("Due Date:"); 
        SpinnerDateModel dateModel = new SpinnerDateModel();
        dueDateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dueDateSpinner, "yyyy-MM-dd");
        dueDateSpinner.setEditor(dateEditor);
        addButton = new JButton("Add");
        addTaskListener(tableModel);
    }

    private void addTaskListener(DefaultTableModel tableModel) {
        ActionListener a = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PreparedStatement stmt = connection.createAddStatement();
                    stmt.setString(1, titleField.getText());
                    Date selectedDate = (Date) dueDateSpinner.getValue();
                    LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    stmt.setDate(2, java.sql.Date.valueOf(localDate));
                    stmt.executeUpdate();
                    String dueDateString = localDate.toString();
                    tableModel.addRow(new Object[]{titleField.getText(), dueDateString});
                    setVisible(false);
                } catch (SQLException x) {
                    System.err.println(x);
                }
            }

        };
        addButton.addActionListener(a);
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
                        .addComponent(addButton)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(titleLabel)
                        .addComponent(titleField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(dueDateLabel)
                        .addComponent(dueDateSpinner)) 
                .addComponent(addButton));

        pack();
    }
}
