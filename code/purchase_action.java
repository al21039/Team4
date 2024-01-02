import java.sql.*;
import java.util.*;
public class purchase_action {
    String sql = "";
    String slc="";
    String sseller="";
    String smerchandise="";
    String sprice = "";
    String scomment = "";
    String sld = "";
    String sbuyer = "";
    String spd="";

    public void purchase(Statement stmt){
        try{
            sql="select * from listing";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                slc = rs.getString("listing_code");
                sseller = rs.getString("seller");
                smerchandise = rs.getString("merchandise");
                sprice = rs.getString("price");
                scomment = rs.getString("comment");
                sld = rs.getString("listing_date");
                sbuyer = rs.getString("buyer");
                spd = rs.getString("purchase_date");
                System.out.println(slc+" "+sseller+" "+smerchandise+" "+sprice+" "+scomment+" "+sld+" "+sbuyer+" "+spd);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
