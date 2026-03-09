package vehicle;

import java.sql.*;
import java.util.Scanner;

public class CarRental {
	
	Scanner sc = new Scanner(System.in);

//	method to add new car to the database
    public void addCar(Car car) {
    	
//    	query to insert car into car table
        String sql = "INSERT INTO car(carid,brand,model,basePrice,isAvailable) VALUES(?,?,?,?,?)";
        
        // Create database connection and prepared statement
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnect.getConnection();  // Get connection from DBConnect class
            ps = conn.prepareStatement(sql);     // Prepare the SQL statement
            
         // Set values for query parameter
            ps.setString(1, car.getCarId());
            ps.setString(2, car.getBrand());
            ps.setString(3, car.getModel());
            ps.setDouble(4, car.getBasePrice());
            ps.setBoolean(5, true);  // new car is available by default

            ps.executeUpdate(); // execute the insert query
            
            System.out.println("Car added successfully: " + car.getBrand() + " " + car.getModel()); //print the car added msg
        } catch (SQLException e) {
            System.out.println("!!! Error adding car: " + e.getMessage());  //print the error msg if car operation fails
        } finally {
            try { 
            	// Close resources to avoid memory leaks
            	if (ps != null) 
            		ps.close(); 
            	if (conn != null) 
            		conn.close();
            	}
            catch (SQLException ex) { 
            	ex.printStackTrace();
            	}
        }
    }

    //method to add customer
    public void addCustomer(Customer customer) {
    	
    	//query to insert customer details
        String sql = "INSERT INTO customers (customer_id, name) VALUES (?, ?)";
        
//        create db connection and prepared statement
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);
            
//            set value for query parameter
            ps.setString(1, customer.getCustId());
            ps.setString(2, customer.getName());
            
            ps.executeUpdate(); //execute the insert query

            System.out.println("Customer added successfully: " + customer.getName()); //print msg of success with customer name
        } catch (SQLException e) {
            System.out.println("!!! Error adding customer: " + e.getMessage()); 
        } finally {
            try { 
//            	close connection to prevent data loss
            	if (ps != null) 
            		ps.close(); 
            	if (conn != null) 
            		conn.close(); 
            	}
            catch (SQLException ex) { 
            	ex.printStackTrace(); 
            	}
        }
    }

