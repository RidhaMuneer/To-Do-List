package Frames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Database.DBConnection;

public class DeleteTaskFrame {

    public static void showDialog(DefaultTableModel tableModel, JTable taskTable) {
        int result = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to delete the selected task?", "Delete Task", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            int selectedRow = taskTable.getSelectedRow();
            if (selectedRow != -1) {
                String clickedRowValue = (String) taskTable.getValueAt(selectedRow, 0);
                DBConnection connection = new DBConnection();
                int id = connection.getTheID(clickedRowValue);
                if (id != -1) {
                    connection.deleteTaskById(id);
                    tableModel.removeRow(selectedRow);
                }
            }
        }
    }
}
