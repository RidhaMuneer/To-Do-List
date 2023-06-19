package Main;

//Ridha Muner Kamil(rm20347@auis.edu.krd)
//Asaad Thiyab Issa(at20134@auis.edu.krd)

//you can enter the url, username, and password of your local database at the DBConnection class in the Database package.

import java.sql.SQLException;

import Database.DBConnection;
import GUI.GUI;

public class Main{
    public static void main(String[] args) throws SQLException {
        DBConnection connection = new DBConnection();
        GUI Main_Frame = new GUI("TO-DO List");
    }
}