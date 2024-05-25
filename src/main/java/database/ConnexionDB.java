package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public  class ConnexionDB {
    public static Connection Connection() {
        return null;
    }

    public Connection Connect(){
        try {

            String url = "jdbc:mysql://localhost:3308/projekti";
            String username = "gresah";
            String password = "saythename17";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn= DriverManager.getConnection(url,username,password);
            return conn;
        }
        catch (ClassNotFoundException | SQLException ex){
            Logger.getLogger(ConnexionDB.class.getName()).log(Level.SEVERE,null,ex);

        }
        return null;
}

}