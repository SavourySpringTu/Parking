package Process;

import Connection.ConnectionSQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Revenue {
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private TypeTicket typeTicket = new TypeTicket();
    public int revenue(LocalDateTime time1, LocalDateTime time2) throws SQLException, ClassNotFoundException {
        int total=0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "SELECT * FROM ticket");
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()){
            String tu = rs.getString("timein");
            LocalDateTime time = LocalDateTime.parse(tu, formatter);
            int n = (int) ChronoUnit.DAYS.between(time1,time);
            int n1 = (int) ChronoUnit.DAYS.between(time2,time);
            if(n>=0 && n1<0){
                if(rs.getString("id_customer")==null ){
                    PreparedStatement pstmt1 = connectionSQL.ConnectionSQL().prepareStatement(
                            "SELECT * FROM type WHERE id=?");
                    pstmt1.setString(1,rs.getString("id_type"));
                    ResultSet rs1 = pstmt1.executeQuery();
                    rs1.next();
                    total = total + rs1.getInt("price");
                }
            }
        }
        rs = pstmt.executeQuery("SELECT * FROM customer");
        while (rs.next()){
            String tu = rs.getString("time");
            LocalDateTime time = LocalDateTime.parse(tu, formatter);
            int n = (int) ChronoUnit.DAYS.between(time1,time);
            int n1 = (int) ChronoUnit.DAYS.between(time2,time);
            if(n>=0 && n1<0){
                total = total + typeTicket.findPricebyCustomer(rs.getBoolean("type"),rs.getInt("day"));
            }
        }
        return total;
    }
}
