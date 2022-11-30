import java.util.*;
import java.sql.*;
import java.lang.*;

public class Manager {

    private Connection conn; //connection variable
    private Statement stmt; //statement object variable

    Manager(Connection conn) throws SQLException{ //constructor, establish connection
        this.conn = conn;
        this.stmt = conn.createStatement();
    }

    public void SelectOp(){
        System.out.println("1. List all salespersons"); //call ListSalepersons()
        System.out.println("2. Count the no. of sales record of each salesperson under a specific range on years of experience"); //call CountSalesRecord()
        System.out.println("3. Show the total sales value of each manufacturer"); //call ShowSalesValue()
        System.out.println("4. Show the N most popular part"); //call ShowNMostPopularPart()
        System.out.println("5. Return to the main menu"); //use continue to go back to topmost menu
        System.out.print("Enter Your Choice: "); //use switch statement to read input and branch
    }

    public void ListSalepersons() {
        //print selection message
        System.out.println("Choose ordering:");
        System.out.println("1. By ascending order");
        System.out.println("2. By descending order");
        System.out.print("Choose the list ordering: ");

        Scanner stdin = new Scanner(System.in);
        int x = -1;
        Boolean flag = true;

        String order = ""; //ordering of result, default empty;

        do{ //loop if invalid output
            try{
                x = stdin.nextInt(); //read input

            } catch (InputMismatchException ex) { //Catch Exception
                System.out.println("Invalid Input: Wrong input type");
                System.out.print("Choose the list ordering: ");
                stdin.next();
                continue;

            }

            switch (x) {
                case 1: //ascending order
                    order = "ASC";
                    flag = false;
                    break;
                case 2: //descending order
                    order = "DESC";
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid Input: Integer out of range");
                    System.out.print("Choose the list ordering: ");
            }
        } while (flag);

        String query = "SELECT sID, sName, sPhoneNumber, sExperience FROM salesperson ORDER BY sExperience " + order;
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query); //send query
        } catch (SQLException ex){
            System.out.println("SQL Error: Failed to query");
            return; //return to menu if query fail
        }

        System.out.println("| ID | Name | Mobile Phone | Years of Experience |");

        try {
            while (rs.next()){
                //get result
                int sid = rs.getInt("sID");
                String sname = rs.getString("sName");
                String sphone = rs.getString("sPhoneNumber");
                int sexp = rs.getInt("sExperience");
                String row = String.format("| %d | %s | %s | %d |", sid, sname, sphone, sexp);

                //print result
                System.out.println(row);
            }
            System.out.println("End of Query");
        } catch (SQLException ex){
            System.out.println("SQL Error: Fail to return result");
        }
    }

    public void CountSalesRecord() {

        Scanner stdin = new Scanner(System.in);
        Boolean flag = true;
        int lowerb = -1;
        int upperb = -1;

        do {
            do{
                try{
                    System.out.print("Type in the lower bound for years of experience: ");
                    lowerb = stdin.nextInt();
                } catch (InputMismatchException ex){
                    System.out.println("Invalid Input: Wrong input type");
                    stdin.next();
                }
                if (lowerb >= 0){
                    flag = false;
                }
            } while(flag);

            do{
                try{
                    System.out.print("Type in the upper bound for years of experience: ");
                    upperb = stdin.nextInt();
                } catch (InputMismatchException ex){
                    System.out.println("Invalid Input: Wrong input type");
                    stdin.next();
                }
                if (upperb >= 0){
                    flag = false;
                }
            } while(flag);

            if (lowerb > upperb){
                System.out.println("Invalid Input: Lower bound is greater than upper bound");
                flag = true;
            }
        } while(flag); //get lower and upper bound

        //convert to String
        String low = Integer.toString(lowerb);
        String up = Integer.toString(upperb);

        String query = "SELECT s.sID, s.sName, s.sExperience, COUNT(t.tID) FROM (SELECT * FROM salesperson WHERE sExperience >= " + low + " AND sExperience <= " + up + ") s, transaction t WHERE s.sID = t.sID GROUP BY s.sID ORDER BY s.sID DESC";
        ResultSet rs;

        try {
            rs = stmt.executeQuery(query); //send query
        } catch (SQLException ex){
            System.out.println("SQL Error: Failed to query");
            return; //return to menu if query fail
        }

        System.out.println("| ID | Name | Years of Experience | Number of Transaction |");

        try {
            while (rs.next()){
                //get result
                int sid = rs.getInt("s.sID");
                String sname = rs.getString("s.sName");
                int sexp = rs.getInt("s.sExperience");
                int nooftrans = rs.getInt("COUNT(t.tID)");
                String row = String.format("| %d | %s | %d | %d |", sid, sname, sexp, nooftrans);

                //print result
                System.out.println(row);
            }
            System.out.println("End of Query");
        } catch (SQLException ex){
            System.out.println("SQL Error: Fail to return result");
        }
    }

    public void ShowSalesValue() {
        System.out.println("| Manufacturer ID | Manufacturer Name | Total Sales Value |");

        String query = "SELECT p.mID, m.mName, SUM(x.salesvalue) AS sumsales FROM part p, (SELECT p.pID, count(t.tID)*p.pPrice AS salesvalue FROM part p, transaction t WHERE p.pID = t.pID GROUP BY p.pID) x, manufacturer m WHERE p.pID = x.pID AND p.mID = m.mID GROUP BY p.mID ORDER BY sumsales DESC";
        ResultSet rs;

        try {
            rs = stmt.executeQuery(query); //send query
        } catch (SQLException ex){
            System.out.println("SQL Error: Failed to query");
            return; //return to menu if query fail
        }

        try {
            while (rs.next()){
                //get result
                int sid = rs.getInt("p.mID");
                String sname = rs.getString("m.mName");
                int salesvalue = rs.getInt("sumsales");
                String row = String.format("| %d | %s | %d", sid, sname, salesvalue);

                //print result
                System.out.println(row);
            }
            System.out.println("End of Query");
        } catch (SQLException ex){
            System.out.println("SQL Error: Fail to return result");
        }

    }

    public void ShowNMostPopularPart() {

        Scanner stdin = new Scanner(System.in);
        Boolean flag = true;
        int x = -1;

        do{
            try{
                System.out.print("Type in the number of parts: ");
                x = stdin.nextInt();
                flag = false;
            } catch (InputMismatchException ex){
                System.out.println("Invalid Input: Wrong input type");
                stdin.next();
            }
            if (x <= 0){
                System.out.println("Invalid Input: Integer out of range");
                flag = true;
            }
        } while(flag); //get N

        //convert to String
        String N = Integer.toString(x);

        String query = "SELECT p.pID, p.pName, x.trans FROM (SELECT t.pID, COUNT(t.tID) AS trans FROM transaction t GROUP BY t.pID ORDER BY trans DESC LIMIT "+ N +") x, part p WHERE p.pID = x.pID;";
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query); //send query
        } catch (SQLException ex){
            System.out.println("SQL Error: Failed to query");
            return; //return to menu if query fail
        }

        System.out.println("| Part ID | Part Name | No. of Transaction |");

        try {
            while (rs.next()){
                //get result
                int pid = rs.getInt("p.pID");
                String pname = rs.getString("p.pName");
                int notrans = rs.getInt("trans");
                String row = String.format("| %d | %s | %d |", pid, pname, notrans);

                //print result
                System.out.println(row);
            }
            System.out.println("End of Query");
        } catch (SQLException ex){
            System.out.println("SQL Error: Fail to return result");
        }

    }










}
