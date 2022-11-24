import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class Administrator {
    final private Connection con;
    final private Statement st;

    final private String[] table = {
            "category",
            "manufacturer",
            "part",
            "salesperson",
            "transaction"
    };

    public static Scanner scanner = new Scanner(System.in);

    Administrator(Connection con) throws SQLException {
        this.con = con;
        this.st = con.createStatement();
    }

    public void printOperations() {
        System.out.println("1. Create all tables");
        System.out.println("2. Delete all tables");
        System.out.println("3. Load from datafile");
        System.out.println("4. Show content of a table");
        System.out.println("5. Return to main menu");
    }

    public void createTable() throws SQLException {
        System.out.println("Processing...");
        // create table
        String[] tableCreation = {
                "CREATE TABLE category " +
                        "(" +
                        "cID INT NOT NULL ," +
                        "cName VARCHAR(20) NOT NULL," +
                        "PRIMARY KEY(cID), " +
                        "CHECK(cID > 0 AND cID <= 9)" +
                        ")",
                "CREATE TABLE manufacturer" +
                        "(" +
                        "mID INT NOT NULL ," +
                        "mName VARCHAR(20) NOT NULL, " +
                        "mAddress VARCHAR(50) NOT NULL, " +
                        "mPhoneNumber CHAR(8) NOT NULL, " +
                        "PRIMARY KEY(mID), " +
                        "CHECK(mID > 0 AND mID <= 99 AND mPhoneNumber > 0)" +
                        ")",
                "CREATE TABLE part" +
                        "(" +
                        "pID INT NOT NULL ," +
                        "pName VARCHAR(20) NOT NULL, " +
                        "pPrice INT NOT NULL, " +
                        "mID INT," +
                        "cID INT, " +
                        "pWarrantyPeriod INT NOT NULL, " +
                        "pAvailableQuantity INT NOT NULL, " +
                        "PRIMARY KEY(pID), " +
                        "FOREIGN KEY(mID) REFERENCES manufacturer(mID)," +
                        "FOREIGN KEY(cID) REFERENCES category(cID)," +
                        "CHECK(" +
                        "pID > 0 AND pID <= 999 " +
                        "AND pPrice > 0 AND pPrice <= 99999 " +
                        "AND pWarrantyPeriod > 0 AND pWarrantyPeriod <= 99 " +
                        "AND pAvailableQuantity >= 0 AND pAvailableQuantity <= 99" +
                        ")" +
                        ")",
                "CREATE TABLE salesperson" +
                        "(" +
                        "sID INT NOT NULL ," +
                        "sName VARCHAR(20) NOT NULL, " +
                        "sAddress VARCHAR(50) NOT NULL, " +
                        "sPhoneNumber CHAR(8) NOT NULL, " +
                        "sExperience INT NOT NULL, " +
                        "PRIMARY KEY(sID), " +
                        "CHECK(" +
                        "sID > 0 AND sID <= 99 " +
                        "AND sPhoneNumber > 0 " +
                        "AND sExperience > 0 AND sExperience <= 9" +
                        ")" +
                        ")",
                "CREATE TABLE transaction" +
                        "(" +
                        "tID INT NOT NULL ," +
                        "pID INT NOT NULL, " +
                        "sID INT NOT NULL, " +
                        // DATEEEEEEE
                        "tDate DATE NOT NULL, " +
                        "PRIMARY KEY(tID), " +
                        "FOREIGN KEY(pID) REFERENCES part(pID), " +
                        "FOREIGN KEY(sID) REFERENCES salesperson(sID), " +
                        "CHECK(tID > 0 AND tID <= 9999)" +
                        ")",
        };
        for (int i = 0; i < 5; i++) {
            PreparedStatement pst = con.prepareStatement(tableCreation[i]);
            pst.execute();
        }
        System.out.println("Done!");
        System.out.println("Database is initialized!");
    }

    public void deleteTable() throws SQLException {
        System.out.println("Processing...");

        for (int i = 4; i >= 0; i--) {
            PreparedStatement pst = con.prepareStatement("DROP TABLE " + table[i]);
            pst.execute();
        }
        System.out.println("Done!");
        System.out.println("Database is deleted!");
    }

    public void loadData() throws SQLException, FileNotFoundException{

        System.out.print("Type in the Source Data Folder Path: ");
        String folderPath = scanner.next();
        System.out.println("Processing...");
        // category
        File file = new File("./src/" + folderPath + "/" + table[0] + ".txt");
        Scanner fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String data = fileReader.nextLine();
            String[] values = data.split("\t", 2);
            PreparedStatement pst1 = con.prepareStatement("INSERT INTO category (cID, cName) VALUES (?,?)");
            pst1.setInt(1, Integer.parseInt(values[0]));
            pst1.setString(2, values[1]);
            pst1.execute();
        }
        // manufacturer
        file = new File("./src/" + folderPath + "/" + table[1] + ".txt");
        fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String data = fileReader.nextLine();
            String[] values = data.split("\t", 4);
            PreparedStatement pst2 = con.prepareStatement("INSERT INTO manufacturer (mID, mName, mAddress, mPhoneNumber) VALUES (?,?,?,?)");
            pst2.setInt(1, Integer.parseInt(values[0]));
            pst2.setString(2, values[1]);
            pst2.setString(3, values[2]);
            pst2.setInt(4, Integer.parseInt(values[3]));
            pst2.execute();
        }
        // parts
        file = new File("./src/" + folderPath + "/" + table[2] + ".txt");
        fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String data = fileReader.nextLine();
            String[] values = data.split("\t", 7);
            PreparedStatement pst3 = con.prepareStatement("INSERT INTO part (pID, pName, pPrice, mID, cID, pWarrantyPeriod, pAvailableQuantity) VALUES (?,?,?,?,?,?,?)");
            pst3.setInt(1, Integer.parseInt(values[0]));
            pst3.setString(2, values[1]);
            pst3.setInt(3, Integer.parseInt(values[2]));
            pst3.setInt(4, Integer.parseInt(values[3]));
            pst3.setInt(5, Integer.parseInt(values[4]));
            pst3.setInt(6, Integer.parseInt(values[5]));
            pst3.setInt(7, Integer.parseInt(values[6]));
            pst3.execute();
        }
        // salesperson
        file = new File("./src/" + folderPath + "/" + table[3] + ".txt");
        fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String data = fileReader.nextLine();
            String[] values = data.split("\t", 5);
            PreparedStatement pst4 = con.prepareStatement("INSERT INTO salesperson (sID, sName, sAddress, sPhoneNumber, sExperience) VALUES (?,?,?,?,?)");
            pst4.setInt(1, Integer.parseInt(values[0]));
            pst4.setString(2, values[1]);
            pst4.setString(3, values[2]);
            pst4.setInt(4, Integer.parseInt(values[3]));
            pst4.setInt(5, Integer.parseInt(values[4]));
            pst4.execute();
        }
        // transaction
        file = new File("./src/" + folderPath + "/" + table[4] + ".txt");
        fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String data = fileReader.nextLine();
            String[] values = data.split("\t", 5);
            PreparedStatement pst5 = con.prepareStatement("INSERT INTO transaction (tID, pID, sID, tDate) VALUES (?,?,?,?)");
            pst5.setInt(1, Integer.parseInt(values[0]));
            pst5.setInt(2, Integer.parseInt(values[1]));
            pst5.setInt(3, Integer.parseInt(values[2]));
            String[] dateDetail = values[3].split("/",3);
            pst5.setDate(4, java.sql.Date.valueOf(dateDetail[2] + "-" + dateDetail[1] + "-" + dateDetail[0]));
            pst5.execute();
        }

        System.out.println("Done!");
        System.out.println("Database is inputted to the database!");
    }

    public void showContent() {

    }

}
