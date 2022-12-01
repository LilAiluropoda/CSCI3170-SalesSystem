Group 14

Cheng Yu Shing 1155158488
Lau Kwun Hang 1155158471
Lo Hoa Tsun 1155158762

List of file:
1. Main.java
Implementation of the main menu for the sales system and perform the I/O for the program.

2. Administrator.java
Implementation of the administrator class and corresponding functions.
    1. Create table schemas in the database
    2. Delete table schemas in the database
    3. Load data from a dataset
    4. Show the content of a specified table

3. Salesperson.java
Implementation of the salesperson class and corresponding functions.
    1. Search for Parts
    2. Perform Transaction

4. Manager.java
Implementation of the manager class and corresponding functions.
    1. List all salespersons in ascending or descending order of years of experience
    2. Count the number of transaction records of each salesperson within a given range on years of
       experience
    3. Sort and list the manufacturers in descending order of total sales value
    4. Show the N most popular parts

Method of compilation and execution:
javac ./*.java
java -classpath ./mysql-jdbc.jar:./ Main