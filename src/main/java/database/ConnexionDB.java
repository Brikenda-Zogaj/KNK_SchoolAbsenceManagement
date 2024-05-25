package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnexionDB {

    static String driver="com.mysql.cj.jdbc.Driver";

    static String url = "jdbc:mysql://localhost:3308/projekti";
    static String username = "gresah";
    static   String password = "saythename17";



    public static Connection getConnection(){
        Connection connection=null;
        try {
            Class.forName(driver);
            try {
                connection=DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

}
