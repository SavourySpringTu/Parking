package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQL {
    //private static Connection connection=null;
    public Connection ConnectionSQL() throws ClassNotFoundException, SQLException {
        try{
            String connectionURL="jdbc:mysql://localhost:3306/parking";
            Connection connection = DriverManager.getConnection(connectionURL, "root", "111101");
            System.out.println("Kết nối sql thành công!");
            return connection;
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        };
        return null;
    }
}
