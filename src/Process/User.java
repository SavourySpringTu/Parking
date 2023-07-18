package Process;

import Connection.ConnectionSQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private Check check = new Check();
    public void addUser(String id,String name,String password,String role) throws SQLException, ClassNotFoundException {
        if(check.checkString(id)==true){
            PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("INSERT INTO user(username,name,password,id_role) VALUES (?,?,?,?)");
            pstmt.setString(1,id);
            pstmt.setString(2,name);
            pstmt.setString(3,password);
            pstmt.setString(4,role);
            pstmt.executeUpdate();
            pstmt.close();
        }
        else{
            // notice
        }

    }
    // ===================== UPDATE USER ======================
    public void updateUser(String id,String name,String password,String role) throws SQLException, ClassNotFoundException {
        if(check.checkString(id)==true) {
            PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("UPDATE user SET name=?,password=?,id_role=? WHERE id=?");
            pstmt.setString(4, id);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
            pstmt.close();
        } else{
            // notice
        }
    }

    //======================== DELETE USER =======================
    public void deleteUser(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("DELETE FROM user WHERE id=?");
        pstmt.setString(1, id);
        pstmt.executeUpdate();
        pstmt.close();
    }
}
