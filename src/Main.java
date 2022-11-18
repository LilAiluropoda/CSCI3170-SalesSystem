import java.sql.*;
import java.util.Scanner;

public class Main {
    public static String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db14?autoReconnect=true&useSSL=false";
    public static String dbUsername = "Group14";
    public static String dbPassword = "CSCI3170";

    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
        System.out.println("Connection Established successfully");

        Statement st = con.createStatement();
//        st.executeUpdate("CREATE TABLE Sailors" +
//                "(sid integer, name varchar(32)," +
//                "rating integer, age float)");
//        st.executeUpdate("INSERT INTO Sailors VALUES ( 22, 'Dustin', 7, 45.0 )");
//        st.executeUpdate("INSERT INTO Sailors VALUES ( 25, 'Smith', 8, 50.0 )");
//        String query = "SELECT sname, rating FROM Sailors";
//        ResultSet rs = st.executeQuery(query);
//
//        while(rs.next()){
//            String s = rs.getString("sname");
//            int n = rs.getInt("rating");
//            System.out.println(s + " " + n);
//        }


        String[] user = {
                "administrator",
                "salesperson",
                "manager"
        };
        System.out.println("-----Main menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        for (int i = 0; i < 3; i++){
            System.out.println((i+1) + ". Operations for " + user[i]);
        }
//        System.out.println("1. Operations for " + user[0]);
//        System.out.println("2. Operations for salesperson");
//        System.out.println("3. Operations for manager");
        System.out.println("4. Exit this program");
        System.out.print("Enter Your Choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        if(choice == 4){
            System.exit(0);
        }
        System.out.println("-----Operations for " + user[choice-1] + " menu -----");
    }
}

