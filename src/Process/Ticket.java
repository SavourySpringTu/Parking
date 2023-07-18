package Process;

import Connection.ConnectionSQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Ticket {
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private Check check = new Check();
    // ========================== ADD =============================
    public void addTicket(String number,String type,String position,String user) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("INSERT INTO ticket(timein,timeout,number_vehicle,id_type,id_position,id_user,id_warehouse,id_customer) VALUES (?,?,?,?,?,?,?,?)");
        LocalDateTime date = LocalDateTime.now();
        pstmt.setObject(1,date);
        pstmt.setObject(2,null);
        pstmt.setString(3,number);
        pstmt.setString(4, type);
        pstmt.setInt(5, Integer.parseInt(position));
        pstmt.setString(6,user);
        pstmt.setString(7,null);
        pstmt.setString(8,null);
        pstmt.executeUpdate();
        pstmt.close();
    }
}
