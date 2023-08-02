package Process;

import Connection.ConnectionSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Images {
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private Check check = new Check();
    public String[] findPathImagebyIdTicket(int id) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT url,type FROM image WHERE id_ticket=?");
        pstmt.setInt(1,id);
        ResultSet rs = pstmt.executeQuery();
        String a[]={"",""};
        int i=0;
        while(rs.next()){
            a[i]=rs.getString("url");
            System.out.println(a[i]);
            i++;
        }
        return a;
    }
    public void addImage(int id_ticket,String path,boolean type) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("INSERT INTO image(url,id_ticket,type,status) VALUES (?,?,?,?)");
        pstmt.setString(1,path);
        pstmt.setInt(2,id_ticket);
        pstmt.setBoolean(3,type);
        pstmt.setBoolean(4,false);
        pstmt.executeUpdate();
        pstmt.close();
    }
    public void updateImage(int id_ticket,String path,boolean type) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("UPDATE image SET url=? WHERE id_ticket=? AND type =? AND status='0'");
        pstmt.setString(1,path);
        pstmt.setInt(2,id_ticket);
        pstmt.setBoolean(3,type);
        pstmt.executeUpdate();
        pstmt.close();
    }
    public void deleteImage(int id_ticket) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("DELETE FROM image WHERE id_ticket=? AND status='0'");
        pstmt.setInt(1,id_ticket);
        pstmt.executeUpdate();
        pstmt.close();
    }
}
