import java.sql.*;
public class testdb {
    public static void main(String[] args) {
        try {
            // PostgreSQLでのデータベースへの接続
            String server = "//tokushima.data.ise.shibaura-it.ac.jp:5432/";
            String database = "team4";
            String url="jdbc:postgresql:" + server + database;
            Class.forName("org.postgresql.Driver"); 
            Connection con =DriverManager.getConnection(url,"al21014","bond");
            Statement stmt = con.createStatement(); 
            String sql = "select * from user_table";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
            String sid = rs.getString("user_id");
            String spassword = rs.getString("password");
            String smoney = rs.getString("money");
            System.out.println(sid+" "+spassword +" "+smoney);
            }
        // 終了処理
        stmt.close();
        con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}