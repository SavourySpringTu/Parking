package Process;

import Connection.ConnectionSQL;
import com.google.zxing.NotFoundException;

import javax.swing.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Warehouse {
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private Ticket ticket = new Ticket();
    private Customer customer = new Customer();
    public void scanTicketaddWarehouse() throws SQLException, ClassNotFoundException, NotFoundException, IOException {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "SELECT * FROM ticket");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            if(rs.getObject("timeout")==null) {
                String tu = rs.getString("timein");
                LocalDateTime time = LocalDateTime.parse(tu, formatter);
                int a = (int) ChronoUnit.DAYS.between(time,date);
                if(a>0){
                    if(rs.getString("id_customer")==null) {
                        System.out.println("null ne");
                        addWarehouse(rs.getInt("id"));
                        ticket.exportTicket(rs.getInt("id"));
                    } else {
                        if(customer.checkTicketofCustomer(rs.getInt("id_customer"),a)==true){
                            addWarehouse(rs.getInt("id"));
                            ticket.exportTicket(rs.getInt("id"));
                        }
                    }
                }
            }
        }
    }
    public void addWarehouse(int id) throws SQLException, ClassNotFoundException {
        LocalDateTime time = LocalDateTime.now();
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("INSERT INTO warehouse(timein,id_ticket,status) VALUES (?,?,?)");
        pstmt.setObject(1,time);
        pstmt.setInt(2,id);
        pstmt.setBoolean(3,false);
        pstmt.executeUpdate();
        pstmt.close();
    }
    public void exportWarehouse(int id) throws SQLException, ClassNotFoundException {
        LocalDateTime time = LocalDateTime.now();
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM warehouse WHERE id=?");
        pstmt.setInt(1,id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        if(rs.getBoolean("status")==true){
            JOptionPane.showMessageDialog(null, "Đã xuất!");
            return;
        }
        pstmt.close();
        PreparedStatement pstmt1 = connectionSQL.ConnectionSQL().prepareStatement("UPDATE warehouse SET timeout=?,status=? WHERE id=?");
        pstmt1.setObject(1,time);
        pstmt1.setBoolean(2,true);
        pstmt1.setInt(3,id);
        pstmt1.executeUpdate();
        pstmt1.close();

    }
}
