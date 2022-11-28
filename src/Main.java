import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

// remark: declare the object of 3 person in the beginning to prevent delcaration repeat
public class Main {
    public static String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db14?autoReconnect=true&useSSL=false";
    public static String dbUsername = "Group14";
    public static String dbPassword = "CSCI3170";

    public static String[] user = {
            "administrator",
            "salesperson",
            "manager"
    };

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException, ClassNotFoundException, FileNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
        System.out.println("Connection Established successfully");

        System.out.println("Welcome to sales system!");
        Statement st = con.createStatement();

        loop:
        while(true){
            // print statements
            System.out.println("-----Main menu-----");
            System.out.println("What kinds of operation would you like to perform?");
            for (int i = 0; i < 3; i++){
                System.out.println((i+1) + ". Operations for " + user[i]);
            }
            System.out.println("4. Exit this program");
            System.out.print("Enter Your Choice: ");
            int choice = scanner.nextInt();

            if(choice == 4){
                System.exit(0);
            }
            System.out.println("-----Operations for " + user[choice-1] + " menu -----");
            System.out.println("What kinds of operation would you like to perform?");
            // admin
            if (choice - 1 == 0){
                Administrator admin = new Administrator(con);
                admin.printOperations();
                System.out.print("Enter Your Choice: ");
                int opChoice = scanner.nextInt();
                switch (opChoice) {
                    case 1:
                        admin.createTable();
                        break;
                    case 2:
                        admin.deleteTable();
                        break;
                    case 3:
                        admin.loadData();
                        break;
                    case 4:
                        admin.showContent();
                        break;
                    case 5:
                        continue loop;
                }
            }
            // salesperson
            else if (choice - 1 == 1){
                Salesperson sales = new Salesperson(con);
                sales.print_operation();
                sales.search_for_part();

            }
            // manager
            else if (choice - 1 == 2){
                Manager manager = new Manager();

            }
            else {
                System.out.println("Invalid Input");
            }
//            break;
        }

    }
}

