package Process;

import Connection.ConnectionSQL;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;

public class TypeTicket {
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    public String findTypebyTime(LocalDateTime datetime) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM type");
        ResultSet rs = pstmt.executeQuery();
        LocalTime time = datetime.toLocalTime();
        while(rs.next()){
            if(rs.getTime("timestart")==null)
            {

            }
            else if(rs.getTime("timestart").toLocalTime().compareTo(time)<=0 && rs.getTime("timeend").toLocalTime().compareTo(time)>0){
                return rs.getString("id");
            }
        }
        return null;
    }
}