//    method to rent a car
    public void rentCar(String carId, String custId, int days) {
    	
//    	validation 
    	 if(days <= 0){
    	        System.out.println("Invalid rental days.");
    	        return;
    	    }
    	carId = carId.toUpperCase();  //to avoid case sensitivity
    	
//    	to create db connection and ps
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            conn = DBConnect.getConnection();
             
         // Check if the car is available
            String checkSql = "SELECT isAvailable FROM car WHERE carid=?";
            ps1 = conn.prepareStatement(checkSql);
            ps1.setString(1, carId);

            ResultSet rs = ps1.executeQuery();

            // If car exists and is available
            if (rs.next() && rs.getBoolean("isAvailable")) {

            	// To Insert rental record
                String rentSql = "INSERT INTO rentals(carid,custid,days) VALUES(?,?,?)";
                ps2 = conn.prepareStatement(rentSql);
                ps2.setString(1, carId);
                ps2.setString(2, custId);
                ps2.setInt(3, days);
                ps2.executeUpdate();

             //To Update car availability to false
                String updateSql = "UPDATE car SET isAvailable=FALSE WHERE carid=?";
                PreparedStatement ps3 = conn.prepareStatement(updateSql);
                ps3.setString(1, carId);
                ps3.executeUpdate();
                ps3.close();

                System.out.println("Car rented successfully!");
            } else {
                System.out.println("XXX Car is not available or does not exist.");
            }

        } catch (SQLException e) {
            System.out.println("!!! Error renting car: " + e.getMessage());
        } finally {
            try { 
            	if (ps2 != null) 
            		ps2.close(); 
            	if (conn != null) 
            		conn.close(); 
            	}
            catch (SQLException ex) { 
            	ex.printStackTrace(); 
            	}
        }
    }
    
 // Method to return a rented car
    public void returnCar(String carId) {
    	
        carId = carId.toUpperCase();

        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            conn = DBConnect.getConnection();

         // To Check if this car is currently rented
            String checkSql = "SELECT * FROM rentals WHERE carid=? AND return_date IS NULL";
            ps1 = conn.prepareStatement(checkSql);
            ps1.setString(1, carId);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {

            	 // To Update return date
                String updateRental = "UPDATE rentals SET return_date = NOW() WHERE carid=? AND return_date IS NULL";
                ps2 = conn.prepareStatement(updateRental);
                ps2.setString(1, carId);
                ps2.executeUpdate();

                // To make car available again
                String updateSql = "UPDATE car SET isAvailable=TRUE WHERE carid=?";
                PreparedStatement ps3 = conn.prepareStatement(updateSql);
                ps3.setString(1, carId);
                ps3.executeUpdate();
                ps3.close();

                System.out.println("Car returned successfully!");

            } else {
                System.out.println("This car is not currently rented.");
            }

            rs.close();

        } catch (SQLException e) {
            System.out.println("Error returning car: " + e.getMessage());
        } finally {
            try {
                if (ps1 != null) 
                	ps1.close();
                if (ps2 != null) 
                	ps2.close();
                if (conn != null) 
                	conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    

    // Method to display all available cars
    public void showAvailableCars() {
        String sql = "SELECT * FROM car WHERE isAvailable = TRUE";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            System.out.println("\n*** Available Cars ***");
            
            //To loop through result set and print cars
            while (rs.next()) {
            	// Retrieve data from database columns and print
                System.out.println(rs.getString("carid") + " - " +
                        rs.getString("brand") + " " +
                        rs.getString("model") + " (₹" +
                        rs.getDouble("basePrice") + "/day)");
            }

        } catch (SQLException e) {
            System.out.println("!!! Error fetching available cars: " + e.getMessage());
        } finally {
            try { 
            	if (rs != null) 
            		rs.close(); 
            	if (stmt != null) 
            		stmt.close(); 
            	if (conn != null) 
            		conn.close(); 
            	}
            catch (SQLException ex) { 
            	ex.printStackTrace(); 
            	}
        }
    }

    
//   method to search the car by brand
    public void searchCarByBrand(String brand) {

        String sql = "SELECT * FROM car WHERE brand ILIKE ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, "%" + brand + "%");   // Set search value with wildcard (%)

            rs = ps.executeQuery();

            // Loop through results and display cars
            while (rs.next()) {

                System.out.println(
                        rs.getString("carid") + " - " +
                        rs.getString("brand") + " " +
                        rs.getString("model") +
                        " (₹" + rs.getDouble("baseprice") + "/day)"
                );
            }
        }catch (SQLException e) {
            System.out.println("Error searching car: " + e.getMessage());
        }
        finally {
            try {
                if(ps != null) 
                	ps.close();
                if(conn != null) 
                	conn.close();
                if(rs != null) 
                	rs.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    
//    method to show total revenue
    public void showTotalRevenue() {
    	
//    	query to calculate the revenue
        String sql = "SELECT SUM(r.days * c.baseprice) AS total_revenue " +
                "FROM rentals r JOIN car c ON r.carid = c.carid " +
                "WHERE r.return_date IS NOT NULL";

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            // Check if result exists
            if (rs.next()) {
                double revenue = rs.getDouble("total_revenue"); // Retrieve total revenue value
                if (rs.wasNull()) revenue = 0.0;   // If result is null then set revenue to 0

                System.out.printf("\n💰 Total Revenue: ₹%.2f%n", revenue);
            }

        } catch (SQLException e) {
            System.out.println("!!! Error fetching revenue: " + e.getMessage());
        } finally {
            try { 
            	if (rs != null) 
            		rs.close(); 
            	if (stmt != null) 
            		stmt.close(); 
            	if (conn != null) 
            		conn.close(); 
            	}
            catch (SQLException ex) { 
            	ex.printStackTrace(); 
            	}
        }
    }

//    method to login after this menu will appear
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    public void login() {

        System.out.println("===== ADMIN LOGIN REQUIRED =====");
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

//        validation
        // validation
        if(username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("Admin login successful!");
            menu();
        } else {
            System.out.println("Invalid credentials! Try again.");
            login();
        }
    }
    
//    method to customer rental details
    public void showCustomerRentalDetails(String custId) {

//    	query to get rental details
        String sql = "SELECT c.brand, c.model, r.days, (r.days * c.basePrice) AS total " +
                     "FROM rentals r " +
                     "JOIN car c ON r.carid = c.carid " +
                     "WHERE r.custid=?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnect.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, custId);

            rs = ps.executeQuery();

            // Loop through results
            while (rs.next()) {
            	// Print rental details
                System.out.println("Customer rented " + rs.getString("brand") + " "
                        + rs.getString("model") + " for "
                        + rs.getInt("days") + " days. Total: ₹"
                        + rs.getDouble("total"));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching customer rentals: " + e.getMessage());
        }
    }
    // MENU  
    public void menu() {

    	 // Infinite loop until user exits
        while (true) {
        	 // Display menu options
            System.out.println("\n===== CAR RENTAL SYSTEM =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Show Available Cars");
            System.out.println("4. Search Car by Brand");
            System.out.println("5. Show Total Revenue");
            System.out.println("6. Exit");
            
            System.out.print("Enter choice: "); // Ask user for choice

            String choiceInput = sc.nextLine();  // Read user input

//            validation of menu choice(must be number)
            if (!choiceInput.matches("\\d+")) {
                System.out.println("!!! Invalid input! Please enter a number.");
                continue;
            }

            int choice = Integer.parseInt(choiceInput);  // Convert input to integer

//            if you choose 1st option it will ask for your name and then display available cars further it will ask for car id and 
//            no. of days you want to rent a car and finally print the car rented msg. 
            if (choice == 1) {
                System.out.print("Enter your name: ");
                String name = sc.nextLine();

//                validation
                if (name.trim().isEmpty()) {
                    System.out.println("X Name cannot be empty.");
                    continue;
                }

//                this will generate random customer id
                String custId = "CUS" + System.currentTimeMillis();

                try {
                    addCustomer(new Customer(custId, name));
                } catch (IllegalArgumentException ex) {
                    System.out.println("x " + ex.getMessage());
                    continue;
                }

                showAvailableCars();

                System.out.print("\nEnter Car ID: ");
                String carId = sc.nextLine().toUpperCase();

//                validation
                if (carId.trim().isEmpty()) {
                    System.out.println("X Car ID cannot be empty.");
                    continue;
                }

                System.out.print("Enter rental days: ");
                String daysInput = sc.nextLine();

//                validation no. of days must be a positive number
                if (!daysInput.matches("\\d+")) {
                    System.out.println("X Days must be a valid positive number.");
                    continue;
                }

//                validation no. of days should be greater than 0
                int days = Integer.parseInt(daysInput);
                if(days <= 0){
                    System.out.println("X Rental days must be greater than 0.");
                    continue;
                }

                try {
                    rentCar(carId, custId, days);
                } catch (IllegalArgumentException ex) {
                    System.out.println("X " + ex.getMessage());
                }

            } 
//            if choice 2 is selected it will ask for the car id you want to return and the car will be returned successfully
            else if (choice == 2) {
                System.out.print("Enter Car ID to return: ");
                String carId = sc.nextLine().toUpperCase();

                if (carId.trim().isEmpty()) {
                    System.out.println("X Car ID cannot be empty.");
                    continue;
                }

                returnCar(carId);

            } 
//             if  choice 3 selected it will show the available cars
            else if(choice == 3){
                showAvailableCars();
            }
            
//            if choice 4 selected it will ask for the brand name that you want to search
            else if(choice == 4){
                System.out.print("Enter brand name: ");
                String brand = sc.nextLine();
                searchCarByBrand(brand);
            }
 
//            if choice 5 selected it will show the total revenue of the rented car
            else if(choice == 5){
                showTotalRevenue();
            }

//            choice 6 will exit from the loop 
            else if(choice == 6){
                break;
            }
        }

        sc.close();
    }
}

