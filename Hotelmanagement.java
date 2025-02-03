package com.hotel.java;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class HotelManagement {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        try {
            
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/RK_HOTEL", "root", "password12345678");
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("WELCOME TO RK HOTEL MANAGEMENT");
                System.out.println("WELCOME YOU ALL MY DEAR CUSTOMERS");
                System.out.println("1. Reserve a room");
                System.out.println("2. View Registration");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Registration");
                System.out.println("5. Delete Registration");
                System.out.println("6. Exit");
                System.out.println("Choose Num: ");
                int num = sc.nextInt();

                switch (num) {
                    case 1:
                        BookRoom(con, sc);
                        break;
                    case 2:
                    	ViewRegistration(con);
                        
                        break;
                    case 3:
                    	GetRoom(con,sc);
                        
                        break;
                    case 4:
                    	UpdateRegistration(con,sc);
                        break;
                    case 5:
                    	DeleteRegistration(con,sc);
                        break;
                    case 6:
                    	 System.out.println("Exiting.you are out of Registartion.THANK YOU VISIT AGAIN");
                        con.close(); 
                        sc.close(); 
                        return; 
                    default:
                        System.out.println("Invalid choice, please select a valid option.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
    }

	
	
	
    public static void BookRoom(Connection con, Scanner sc) {
        System.out.println("Welcome to Reserve/Book a Room");
        System.out.println("=============================");
        System.out.println("Enter Customer Name: ");
        String name = sc.next();
        System.out.println("Enter Room No: ");
        int roomNo = sc.nextInt();

        String phone1 = "";
        while (phone1.length() != 10) {
            System.out.println("Enter Phone No (10 digits): ");
            phone1 = sc.next();
            if (phone1.length() != 10) {
                System.out.println("Phone number must be 10 digits. Please enter again.");
            }
        }

        String query = "INSERT INTO room_reservations(customer_name, room_num, contact_num) VALUES (?, ?, ?)";
        try {
            PreparedStatement pre = con.prepareStatement(query);
            pre.setString(1, name);
            pre.setInt(2, roomNo);
            pre.setString(3, phone1);

            int result = pre.executeUpdate();
            if (result > 0) {
                System.out.println("Data inserted into room_reservations table successfully.");
            } else {
                System.out.println("Data not inserted into room_reservations table.");
            }
        }
            catch (SQLException e) {
                e.printStackTrace(); // Print stack trace for debugging
            }
    }
        
        
        
 
    public static void ViewRegistration(Connection con)
        {
          String query="select  booking_id, customer_name, room_num, contact_num,booking_date from room_reservations";
          
          try {
            Statement stat=con.createStatement();
            ResultSet res=stat.executeQuery(query);
            while(res.next())
            {
              int id=res.getInt("booking_id");
              String name=res.getString("customer_name");
              int room=res.getInt("room_num");
              String phone=res.getString("contact_num");
              Date date=res.getDate("booking_date");
              System.out.println(id);
              System.out.println(name);
              System.out.println(room);
              System.out.println(phone);
              System.out.println(date);
              
              System.out.println();
            }
          } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
          
          
 
          public static void GetRoom(Connection con,Scanner sc)
          {
            System.out.println("Enter phone number to know room no : ");
            String phone=sc.next();
            String query="select room_num,contact_num from room_reservations where contact_num=?";
            
            try {
              PreparedStatement pre=con.prepareStatement(query);
              pre.setString(1,phone);
              
              ResultSet res=pre.executeQuery();
              boolean b=false;
              while(res.next())
              {
                int room=res.getInt("room_num");
                String p=res.getString("contact_num");
                if(phone.equals(p))
                {
                  System.out.println("Room no found based on user phone no : " + room);
                  b=true;
                }
                
              }
              if(!b)
              {
                System.out.println("Room no not found");
              }
              
            } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
            
            
            
            public static void UpdateRegistration(Connection con, Scanner sc) {
                System.out.println("Update Registration Details");
                System.out.println("===========================");
                System.out.println("Enter the Booking ID to update: ");
                int bookingId = sc.nextInt(); // Assuming booking_id is used for updating, modify as needed

                System.out.println("Enter new Customer Name: ");
                String newName = sc.next();

                System.out.println("Enter new Room No: ");
                int newRoomNo = sc.nextInt();

                String newPhone = "";
                while (newPhone.length() != 10) {
                    System.out.println("Enter new Phone No (10 digits): ");
                    newPhone = sc.next();
                    if (newPhone.length() != 10) {
                        System.out.println("Phone number must be 10 digits. Please enter again.");
                    }
                }

                String query = "UPDATE room_reservations SET customer_name = ?, room_num = ?, contact_num = ? WHERE booking_id = ?";
                try {
                    PreparedStatement pre = con.prepareStatement(query);
                    pre.setString(1, newName);
                    pre.setInt(2, newRoomNo);
                    pre.setString(3, newPhone);
                    pre.setInt(4, bookingId);

                    int result = pre.executeUpdate();
                    if (result > 0) {
                        System.out.println("Registration updated successfully.");
                    } else {
                        System.out.println("No registration found with the provided Booking ID.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace(); // Print stack trace for debugging
                }
            }
      
            
            
            
   
            public static void DeleteRegistration(Connection con, Scanner sc) {
                System.out.println("Delete Registration");
                System.out.println("===================");
                System.out.println("Enter the Booking ID to delete: ");
                int bookingId = sc.nextInt(); // Assuming booking_id is used for deletion, modify as needed

                String query = "DELETE FROM room_reservations WHERE booking_id = ?";
                try {
                    PreparedStatement pre = con.prepareStatement(query);
                    pre.setInt(1, bookingId);

                    int result = pre.executeUpdate();
                    if (result > 0) {
                        System.out.println("Registration deleted successfully.");
                    } else {
                        System.out.println("No registration found with the provided Booking ID.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace(); // Print stack trace for debugging
                }
            

    }
}
