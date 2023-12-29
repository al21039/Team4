import java.sql.*;
import java.util.*;
public class group4_main {
    public static void main(String[] args) {
        try {
            // PostgreSQLでのデータベースへの接続
            String server = "//tokushima.data.ise.shibaura-it.ac.jp:5432/";
            String database = "team4";
            String url="jdbc:postgresql:" + server + database;
            Class.forName("org.postgresql.Driver"); 
            Connection con =DriverManager.getConnection(url,"al21014","bond");
            Statement stmt = con.createStatement(); 
            System.out.println("This is flea market app.\nPlease enter some command.\nIf you want a hint, type '!help'. ");
            Scanner sc = new Scanner(System.in);
            while(true){
                String input =sc.nextLine();
                char char1= input.charAt(0);
                if(char1!='!'){
                    System.out.println("When entering a command, the first character is '!' as the first character.");
                }
                
                if("!exit".equals(input)){
                    break;
                }
                
            }
            sc.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
