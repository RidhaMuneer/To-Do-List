package Main;

//you can enter the URL, username, and password of your local database at the DBConnection class in the Database package.

import java.sql.SQLException;

import Database.DBConnection;
import GUI.GUI;

public class Main{
    public static void main(String[] args) throws SQLException {
        DBConnection connection = new DBConnection();
        GUI Main_Frame = new GUI("TO-DO List");
    }
}
