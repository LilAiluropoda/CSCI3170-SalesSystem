import java.io.FileNotFoundException;
import java.sql.*;
import java.util.*;
// remark: declare the object of 3 person in the beginning to prevent declaration repeat
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
            System.out.println();
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
                sales.decide_operation();

            }
            // manager
            else if (choice - 1 == 2){
                Manager manager = new Manager(con);
                manager.SelectOp();

                Scanner stdin = new Scanner(System.in);
                Boolean flag = true;
                int mchoice = -1;

                do{ //loop if invalid output
                    try{
                        mchoice = stdin.nextInt(); //read input

                    } catch (InputMismatchException ex) { //Catch Exception
                        System.out.println("Invalid Input: Wrong input type");
                        System.out.print("Enter Your Choice: ");
                        stdin.next();
                        continue;

                    }

                    switch (mchoice) {
                        case 1: //ascending order
                            manager.ListSalepersons();
                            flag = false;
                            break;
                        case 2: //descending order
                            manager.CountSalesRecord();
                            flag = false;
                            break;
                        case 3:
                            manager.ShowSalesValue();
                            flag = false;
                            break;
                        case 4:
                            manager.ShowNMostPopularPart();
                            flag = false;
                            break;
                        case 5:
                            continue loop;
                        default:
                            System.out.println("Invalid Input: Integer out of range");
                            System.out.print("Enter Your Choice: ");
                    }
                } while (flag);
            }
            else {
                System.out.println("Invalid Input");
            }
//            break;
        }

    }
}

