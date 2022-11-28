import java.io.*;
import java.sql.*;
import java.util.Scanner;
public class Salesperson {
    final private Connection conn;
//    final private Statment stmt;

    // 1 for search_part,
    // replace search_criterion with (mName/pName)
    // 2 for sell_part
    //
    private static String [] query_operation = {
            "SELECT  pID AS ID, pName AS Name, mName AS Manufacturer, cName AS Category, pAvailableQuantity AS Quantity, pWarrantyPeriod AS Warranty, pPrice AS Price" +
            "FROM part natural join manufacturer natural join category " +
            "WHERE search_criterion = \"search_keyword\" " +
            "ORDER BY pPrice sort_in",
            ""
    };
    final private static Scanner scanner = new Scanner(System.in);
    public Salesperson(Connection conn) throws SQLException {
        this.conn = conn;
//        this.stmt = conn.createStatement();
    }

    public void print_operation(){
        System.out.println("-----Operations for salesperson menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Search for parts");
        System.out.println("2. Sell a part");
        System.out.println("3. Return to the main menu");
        System.out.print("Enter Your Choice: ");
    }

    public void search_for_part(){
        System.out.println("Choose the Search criterion:");
        System.out.println("1. Part Name");
        System.out.println("2. Manufacturer Name");
        System.out.print("Choose the Search criterion: ");
        // ask for search criterion, 1 for part name, 2 for manufacturer name
        String search_criterion, sort_in, cur_query_op, search_keyword;
        int choose = scanner.nextInt();
        switch(choose){
            case 1:
                search_criterion = "pName";
                break;
            case 2:
                search_criterion = "mName";
                break;
            default:
                System.out.println("Invalid input. User should input 1 or 2. There will be error.");
        }
        // ask for the search keyword
        System.out.print("Type in the Search Keyword: ");
        search_keyword = scanner.nextLine();
        // ask for the way to sort the array
        choose = scanner.nextInt();
        switch(choose){
            case 1:
                sort_in = "ASC";
                break;
            case 2:
                sort_in = "DESC";
                break;
            default:
                System.out.println("Invalid input. User should input 1 or 2. There will be error");
        }
        cur_query_op = this.query_operation[0].replace("search_criterion", search_criterion);
        cur_query_op = cur_query_op.replace("sort_in", sort_in);
        cur_query_op = cur_query_op.replace("search_keyword", search_keyword);
        // debug use
        System.out.println("Debug. query operation send in to pstmt:");
        System.out.println(cur_query_op);
        // debug end

        //get tge result and print out the result
        ResultSet rs = this.conn.createStatement(cur_query_op);
        while(rs.next()){
            System.out.print("| ");
            for(int i = 1; i <= 7; i++){
                System.out.print(rs.getString(i));
                System.out.print(" | ");
            }
            System.out.println();
        }
        println("End of Query");

    }

}
