package Process;

import Connection.ConnectionSQL;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Role {
    private Check check = new Check();
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    public void addRole(String id,String name) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("INSERT INTO role(id,name) VALUES (?,?)");
        pstmt.setString(1,id);
        pstmt.setString(2,name);
        pstmt.executeUpdate();
        pstmt.close();
        JOptionPane.showMessageDialog(null, "Thành Công!");
    }
    public void updateRole(String id, String name) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("UPDATE role SET name=? WHERE id=?");
        pstmt.setString(1, name);
        pstmt.setString(2, id);
        pstmt.executeUpdate();
        pstmt.close();
        JOptionPane.showMessageDialog(null, "Thành Công!");
    }

    // ======================= DELETE ROLE =====================
    public void deleteRole(String id) throws SQLException, ClassNotFoundException {
        if(check.checkString(id)==true) {
            PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("DELETE FROM role WHERE id=?");
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            JOptionPane.showMessageDialog(null, "Thành Công!");
        }else{
            // notice
        }
    }
}
