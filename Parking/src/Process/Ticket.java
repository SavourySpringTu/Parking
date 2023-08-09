package Process;

import Connection.ConnectionSQL;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import com.google.zxing.*;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Ticket {
    private ConnectionSQL connectionSQL = new ConnectionSQL();
    private Check check = new Check();
    private Images images = new Images();
    private Positions positions = new Positions();
    private TypeTicket typeTicket = new TypeTicket();
    private Customer customer = new Customer();
    // ========================== ADD =============================
    public void addTicket(String number,String type_ticket,boolean type_vehicle,int position,String user,String path,String path1) throws SQLException, ClassNotFoundException {
        if(positions.checkPosition(position,type_vehicle)==false){
            JOptionPane.showMessageDialog(null, "Position Full!");
            return;
        }
        if(checkNumberVehicle(number)==false){
            JOptionPane.showMessageDialog(null, "Number Duplicate!");
            return;
        }
        int tmpCs = customer.findCustomerbyNumber(number);
        if(tmpCs!=0){
        }else{
            if(type_ticket.equals("T01")||type_ticket.equals("T02")){
                JOptionPane.showMessageDialog(null, "Type Ticket Invalid!");
                return;
            }
        }
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("INSERT INTO ticket(timein,timeout,number_vehicle,id_type,type_vehicle,id_position,id_user,id_warehouse,id_customer) VALUES (?,?,?,?,?,?,?,?,?)");
        LocalDateTime date = LocalDateTime.now();
        pstmt.setObject(1,date);
        pstmt.setObject(2,null);
        pstmt.setString(3,number);
        pstmt.setString(4, type_ticket);
        pstmt.setBoolean(5,type_vehicle);
        pstmt.setInt(6, position);
        pstmt.setString(7,user);
        pstmt.setString(8,null);
        pstmt.setString(9,null);
        pstmt.executeUpdate();
        pstmt.close();
        // ================ UPDATE CUSTOMER & TYPE ==================
        int tmpId = findIdTicketbyNumber(number);
        if(tmpCs!=0){
            pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                    "UPDATE ticket SET id_customer=?,id_type=? WHERE id=?");
            pstmt.setInt(3,tmpId);
            pstmt.setInt(1,tmpCs);
            if(type_vehicle==false){
                pstmt.setString(2,"T01");
            }else{
                pstmt.setString(2,"T02");
            }
            pstmt.executeUpdate();
            pstmt.close();
        }
        positions.updateInsertandExporttVehicle(position,true);
        images.addImage(tmpId,path,false);
        images.addImage(tmpId,path1,true);
        JOptionPane.showMessageDialog(null, "Insert Success");
    }

    // ====================== UPDATE ===========================
    public void updateTicket(int id,String number,String type_ticket,int position,String path,String path1) throws SQLException, ClassNotFoundException {
        if(cannotChangeType(id,type_ticket)==false)
        {
            JOptionPane.showMessageDialog(null, "Cannot Change Type Ticket!");
            return;
        }
        if(positions.checkChangPosition(id,position)==false){
            JOptionPane.showMessageDialog(null, "Cannot Change Position!");
            return;
        }
        int x = positions.findPositionbyTicket(id);
        positions.updateInsertandExporttVehicle(x,false);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "UPDATE ticket SET number_vehicle=?,id_type=?,id_position=? WHERE id=?");
        pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "UPDATE ticket SET number_vehicle=?,id_type=?,id_position=? WHERE id=?");
        pstmt.setString(1,number);
        pstmt.setString(2, type_ticket);
        pstmt.setInt(3, position);
        pstmt.setInt(4, id);
        pstmt.executeUpdate();
        pstmt.close();
        positions.updateInsertandExporttVehicle(position,true);
        images.updateImage(id,path,false);
        images.updateImage(id,path1,true);
        JOptionPane.showMessageDialog(null, "Update Success");
    }
    public void deleteTicket(int id) throws SQLException, ClassNotFoundException {
        int tmp = positions.findPositionbyTicket(id);
        images.deleteImage(id);
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "DELETE FROM ticket WHERE id=?");
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
        pstmt.close();
        positions.updateInsertandExporttVehicle(tmp,false);
        images.deleteImage(id);
        JOptionPane.showMessageDialog(null, "Delete Success");
    }

    public int findIdTicketbyNumber(String number) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "SELECT id FROM ticket WHERE number_vehicle=? AND timeout IS NULL");
        pstmt.setString(1,number);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        return rs.getInt("id");
    }
    public String[] insertTicket(String number,Boolean typeVehicle,String user,String path,String path1) throws SQLException, ClassNotFoundException {
        if(checkNumberVehicle(number)==false){
            JOptionPane.showMessageDialog(null, "Number Duplicate!");
            return null;
        }
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "INSERT INTO ticket(timein,timeout,number_vehicle,type_vehicle,id_type,id_position,id_user) VALUES (?,?,?,?,?,?,?)");
        LocalDateTime time = LocalDateTime.now();
        pstmt.setObject(1,time);
        pstmt.setObject(2,null);
        pstmt.setString(3,number);
        pstmt.setBoolean(4,typeVehicle);
        pstmt.setString(5,typeTicket.findTypebyTime(time));
        int p = positions.autoFindPosition(typeVehicle);
        if(p==0){
            JOptionPane.showMessageDialog(null, "Full!");
            return null;
        }
        pstmt.setInt(6,p);
        pstmt.setString(7,user);
        pstmt.executeUpdate();
        pstmt.close();

        // ================ UPDATE CUSTOMER & TYPE ==================
        int tmpId = findIdTicketbyNumber(number);
        int tmpCs = customer.findCustomerbyNumber(number);
        if(tmpCs!=0){
            pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                    "UPDATE ticket SET id_customer=?,id_type=? WHERE id=?");
            pstmt.setInt(3,tmpId);
            pstmt.setInt(1,tmpCs);
            if(typeVehicle==false){
                pstmt.setString(2,"T01");
            }else{
                pstmt.setString(2,"T02");
            }
            pstmt.executeUpdate();
            pstmt.close();
        }
        images.addImage(tmpId,path,false);
        images.addImage(tmpId,path1,true);
        positions.updateInsertandExporttVehicle(p,true);

        // ==================== RETURN DATA =====================
        pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT id_type,id_position,id_customer FROM ticket WHERE id=?");
        pstmt.setInt(1,tmpId);
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        String a1 = String.valueOf(rs.getInt("id_customer"));
        String a2 = String.valueOf(rs.getInt("id_position"));
        String t = rs.getString("id_type");
        pstmt.close();
        pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT price FROM type WHERE id=?");
        pstmt.setString(1,t);
        ResultSet rs1 = pstmt.executeQuery();
        rs1.next();
        String a3 = "";
        if(tmpCs!=0){
            a3 = "";
        }else{
            a3 = String.valueOf(rs1.getInt("price"));
        }
        pstmt.close();
        String a[]={a1,a2,a3, String.valueOf(tmpId)};
        return a;
    }
    // ================= CHECK NUMBER AND TYPE VEHICLE OF CUSTOMER=============

    // ================= EXPORT TICKET =====================
    public void exportTicket(int id) throws IOException, NotFoundException, SQLException, ClassNotFoundException {
        // ============== update time out =======================
        System.out.println("ex:"+id);
        LocalDateTime o = LocalDateTime.now();
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("UPDATE ticket SET timeout=? WHERE id =?");
        pstmt.setObject(1,o);
        pstmt.setInt(2,id);
        pstmt.executeUpdate();
        pstmt.close();
        int ps = positions.findPositionbyTicket(id);
        pstmt = connectionSQL.ConnectionSQL().prepareStatement("UPDATE positions SET status=0 WHERE id=?");
        pstmt.setInt(1,ps);
        pstmt.executeUpdate();
    }
    public String[] loadImageByQR(String path,boolean type) throws NotFoundException, IOException, SQLException, ClassNotFoundException {
        int id = Integer.parseInt(readQR(path));
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement("SELECT * FROM ticket WHERE id=?");
        pstmt.setInt(1,id);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()==false||rs.getBoolean("type_vehicle")!=type){
            String a[]={"","","","","",""};
            JOptionPane.showMessageDialog(null, "QR Invalid!");
            return a;
        }
        LocalDateTime time = (LocalDateTime) rs.getObject("timeout");
        if(time==null){
            String a = rs.getString("number_vehicle");
            LocalDateTime lc = (LocalDateTime) rs.getObject("timein");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String a1 = lc.format(dateTimeFormatter);
            String a2 = String.valueOf(rs.getInt("id_customer"));
            pstmt.close();
            String im[] = images.findPathImagebyIdTicket(id);
            String a3 = im[0];
            String a4 = im[1];
            String re[]={a,a1,a2,a3,a4, String.valueOf(id)};
            return re;
        } else{
            JOptionPane.showMessageDialog(null, "Did Export!");
            return null;
        }
    }
    public String readQR(String path) throws FileNotFoundException, IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(path)))));
        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }
    // ================ CHECK NUMBER VEHICLE ===============
    public boolean checkNumberVehicle(String number) throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "SELECT * FROM ticket WHERE number_vehicle=? AND timeout IS NULL ");
        pstmt.setString(1,number);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()==false){
            return true;
        }
        return false;
    }
    // =====================================================
    public boolean cannotChangeType(int id,String type) throws SQLException, ClassNotFoundException {
        if(type=="T01" || type=="T02"){
            return false;
        }
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "SELECT * FROM ticket WHERE id=? AND (id_type=? OR id_type=?)");
        pstmt.setInt(1,id);
        pstmt.setString(2,"T01");
        pstmt.setString(3,"T02");
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()==false){
            return true;
        }
        return false;
    }
    //====================================================
    public int countTicket() throws SQLException, ClassNotFoundException {
        PreparedStatement pstmt = connectionSQL.ConnectionSQL().prepareStatement(
                "SELECT * FROM ticket");
        ResultSet rs = pstmt.executeQuery();
        int i=1;
        while(rs.next()){
            i++;
        }
        return i;
    }

    // =================== CREATE QR =======================
    public String createQR(int id) throws IOException, WriterException {
        String data = String.valueOf(id);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix matrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
        String outputFile = "src\\ImageQR\\"+id +".png";
        Path path = FileSystems.getDefault().getPath(outputFile);
        MatrixToImageWriter.writeToFile(matrix, "PNG", path.toFile());
        return outputFile;
    }
}
