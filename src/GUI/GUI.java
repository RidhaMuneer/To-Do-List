package GUI;

import Frames.*;
import Database.DBConnection;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GUI extends JFrame{

    private JTable taskTable;
    private JScrollPane tableScrollPane;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    public DefaultTableModel tableModel;
    public DBConnection connection = new DBConnection();
    private DeleteTaskFrame deleteTaskFrame;

    public GUI(String title) throws SQLException {
        setTitle(title);
        initComponents();
        createLayout();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() throws SQLException {
        createMenuBar();
        tableModel = new DefaultTableModel();
        taskTable = new JTable(tableModel);
        taskTable.setModel(tableModel);
        tableModel.addColumn("Title");
        tableModel.addColumn("Due Date");
        connection.retrieveDataToTable(tableModel);
        tableScrollPane = new JScrollPane(taskTable);
        tableScrollPane = new JScrollPane(taskTable);
        addButton = new JButton("Add");
        addListener();
        updateButton = new JButton("Update");
        updateListener();
        deleteButton = new JButton("Delete");
        deleteListener();
    }

    //we studied the following source to implement the menu bar:
    //https://www.geeksforgeeks.org/java-swing-jmenubar/
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu add = new JMenu("Add");
        JMenu editMenu = new JMenu("Edit");
        JMenuItem addItem = new JMenuItem("Add");
        JMenuItem update = new JMenuItem("Update");
        JMenuItem delete = new JMenuItem("Delete");

        addItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddTaskFrame addTaskFrame = new AddTaskFrame(tableModel);
            }
        });

        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    String clickedRowValue = (String) taskTable.getValueAt(selectedRow, 0);
                    Object dateObject = taskTable.getValueAt(selectedRow, 1);
                    Date clickedRowValue1 = null;

                    if (dateObject instanceof Date) {
                        clickedRowValue1 = (Date) dateObject;
                    } else if (dateObject instanceof String) {
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date parsedDate = dateFormat.parse((String) dateObject);
                            clickedRowValue1 = new Date(parsedDate.getTime());
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                    }
    
                    if (clickedRowValue1 != null) {
                        UpdateTaskFrame updateTaskFrame = new UpdateTaskFrame(tableModel, taskTable, clickedRowValue, clickedRowValue1);
                    }
                }
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteTaskFrame.showDialog(tableModel, taskTable);
            }
        });
        add.add(addItem);
        editMenu.add(update);
        editMenu.add(delete);
        menuBar.add(add);
        menuBar.add(editMenu);
        setJMenuBar(menuBar);
    }

    private void deleteListener() {
        ActionListener d = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTaskFrame.showDialog(tableModel, taskTable);
            }
        };
        deleteButton.addActionListener(d);
    }
    private void updateListener() {
        ActionListener u = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow != -1) {
                    String clickedRowValue = (String) taskTable.getValueAt(selectedRow, 0);
                    Object dateObject = taskTable.getValueAt(selectedRow, 1);
                    Date clickedRowValue1 = null;

                    if (dateObject instanceof Date) {
                        clickedRowValue1 = (Date) dateObject;
                    } else if (dateObject instanceof String) {
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date parsedDate = dateFormat.parse((String) dateObject);
                            clickedRowValue1 = new Date(parsedDate.getTime());
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                    }
    
                    if (clickedRowValue1 != null) { // Check if the date is not null
                        UpdateTaskFrame updateTaskFrame = new UpdateTaskFrame(tableModel, taskTable, clickedRowValue, clickedRowValue1);
                    }
                }
            }
        };
        updateButton.addActionListener(u);
    }
    
    private void addListener(){
        ActionListener a = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AddTaskFrame addTaskFrame = new AddTaskFrame(tableModel);
            }
            
        };
        addButton.addActionListener(a);
    }

    private void createLayout() {
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(tableScrollPane)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addComponent(updateButton)
                        .addComponent(deleteButton)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(tableScrollPane)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(addButton)
                        .addComponent(updateButton)
                        .addComponent(deleteButton)));

        getContentPane().add(panel);
    }
}