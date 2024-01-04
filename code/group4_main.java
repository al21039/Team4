import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class group4_main {
    public static void main(String[] args) {
        try {
            // PostgreSQLでのデータベースへの接続
            String server = "//tokushima.data.ise.shibaura-it.ac.jp:5432/";
            String database = "team4";
            String url = "jdbc:postgresql:" + server + database;
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, "al21014", "bond");
            Statement stmt = con.createStatement();
            Scanner sc = new Scanner(System.in);
            String regex = "^[A-Za-z0-9+$]";
            System.out.println("1:Login, 2:Sign Up");
            System.out.println("Enter 1 or 2");
            String userId, pass;
            String userStatus = sc.nextLine();

            while (true) {
                // ログインを行う
                if (userStatus.equals("1")) {
                    while (true) {
                        System.out.println("-----Login-----");
                        System.out.println("Input Your User ID >");
                        userId = sc.nextLine();
                        String sql = "select * from user_table where user_id = " + userId;
                        ResultSet rs = stmt.executeQuery(sql);
                        // ユーザIDが登録されているものか判別
                        if (!rs.next()) {
                            System.out.println("User ID not Exists");
                            continue;
                        }
                        String userPass = rs.getString("passward");
                        while (true) {
                            System.out.println("Input Your Password");
                            pass = sc.nextLine();
                            // 登録されているパスワードと同じか判別
                            if (userPass.equals(pass)) {
                                System.out.println("Login Successful");
                                break;
                            } else
                                System.out.println("Wrong Password");
                        }
                    }
                }
                // ユーザ登録を行う
                if (userStatus.equals("2")) {
                    while (true) {
                        System.out.println("-----Sign Up-----");
                        System.out.println("登録したいIDを入力して下さい(英数字10文字まで) >");
                        userId = sc.nextLine();

                        // 入力されたユーザIDが英数字のみかどうか判別
                        if (check(regex, userId, 10) == true) {
                            String sql = "select * from user_table where" + userId;
                            ResultSet rs = stmt.executeQuery(sql);
                            // 入力したユーザIDがすでに使われているか判別
                            if (rs.next()) {
                                System.out.println("User ID already exists");
                            }
                            break;
                        } else
                            System.out.println("Enter Correctly");
                    }
                    while (true) {
                        System.out.println("パスワードを決めてください(英数字20文字まで) >");
                        pass = sc.nextLine();
                        if (check(regex, pass, 20) == true)
                            break;
                        else
                            System.out.println("Enter Correctly");
                    }
                    String sql = "insert into user_table";
                    sql += "values";
                    sql += "('" + userId + "','" + pass + "','0')";
                    stmt.executeQuery(sql);
                    break;
                }
            }

            System.out.println(
                    "This is flea market app.\nPlease enter some command.\nIf you want a hint, type '!help'. ");

            while (true) {
                String input = sc.nextLine();
                char char1 = input.charAt(0);
                if (char1 != '!') {
                    System.out.println("When entering a command, the first character is '!' as the first character.");
                }

                // 商品の出品
                if ("!listing".equals(input)) {
                    System.out.println("Input your merchandise");
                    String merchandise = sc.nextLine();
                    System.out.println("Input Selling price");
                    String price = sc.nextLine();
                    System.out.println("Input merchandise comment (No need to input)");
                    String comment = sc.nextLine();
                    LocalDateTime nowDate = LocalDateTime.now();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                    String listing_date = dtf.format(nowDate);
                    String sql = "select count(*) as cnt from listing";
                    ResultSet rs = stmt.executeQuery(sql);
                    String count = rs.getString("cnt");
                    int numberOfMerchandise = Integer.parseInt(count);
                    numberOfMerchandise++;
                    sql = "insert into user_table(listing_code,seller,merchandise,price,comment,listing_date)";
                    sql += "values";
                    sql += "('" + String.format("%08d", numberOfMerchandise) + "','" + userId + "','" + merchandise
                            + "','" + price + "','" + comment + "','" + listing_date + "')";
                    stmt.executeQuery(sql);
                }

                // ユーザの出品状況の確認
                if ("!lhis".equals(input)) {
                    String sql = "select listing_code, merchandise, price, listing_date from listing where seller = "
                            + userId;
                    ResultSet rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        System.out.println(rs.getString("listing_code") + "\t" + rs.getString("merchandise") + "\t"
                                + rs.getInt("price") + "\t" + rs.getString("listing_date"));
                    }
                }

                if ("!exit".equals(input)) {
                    break;
                }

            }
            sc.close();
            stmt.close();
            con.close();
        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean check(String regex, String text, int length) {
        boolean result = true;
        if (text == null || text.isEmpty() || text.length() > length)
            return false;
        Pattern ptn = Pattern.compile(regex);
        Matcher mtr = ptn.matcher(text);
        result = mtr.matches();
        return result;
    }
}
