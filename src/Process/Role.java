package Process;

import Connection.ConnectionSQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Role {
    private Check check = new Check();
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    public void addRole(String id,String name) throws SQLException, ClassNotFoundException {
        if(check.checkString(id)==true){
            PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("INSERT INTO role(id,name,status) VALUES (?,?,?)");
            pstmt.setString(1,id);
            pstmt.setString(2,name);
            pstmt.setBoolean(3,false);
            pstmt.executeUpdate();
            pstmt.close();
        }
        else{
            // notice
        }

    }
    public void updateRole(String id, String name,Boolean status) throws SQLException, ClassNotFoundException {
        if(check.checkString(id)==true) {
            PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("UPDATE role SET name=?,status=? WHERE id=?");
            pstmt.setString(1, name);
            pstmt.setString(3, id);
            pstmt.setBoolean(2,status);
            pstmt.executeUpdate();
            pstmt.close();
        } else{
            // notice
        }
    }

    // ======================= DELETE ROLE =====================
    public void deleteRole(String id) throws SQLException, ClassNotFoundException {
        if(check.checkString(id)==true) {
            PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("DELETE FROM role WHERE id=?");
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            pstmt.close();
        }else{
            // notice
        }
    }
}
