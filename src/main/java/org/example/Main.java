package org.example;

import java.sql.*;
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
                    case 2:viewReservation(con); break;
                    case 3:getRoomNumber(con,sc); break;
//                    case 4:updateDetails(con,sc); break;
//                    case 5:deleteReservation(con,sc); break;
//                    case 0:exit(); return;
                    default:
                        System.out.println("Invalid Input Try again!!");
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
                    System.out.println("Reservation Successful!!");
                }
                else{
                    System.out.println("Reservation failed");

                }
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    private static void viewReservation(Connection con){

        String sqlQuery="select guest_id,guest_name,room_number,contact_num,reservation_date " +
                        "from reservation; ";

        try(Statement stmt= con.createStatement()){

            ResultSet rs=stmt.executeQuery(sqlQuery);

            System.out.println("Current Reservations: ");
            System.out.println("+---------------------+--------------------------+--------------------+-----------------------------------+--------------------------------+");
            System.out.println("|     Guest_ID        |         Guest_Name       |      Room Number   |         Contact Number            |         Reservation Date       |");
            System.out.println("+---------------------+--------------------------+--------------------+-----------------------------------+--------------------------------+");

            while(rs.next())
            {
                int guest_id=rs.getInt("guest_id");
                String guest_name=rs.getString("guest_name");
                int room_number=rs.getInt("room_number");
                String contact_num=rs.getString("contact_num");
                String reservation_date=rs.getTimestamp("reservation_date").toString();

                System.out.printf("| %-21d | %-27s | %-20d | %-35s | %-32s |",
                          guest_id,guest_name,room_number,contact_num,reservation_date);



            }

            System.out.println("+---------------------+--------------------------+--------------------+-----------------------------------+--------------------------------+");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    private static void getRoomNumber(Connection con,Scanner sc)
    {
        try{
        System.out.println("Enter the reservation Id : ");
        int guest_id=sc.nextInt();

        System.out.println("Enter the name : ");

        String guest_name=sc.nextLine();

        String sqlQuery="select room_number from reservation " +
                "where guest_id="+guest_id+" and guest_name = '"+guest_name+"'";

        try(Statement stmt=con.createStatement()){

            ResultSet rs=stmt.executeQuery(sqlQuery);
            if(rs.next())
            {
                int room_number=rs.getInt("room_number");

                System.out.println("The room number for "+guest_id+" Name : "+guest_name+ " is:"+room_number);
            }
            else {
                System.out.println("No resrvation found!!");
            }


        }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}