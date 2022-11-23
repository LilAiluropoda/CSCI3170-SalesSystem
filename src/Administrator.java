import java.sql.SQLException;
import java.sql.Statement;

public class Administrator {
    private Statement st;

    Administrator(Statement st) {
        this.st = st;
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

        String[] table = {
                "category",
                "manufacturer",
                "part",
                "salesperson",
                "transaction"
        };
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
                        "tDate CHAR NOT NULL, " +
                        "PRIMARY KEY(tID), " +
                        "FOREIGN KEY(pID) REFERENCES part(pID), " +
                        "FOREIGN KEY(sID) REFERENCES salesperson(sID), " +
                        "CHECK(tID > 0 AND tID <= 9999)" +
                        ")",
        };


        for(int i = 0; i < 5; i++){
//            st.executeUpdate("DROP TABLE " + table[i]);
            st.executeUpdate(tableCreation[i]);
        }

        System.out.println("Done!");
        System.out.println("Database is initialized!");
    }

    public void deleteTable() {

    }

    public void loadData() {

    }

    public void showContent() {

    }

}
