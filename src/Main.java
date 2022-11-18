import java.sql.*;

public class Main {
    public static String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db14?autoReconnect=true&useSSL=false";
    public static String dbUsername = "Group14";
    public static String dbPassword = "CSCI3170";

    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        System.out.println("Hello world!");
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
        System.out.println("Connection Established successfully");
        Statement st = con.createStatement();

//        st.executeUpdate("CREATE TABLE Sailors" +
//                "(sid integer, name varchar(32)," +
//                "rating integer, age float)");
//        st.executeUpdate("INSERT INTO Sailors VALUES ( 22, 'Dustin', 7, 45.0 )");
//        st.executeUpdate("INSERT INTO Sailors VALUES ( 25, 'Smith', 8, 50.0 )");

        String query = "SELECT sname, rating FROM Sailors";
        ResultSet rs = st.executeQuery(query);
        System.out.println("Nice World");

        while(rs.next()){
            String s = rs.getString("sname");
            int n = rs.getInt("rating");
            System.out.println(s + " " + n);
        }
    }
}

