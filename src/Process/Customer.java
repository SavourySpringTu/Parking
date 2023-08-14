package Process;

import Connection.ConnectionSQL;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
        if(checkAddCustomer(number)==false){
            JOptionPane.showMessageDialog(null, "Trùng số xe!");
            return 0;
        }
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
        JOptionPane.showMessageDialog(null, "Thành công!");
        return total;
    }
    public boolean checkAddCustomer(String number) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM customer WHERE number_vehicle=?");
        pstmt.setString(1,number);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            if(rs.getBoolean("status")==false){
                return false;
            }
        }
        return true;
    }
    public void updateCustomer(int id,String name,String phone,int day,boolean type,String number,boolean status) throws SQLException, ClassNotFoundException {
        if(checkDeleteUpdateCustomer(id)==false){
            JOptionPane.showMessageDialog(null, "Không thể sửa!");
            return;
        }
        PreparedStatement pstmt =connectionSQL.ConnectionSQL().prepareStatement(
                "UPDATE customer SET name=?,number_phone=?,status=?,number_vehicle=?,type=?,day=? WHERE id=?");
        pstmt.setString(1,name);
        pstmt.setString(2,phone);
        pstmt.setBoolean(3,status);
        pstmt.setString(4,number);
        pstmt.setBoolean(5,type);
        pstmt.setInt(6,day);
        pstmt.setInt(7,id);
        pstmt.executeUpdate();
        pstmt.close();
        JOptionPane.showMessageDialog(null, "Thành công!");
    }
    public void deleteCustomer(int id) throws SQLException, ClassNotFoundException {
        if(checkDeleteUpdateCustomer(id)==false){
            JOptionPane.showMessageDialog(null, "Không thể xóa!");
            return;
        }
        PreparedStatement pstmt =connectionSQL.ConnectionSQL().prepareStatement("DELETE FROM customer WHERE id=?");
        pstmt.setInt(1,id);
        pstmt.executeUpdate();
        pstmt.close();
        JOptionPane.showMessageDialog(null, "Thành công!");
    }
    public boolean checkDeleteUpdateCustomer(int id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt =connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM ticket WHERE id_customer=?");
        pstmt.setInt(1,id);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()==true){
            System.out.println("false");
            return false;
        }
        pstmt.close();
        System.out.println("true");
        return true;
    }
    public boolean checkTicketofCustomer(int id,int a) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt =connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM customer WHERE id=?");
        pstmt.setInt(1,id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        if(a>=rs.getInt("day")) {
            updateCustomerStatus(rs.getInt("id"));
            return true;
        }
        return false;
    }
    public void checkCustomerStatus() throws SQLException, ClassNotFoundException {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PreparedStatement pstmt =connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM customer");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            String tu = rs.getString("time");
            LocalDateTime time = LocalDateTime.parse(tu, formatter);
            int a = (int) ChronoUnit.DAYS.between(time,date);
            if(a>=rs.getInt("day")) {
                updateCustomerStatus(rs.getInt("id"));
            }
        }
    }
    public void updateCustomerStatus(int id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt1 =connectionSQL.ConnectionSQL().prepareStatement("UPDATE customer SET status=? WHERE id=?");
        pstmt1.setBoolean(1,true);
        pstmt1.setInt(2,id);
        pstmt1.executeUpdate();
        pstmt1.close();
    }
}

