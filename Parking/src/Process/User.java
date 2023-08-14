package Process;

import Connection.ConnectionSQL;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private Check check = new Check();
    public void addUser(String id,String name,String password,String role,boolean status) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("INSERT INTO user(username,name,password,id_role,status) VALUES (?,?,?,?,?)");
        pstmt.setString(1,id);
        pstmt.setString(2,name);
        pstmt.setString(3,password);
        pstmt.setString(4,role);
        pstmt.setBoolean(5,status);
        pstmt.executeUpdate();
        pstmt.close();
        JOptionPane.showMessageDialog(null, "Thành công!");
    }
    // ===================== UPDATE USER ======================
    public void updateUser(String username,String name,String password,String role,boolean status) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("UPDATE user SET name=?,password=?,id_role=?,status=? WHERE username=?");
        pstmt.setString(1, name);
        pstmt.setString(2, password);
        pstmt.setString(3, role);
        pstmt.setBoolean(4,status);
        pstmt.setString(5, username);
        pstmt.executeUpdate();
        pstmt.close();
        JOptionPane.showMessageDialog(null, "Thành công!");
    }

    //======================== DELETE USER =======================
    public void deleteUser(String id) throws SQLException, ClassNotFoundException {
        if(checkDeleteUser(id)==false){
            JOptionPane.showMessageDialog(null, "Không thể xóa!");
            return;
        }
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "DELETE FROM user WHERE id=?");
        pstmt.setString(1, id);
        pstmt.executeUpdate();
        pstmt.close();
        JOptionPane.showMessageDialog(null, "Thành công!");
    }
    // =================== Login  ===================
    public String[] login(String user,String password) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "SELECT username,password,id_role FROM user WHERE status=0");
        ResultSet rs = pstmt.executeQuery();
        String resul[]={"",""};
        while(rs.next()){
            if(rs.getString("username").equals(user)==true && rs.getString("password").equals(password)==true){
                resul[0] = rs.getString("username");
                resul[1] = rs.getString("id_role");
                return resul;
            }
        }
        return null;
    }
    public boolean checkDeleteUser(String user) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "SELECT * FROM ticket");
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()){
            if(rs.getString("id_user").equals(user)==true){
                return false;
            }
        }
        return true;
    }
}
