import java.nio.charset.Charset;
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
            String user = "al21039";
            String password = "bond";
            Connection con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
            Statement stmt = con.createStatement();
            Scanner sc = new Scanner(System.in);
            ResultSet rs;
            String regex = "^[A-Za-z0-9]+$";
            System.out.println("1:Login, 2:Sign Up");
            System.out.println("Enter 1 or 2");
            String userId, pass;
            int counter = 0;
            String userStatus = sc.nextLine();

            while (true) {
                // ログインを行う
                if (userStatus.equals("1")) {
                    while (true) {
                        System.out.println("-----Login-----");
                        System.out.println("Input Your User ID >");
                        userId = sc.nextLine();
                        String sql = "select * from user_table where user_id like '" + userId + "'";
                        rs = stmt.executeQuery(sql);
                        // ユーザIDが登録されているものか判別
                        if (!rs.next()) {
                            System.out.println("User ID not Exists");
                            continue;
                        }
                        String userPass = rs.getString("password");
                        System.out.println("Input Your Password");
                        pass = sc.nextLine();
                        // 登録されているパスワードと同じか判別
                        if (userPass.equals(pass)) {
                            System.out.println("Login Successful");
                            break;
                        } else {
                            System.out.println("Wrong Password");
                        }
                    }
                    break;
                }

                // ユーザ登録を行う
                if (userStatus.equals("2")) {
                    while (true) {
                        System.out.println("-----Sign Up-----");
                        System.out.println("Enter the ID to register(Up to 10 alphanumeric characters) >");
                        userId = sc.nextLine();
                        // 入力されたユーザIDが英数字のみかどうか判別
                        if (check(regex, userId, 10) == true) {
                            String sql = "select * from user_table where user_id like '" + userId + "'";
                            rs = stmt.executeQuery(sql);
                            // 入力したユーザIDがすでに使われているか判別
                            if (rs.next()) {
                                System.out.println("User ID already exists");
                                continue;
                            }
                            break;
                        } else {
                            System.out.println("Enter Correctly");
                        }
                    }
                    while (true) {
                        System.out.println("Choose a password(Up to 20 alphanumeric characters) >");
                        pass = sc.nextLine();
                        if (check(regex, pass, 20) == true)
                            break;
                        else
                            System.out.println("Enter Correctly");
                    }
                    String sql = "insert into user_table (user_id, password, money)";
                    sql += " values";
                    sql += "('" + userId + "','" + pass + "', 0)";
                    stmt.executeUpdate(sql);
                    con.commit();

                    break;
                }
            }

            System.out.println(
                    "\nThis is flea market app.\nPlease enter some command.\nIf you want a hint, type '!help'. ");

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
                    System.out.println("Input Selling price (yen)");
                    String price = sc.nextLine();
                    System.out.println("Input merchandise comment (No need to input)");
                    String comment = sc.nextLine();
                    LocalDateTime nowDate = LocalDateTime.now();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                    String listing_date = dtf.format(nowDate);
                    String sql = "select count(*) as cnt from listing";
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        counter = rs.getInt("cnt");
                    }
                    counter++;
                    sql = "insert into listing (listing_code, seller, merchandise, price,comment, listing_date)";
                    sql += "values";
                    sql += "('" + String.format("%08d", counter) + "','" + userId + "','" + merchandise
                            + "'," + price + ",'" + comment + "','" + listing_date + "')";
                    stmt.executeUpdate(sql);
                    con.commit();
                    System.out.println("Listing Successful");
                }

                // ユーザの出品状況の確認
                if ("!lhis".equals(input)) {
                    String sql = "select listing_code, merchandise, price, listing_date from listing where seller like '"
                            + userId + "'";
                    System.out.println(sql);
                    rs = stmt.executeQuery(sql);
                    if (!rs.next()) {
                        System.out.println("you are not listing");
                    } else {
                        rs = stmt.executeQuery(sql);
                        System.out.println(format("code", 8) + " | " + format("merchandise", 50) + " | "
                                + format("price", 12) + " | " + format("listing_date", 16));
                        while (rs.next()) {
                            String code = rs.getString("listing_code");
                            String item = rs.getString("merchandise");
                            String price = rs.getString("price");
                            String date = rs.getString("listing_date");
                            System.out
                                    .println(
                                            code + " | " + format(item, 50) + " | " + format(price, 12) + " | " + date);
                        }
                    }
                }
                // user_idとお金を表示
                if ("!user".equals(input)) {
                    int money = 0;
                    System.out.println("user ID : " + userId);
                    String sql = "select money from user_table where user_id like '" + userId + "'";
                    rs = stmt.executeQuery(sql);
                    while (rs.next()) {
                        money = rs.getInt("money");
                    }
                    System.out.println("money : " + money);
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
        if (text == null || text.isEmpty() || text.length() > length) {
            return false;
        }
        Pattern ptn = Pattern.compile(regex);
        Matcher mtr = ptn.matcher(text);
        result = mtr.matches();
        return result;
    }

    private static String format(String target, int length) {
        int byteDiff = (getByteLength(target, Charset.forName("UTF-8")) - target.length()) / 2;
        return String.format("%-" + (length - byteDiff) + "s", target);
    }

    private static int getByteLength(String string, Charset charset) {
        return string.getBytes(charset).length;
    }
}
