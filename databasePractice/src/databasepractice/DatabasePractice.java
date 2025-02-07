/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package databasepractice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author yuna
 * This is a java program that shows the user all the rows in a MySql table 'coffee' from the DB 'newDB'
 * Then it prompts the user to enter anew entry o insert a new set of values to the table
 * After that it prints out the new table with a 'succesfully Added' message after showing the appended table.
 * Also, if the entrance is wrong and doesn't fit SQL rules, error is outputted and the user s asked to reenter 
 */

/**
 * 
 * @author yuna
 * latest edit notice: changed the insertion part to be a method
 */

public class DatabasePractice {

    //Global variable initialization
    String url;
    String user;
    String passwd;
    static Connection con;
    static Statement stmt;
    static ResultSet rs;
    
    //  method to create a connection
    void createConnection() throws SQLException{
        url = "jdbc:mysql://localhost:3306/newDB?zeroDateTimeBehavior=CONVERT_TO_NULL";
        user = "root";
        passwd = "BettYBananA218@";
        
        con = DriverManager.getConnection(url, user, passwd);
        stmt = con.createStatement();
    }
    
    //  Shows the whole coffee table in the database
    public static void tableOutput() throws SQLException{
        rs = stmt.executeQuery("SELECT * FROM coffee;");
        
        //rs.next(); // unnecensary because the while loop checks for it anyway
        
        System.out.println("id   roast");
        while (rs.next()){
            String roast = rs.getString("roast");
            int id = rs.getInt("id");
            System.out.println(id + "  " + roast);
        }
    }
    
    //inserts the values given by the user into the coffe table of the newDB
    public static boolean isInsertIntoTable(int uInpId, String uInpRoast) {
        try {
            String insertSt = "INSERT INTO coffee (id, roast) values(  " + uInpId +", '" + uInpRoast + "');";
            stmt.execute(insertSt);
            return true;
        } catch (SQLException e) {
            //Logger.getLogger(DatabasePractice.class.getName()).log(Level.SEVERE, null, ex);
            System.out.print("ERROR, CANT ADD TO DB: " + e.getMessage());
            return false;
        }
        
    }
    
    //Ask user to reinput values/ new values to input reccursivly
    public static void newInput(Scanner in){
        String newInp = "y";
        int count = 0;
        while(newInp.equals("y")){
            try {
                System.out.print("ID: ");
                int uInpID = in.nextInt();
                System.out.print("Roast: ");
                String uInpRoast = in.next();
                
                String insertStmt = "INSERT INTO coffee (id, roast) values(" + uInpID + ", '" + uInpRoast + "');";
                stmt.execute(insertStmt);
                System.out.println("INSERTED SUCCESSFULLY!\n----------------------------------------------------------------------");
                System.out.print("Would you like to add new input? (type[Y]es or [N]o (or any other key): ");
                newInp = in.next();
                count++;
                
            } catch (SQLException e) {
                System.out.println("ERROR, CANT ADD TO DB: " + e.getMessage());
                System.out.print("Would you like to add new input? (type[Y]es or [N]o (or any other key): ");
                newInp = in.next();
            }
        }
        System.out.println("\nTHANK YOU, YOUR INPUTS WILL BE SAVED!");
    }
    
    public static void main(String[] args) throws SQLException{
        //Scanner initialization
        Scanner in = new Scanner(System.in);
        
        //establishes connection
        DatabasePractice pro =  new DatabasePractice();
        pro.createConnection();
        
        System.out.println("----------------------------------------------------------------------");
        //show whole Table
        tableOutput();
        
        System.out.println("----------------------------------------------------------------------\n");
        System.out.println("Enter the values for id and roast to add to the table. The options for roast are\n---Light----Medium----Dark---- ");
        
        System.out.print("ID: ");
        int uInpID = in.nextInt();
        System.out.print("Roast: ");
        String uInpRoast = in.next();
        
        //adds previously asked values to table
        boolean addedToTable = isInsertIntoTable(uInpID, uInpRoast);
        
        
        
        // Sucess / failure message SIMPLE!! + ask if want to reinput/new input
        System.out.println("\n---------------------------------------------------------------------------");
        if(addedToTable){
            System.out.println("ADDED TO TABLE SUCCESFULLY");
            System.out.print("Would you like to add new input? (type[Y]es or [N]o (or any other key):");
            if(in.next().toLowerCase().equals("y")){
                newInput(in);
            }
        } else{
            System.out.println("FAILED TO ADD TO TABLE");
            System.out.print("Would you like to add new input? (type[Y]es or [N]o (or any other key):");
            if(in.next().toLowerCase().equals("y")){
                newInput(in);
            }
        }
        
        //print final values
        System.out.println("\n----------------------------------------------------------------------\nFINAL VALUES AFTER APPPENDED DATABSE BY USER\n----------------------------------------------------------------------");
        tableOutput();
        System.out.println("----------------------------------------------------------------------");
    } 
}
