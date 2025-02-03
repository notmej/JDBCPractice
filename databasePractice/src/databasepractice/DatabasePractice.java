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
        
        
        // Sucess / failure message / ask for new input
        System.out.println("----------------------------------------------------------------------\n");
        if(addedToTable){
            System.out.println("----ADDED TO TABLE SUCCESSFULLY!----\n");
            System.out.println("UPDATED TABLE:");
            tableOutput();
            
        } else {
            System.out.println("----ERROR: CNT ADD TO TABLE----\n");
            System.out.println("UPDATED TABLE:");
            tableOutput();
            
            //asks if user would liek to reenter
            System.out.println("If you'd like to ReEnter, please enter 'y' and if you'd like to exit, press any other key:");
            String newValEnt = in.next();
            
            while(newValEnt.toLowerCase().equals("y")){
                System.out.print("ID: ");
                uInpID = in.nextInt();
                
                System.out.print("Roast: ");
                uInpRoast = in.next();
                
                addedToTable = isInsertIntoTable(uInpID, uInpRoast);
                if(addedToTable){
                    System.out.println("----ADDED TO TABLE SUCCESSFULLY!----\n");
                    System.out.println("UPDATED TABLE:");
                    
                    tableOutput();
                    
                    System.out.println("If you'd like to Enter a new value, please enter 'y' and if you'd like to exit, press any other key:");
                    newValEnt = in.next();

                } else {
                    
                    System.out.println("If you'd like to ReEnter, please enter 'y' and if you'd like to exit, press any other key:");
                    newValEnt = in.next();

                }
            }
        }
    } 
}
