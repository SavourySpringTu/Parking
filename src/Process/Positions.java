package Process;

import Connection.ConnectionSQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Positions {
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private Check check = new Check();
    // =========================== ADD ============================
    public void addPosition(boolean type,String camera) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("INSERT positions(type,camera,status) VALUES (?,?,?)");
        pstmt.setBoolean(1, type);
        pstmt.setString(2, camera);
        Boolean status = false;
        pstmt.setBoolean(3, status);
        pstmt.executeUpdate();
        pstmt.close();
    }
    // ======================== UPDATE ===============================
    public void updatePosition(String id,boolean type,String camera) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("UPDATE positions SET camera=?,type=? WHERE id=?");
        pstmt.setBoolean(2, type);
        pstmt.setString(1, camera);
        pstmt.setInt(3, Integer.parseInt(id));
        pstmt.executeUpdate();
        pstmt.close();
    }
    // ========================== DELETE =============================
    public void deletePosition(String id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("DELETE FROM positions WHERE id=?");
        pstmt.setInt(1, Integer.parseInt(id));
        pstmt.executeUpdate();
        pstmt.close();
    }
}
