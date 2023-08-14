package Process;

public class Check {
    public boolean checkString(String id){
        String tmp = id.replaceAll("\\s", "");
        if(tmp.equals("")==true){
            return false;
        }else {
            return true;
        }
    }
    public boolean checkInt(String id){
        try{
            int i = Integer.parseInt(id);
            if(i<0){
                return false;
            }
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
