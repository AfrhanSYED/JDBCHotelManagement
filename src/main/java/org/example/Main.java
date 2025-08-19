package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String url="jdbc:mysql://localhost:3306:/jdbcdemo";
    private static final String username="root";
    private static final String password="Afrhansay*123";
    public static void main(String[] args) {



        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded Successfully");
        }catch (ClassNotFoundException e)
        {
            System.out.println("Driver was not loaded"+e.getMessage());
        }

        try {

            Connection con= DriverManager.getConnection(url,username,password);
            System.out.println("Connection established!!");
            while (true){
                System.out.println("Hotel Management System!!");
                System.out.println("1.Reserve a room");
                System.out.println("2.View Reservation");
                System.out.println("3.Get room Number");
                System.out.println("4.Update the details");
                System.out.println("5.Cancel the Reservation");
                System.out.println("0.Exit");

                Scanner sc=new Scanner(System.in);
                int choice=sc.nextInt();
                switch (choice){
                    case 1:reserveRoom(con,sc);
                            break;
//                    case 2:viewReservation(con); break;
//                    case 3:getRoomNumber(con,sc); break;
//                    case 4:updateDetails(con,sc); break;
//                    case 5:deleteReservation(con,sc); break;
//                    case 0:exit(); return;
                    default:
                        System.out.println("Invalid Input Try agaim!!");
                }


            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }   catch(Exception e){
            throw new RuntimeException(e);
        }





    }
    private static void reserveRoom(Connection con,Scanner sc){

        try{
            System.out.println("Enter Guest name : ");

            String guestName=sc.nextLine();

            System.out.println("Enter Room Number : ");
            int roomNumber=sc.nextInt();

            System.out.println("Enter Contact Number: ");
            String contactNumber=sc.nextLine();

            String sqlQuery="insert into reservation (guest_name,room_number,contact_num)" +
                    "values('" +guestName+ "' ," +roomNumber+ " , '" +contactNumber+ "')";

            try(Statement stmt= con.createStatement())
            {
                int rowsAffected= stmt.executeUpdate(sqlQuery);
                if(rowsAffected>0)
                {
                    System.out.println("Reservation Successfull!!");
                }
                else{
                    System.out.println("Reservation failed");

                }
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}