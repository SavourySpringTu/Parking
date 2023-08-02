package Process;

import Connection.ConnectionSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Customer {
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private TypeTicket typeTicket = new TypeTicket();

    public int findCustomerbyNumber(String number) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM customer");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            if (rs.getBoolean("status") == true) {
                return 0;
            }
            if (rs.getString("number_vehicle").equals(number) == true) {
                return rs.getInt("id");
            }
        }
        return 0;
    }

    public int insetCustomer(String name, int day, String phone, String number, boolean type) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "INSERT INTO customer(name,time,day,number_phone,number_vehicle,type,status) VALUES (?,?,?,?,?,?,?)");
        pstmt.setString(1, name);
        LocalDateTime time = LocalDateTime.now();
        pstmt.setObject(2, time);
        pstmt.setInt(3, day);
        pstmt.setString(4, phone);
        pstmt.setString(5, number);
        pstmt.setBoolean(6, type);
        pstmt.setBoolean(7, false);
        pstmt.executeUpdate();
        pstmt.close();
        if (type == false) {
            pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                    "SELECT price FROM type WHERE id = 'T01'");
        } else {
            pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT price FROM type WHERE id = 'T02'");
        }
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int total = (rs.getInt("price"))*day;
        return total;
    }
}

