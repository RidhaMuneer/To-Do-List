package Database;

import java.sql.*;
import java.sql.Date;

import javax.swing.table.DefaultTableModel;

public class DBConnection{

    public Connection connection;
    public Statement statement;
    
    public DBConnection(){
        try {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS ITE409.task ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "title VARCHAR(255) NOT NULL,"
                + "due_date DATE NOT NULL"
                + ")";	
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root", "root", "12344321");
            statement = connection.createStatement();
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public PreparedStatement createAddStatement() {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO ITE409.task (title, due_date) VALUES (?, ?)");
            return statement;
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    public void retrieveDataToTable(DefaultTableModel tableModel) throws SQLException{
        String sql = "SELECT title, due_date FROM ITE409.task";
        ResultSet result = statement.executeQuery(sql);
        while(result.next()){
            String title = result.getString("title");
            String dueDate = result.getString("due_date");
            tableModel.addRow(new Object[]{title, dueDate});
        }
    }

    public int getTheID(String taskName) {
        int id = -1;
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id FROM ITE409.task WHERE title = '" + taskName + "';");
            if (rs.next()) {
                id = rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("GetTheID: " + id);
        return id;
    }

    public void deleteTaskById(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM ITE409.task WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTaskById(int id, String title, java.util.Date dueDate) {
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE ITE409.task SET title = ?, due_date = ? WHERE id = ?");
            stmt.setString(1, title);
            
            Date sqlDueDate = new Date(dueDate.getTime());
            stmt.setDate(2, sqlDueDate);
            
            stmt.setInt(3, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}