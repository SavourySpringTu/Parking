package About;

public class Check {
    public boolean checkString(String id){
        String tmp = id.replaceAll("\\s", "");
        if(tmp.equals("")==true){
            return false;
        }else {
            return true;
        }
    }

}
