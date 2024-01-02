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

            System.out.println("フリーマーケットアプリへようこそ！\nユーザ登録済みの方は「!login」、ユーザ登録をしていない方は「!register」と入力してください。\nコマンド確認は「!help」と入力してください。 ");
            Scanner sc = new Scanner(System.in);
            boolean iscomand = false;
            boolean islogin = false;
            char char1='\0';
            String user = "";
            purchase_action pac = new purchase_action();

            System.out.printf("未ログイン>");

            while(true){
                String input =sc.nextLine();
                iscomand=false;
                if(input!=""){
                    char1= input.charAt(0);
                }

                if(char1!='!'){
                    System.out.println("コマンドの最初の文字は「!」です。コマンド確認は「!help」と入力してください。");
                    iscomand=true;
                }

                if ("!help".equals(input)){

                    System.out.println("helpコマンド");
                    iscomand=true;
                }

                if ("!register".equals(input)){
                    System.out.println("registerコマンド");
                    iscomand=true;
                }

                if ("!login".equals(input)){
                    if(islogin==true){
                        System.out.plintln("ログイン済です");
                    }else{
                        islogin=true;
                        System.out.println("loginコマンド");
                        user="user1";
                        iscomand=true;
                        //ログイン中
                    }
                    
                }

                if ("!listing".equals(input)){
                    if(islogin==true){
                        System.out.println("listingコマンド");
                    }else{
                        System.out.println("先にログインしてください。");
                    }
                    iscomand=true;
                }

                if ("!purchase".equals(input)){
                    if(islogin==true){
                        System.out.println("purchaseコマンド");
                        pac.purchase(stmt);
                    }else{
                        System.out.println("先にログインしてください。");
                    }
                    iscomand=true;
                }

                if ("!money".equals(input)){
                    if(islogin==true){
                        System.out.println("moneyコマンド");
                    }else{
                        System.out.println("先にログインしてください。");
                    }
                    iscomand=true;
                }

                if ("!bmlist".equals(input)){
                    if(islogin==true){
                        System.out.println("bmlistコマンド");
                    }else{
                        System.out.println("先にログインしてください。");
                    }
                    iscomand=true;
                }

                if ("!lhis".equals(input)){
                    if(islogin==true){
                        System.out.println("lhisコマンド");
                    }else{
                        System.out.println("先にログインしてください。");
                    }
                    iscomand=true;
                }

                if ("!phis".equals(input)){
                    if(islogin==true){
                        System.out.println("phisコマンド");
                    }else{
                        System.out.println("先にログインしてください。");
                    }
                    iscomand=true;
                }
                                
                if("!exit".equals(input)){
                    break;
                }

                if(iscomand==false){
                    System.out.println("有効なコマンドではありません。コマンド確認は「!help」と入力してください。");
                }

                if(islogin==true){
                    System.out.printf("%s>",user);
                }else{
                    System.out.printf("未ログイン>");

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
