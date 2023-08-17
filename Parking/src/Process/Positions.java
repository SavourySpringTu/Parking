package Process;

import Connection.ConnectionSQL;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Positions {
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private Check check = new Check();
    // =========================== ADD ============================
    public void addPosition(boolean type,String camera) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "INSERT INTO positions(type,camera,status) VALUES (?,?,?)");
        pstmt.setBoolean(1, type);
        pstmt.setString(2, camera);
        Boolean status = false;
        pstmt.setBoolean(3, status);
        pstmt.executeUpdate();
        pstmt.close();
        JOptionPane.showMessageDialog(null, "Thành công!");
    }
    // ======================== UPDATE ===============================
    public void updatePosition(String id,boolean type,String camera) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "SELECT status FROM positions WHERE id=?");
        pstmt.setInt(1, Integer.parseInt(id));
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        if(rs.getBoolean("status")==true)
        {
            JOptionPane.showMessageDialog(null, "Không thể sửa!");
            return;
        }
        pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "UPDATE positions SET camera=?,type=? WHERE id=?");
        pstmt.close();
        pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "UPDATE positions SET camera=?,type=? WHERE id=?");
        pstmt.setBoolean(2, type);
        pstmt.setString(1, camera);
        pstmt.setInt(3, Integer.parseInt(id));
        pstmt.executeUpdate();
        pstmt.close();
        JOptionPane.showMessageDialog(null, "Thành công!");
    }
    public void updateInsertandExporttVehicle(int id,boolean status) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "UPDATE positions SET status=? WHERE id=?");
        pstmt.setBoolean(1, status);
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
        pstmt.close();
    }
    // ========================== DELETE =============================
    public void deletePosition(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "SELECT status FROM positions WHERE id=?");
        pstmt.setInt(1, Integer.parseInt(id));
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        if(rs.getBoolean("status")==true)
        {
            JOptionPane.showMessageDialog(null, "Không thể xóa!");
            return;
        }
        pstmt.close();
        pstmt = connectionSQL.ConnectionSQL().prepareStatement("DELETE FROM positions WHERE id=?");
        pstmt.setInt(1, Integer.parseInt(id));
        pstmt.executeUpdate();
        JOptionPane.showMessageDialog(null, "Thành công!");
    }
    // =================== =======================
    public int findPositionbyTicket(int id) throws SQLException, ClassNotFoundException {
        System.out.println(id);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT id_position FROM ticket WHERE id=?");
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int tmp = rs.getInt("id_position");
        pstmt.close();
        return tmp;
    }
    public boolean checkPosition(int id,boolean typevh) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT type,status FROM positions WHERE id=?");
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        System.out.println(typevh);
        if(rs.getBoolean("status")==true || rs.getBoolean("type")!= typevh){
            return false;
        }
        return true;
    }
    public int autoFindPosition(boolean type) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM positions");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            if(rs.getBoolean("type")==type && rs.getBoolean("status")==false){
                return rs.getInt("id");
            }
        }
        return 0;
    }
    public boolean checkChangPosition(int id_ticket,int id_ps) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT id_position, type_vehicle FROM ticket WHERE id=?");
        pstmt.setInt(1,id_ticket);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        boolean a = rs.getBoolean("type_vehicle");
        pstmt.close();
        pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM positions WHERE id=?");
        pstmt.setInt(1,id_ps);
        rs = pstmt.executeQuery();
        rs.next();
        boolean a1 = rs.getBoolean("type");
        if(a!=a1 || rs.getBoolean("status")==true){
            return false;
        }
        return true;
    }
    public String[] countPositionEmpty() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM positions WHERE status=0");
        ResultSet rs = pstmt.executeQuery();
        int a=0; int b=0;
        while(rs.next()){
            if(rs.getBoolean("type")==false){
                a++;
            }else{
                b++;
            }
        }
        String r[]={String.valueOf(a), String.valueOf(b)};
        return r;
    }
}
