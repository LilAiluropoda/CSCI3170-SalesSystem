import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

// to do: print the header of column in the beginning search part result
public class Salesperson {
    final private Connection conn;
    final private Statement stmt;

    // 0 for search_part,
    // replace search_criterion with (mName/pName)
    // 1, 2for transaction
    // 1 for check the part available
    // 2 for perform transaction
    private static String [] query_operation = {
            "SELECT  pID AS ID, pName AS Name, mName AS Manufacturer, cName AS Category, pAvailableQuantity AS Quantity, pWarrantyPeriod AS Warranty, pPrice AS Price " +
            "FROM part natural join manufacturer natural join category " +
            "WHERE search_criterion LIKE \"%search_keyword%\" " +
            "ORDER BY pPrice sort_in",
            "SELECT pAvailableQuantity, pName " +
            "FROM part WHERE pID = ",
            "INSERT INTO transaction(tID, pID, sID, tDate) " +
            "values(?, ?, ?,?)"
    };
    final private static Scanner scanner = new Scanner(System.in);
    Salesperson(Connection conn) throws SQLException {
        this.conn = conn;
        this.stmt = conn.createStatement();
    }

    private void print_operation(){
        // System.out.println("-----Operations for salesperson menu-----");
        // System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Search for parts");
        System.out.println("2. Sell a part");
        System.out.println("3. Return to the main menu");
        System.out.print("Enter Your Choice: ");
    }


    private void search_for_part() throws SQLException{
        System.out.println("Choose the Search criterion:");
        System.out.println("1. Part Name");
        System.out.println("2. Manufacturer Name");
        System.out.print("Choose the Search criterion: ");
        // ask for search criterion, 1 for part name, 2 for manufacturer name
        String search_criterion = null, sort_in = null, cur_query_op, search_keyword = null;
        int choice = scanner.nextInt();
        // to read the new-line character, let the next nextline() scanner can get input correctly
        scanner.nextLine();
        switch(choice){
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
        System.out.println("Choose ordering:");
        System.out.println("1. By price, ascending order");
        System.out.println("2. By price, descending order");
        System.out.print("Choose the search criterion: ");
        choice = scanner.nextInt();
        switch(choice){
            case 1:
                sort_in = "ASC";
                break;
            case 2:
                sort_in = "DESC";
                break;
            default:
                System.out.println("Invalid input. User should input 1 or 2. There will be error");
                return;
        }
        cur_query_op = this.query_operation[0].replace("search_criterion", search_criterion);
        cur_query_op = cur_query_op.replace("sort_in", sort_in);
        cur_query_op = cur_query_op.replace("search_keyword", search_keyword);

        //get the result and print out the result
        ResultSet rs = this.stmt.executeQuery(cur_query_op);

        System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
        while(rs.next()){
            System.out.print("| ");
            for(int i = 1; i <= 7; i++){
                System.out.print(rs.getString(i));
                System.out.print(" | ");
            }
            System.out.println();
        }
        System.out.println("End of Query");
    }

    private void transaction() throws SQLException{
        System.out.print("Enter The Part ID: ");
        int pid = scanner.nextInt();
        System.out.print("Enter The Salesperson ID: ");
        int sid = scanner.nextInt();

        // check is the part available
        String cur_query_op = this.query_operation[1] + String.valueOf(pid);
        ResultSet rs = this.stmt.executeQuery(cur_query_op);
        rs.next();
        String ret_sql = rs.getString(1);
        int avai_qua = Integer.parseInt(ret_sql);
        String pname = rs.getString(2);
        if (avai_qua <= 0){
            System.out.println("Error. The available quality of this part in database is " + avai_qua +". This operation will truncate.");
            return;
        }
        // Perform transaction
        // 1. Update the available quantity the corresponding part
        // 2. Add a new record to the transaction record

        avai_qua--;
        // Update available quantity
        String update_statement = "UPDATE part SET pAvailableQuantity = " +  String.valueOf(avai_qua) + " WHERE pId = " + String.valueOf(pid);
        System.out.println(update_statement);
        int update_ret = stmt.executeUpdate(update_statement);
        System.out.println("update ret: " + update_ret);
        // Add a new record to the transaction record
        // get the max of tid first
        rs = stmt.executeQuery("select MAX(tID) from transaction");
        rs.next();
        int tid = rs.getInt(1);
        tid++;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        java.sql.Date sql_date = java.sql.Date.valueOf(sdf.format(date));
        PreparedStatement pstmt = this.conn.prepareStatement(query_operation[2]);
        pstmt.setInt(1, tid);
        pstmt.setInt(2, pid);
        pstmt.setInt(3, sid);
        pstmt.setDate(4, sql_date);
        update_ret = pstmt.executeUpdate();
        System.out.println("update ret: " + update_ret);
        // end of perform transaction and print the result
        System.out.println("Product: " + pname + "(id: " + pid + ") Remaining Quality: " + avai_qua);
    }

    public void decide_operation() throws SQLException{
        print_operation();
        int choice = scanner.nextInt();
        switch (choice){
            case 1:
                this.search_for_part();
                break;
            case 2:
                this.transaction();
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid input! You should input number 1, 2, or 3! This operation would be truncate.");
        }

    }
}
