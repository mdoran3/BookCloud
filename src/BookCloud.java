/*
 * Group 2
 * Alex Allen
 * Timothy Awotunde
 * Mitchell Doran
 * Zachariah Hintze
 * 
 * SER322
 * Deliverable 4: Application Implementation
 * 
 * 
 * 
 * Application:
 * ----------------
 * Book Cloud Admin Control Center 
 * 
 */



import java.sql.*;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;

public class BookCloud {
    private Connection conn;
    private static Scanner scanner = new Scanner(System.in);

    public BookCloud(String url, String user, String pwd, String driver) throws SQLException {
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pwd);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    /* ----------------------------------------
     *             VIEW USER
     * ----------------------------------------           
     */
    public void viewUsers() {
    	try {
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT userName, firstName, lastName, emailAddress, phoneNumber, registrationDate, streetNo, streetName, city, state, zipCode FROM user");
            System.out.printf("%-20s%-20s%-30s%-20s%-20s%n","User Name","Legal Name","Email","Phone Number","Address");
            System.out.printf("%-20s%-20s%-30s%-20s%-20s%n","---------","----------","-----","------------","-------");
            while (rs.next()) {
            	String fullname = rs.getString("firstName") + " " + rs.getString("lastName");
                System.out.printf("%-20s%-20s%-30s%-20s%s %s %s, %s %s%n", rs.getString("userName"),fullname,rs.getString("emailAddress"),rs.getString("phoneNumber"),rs.getString("streetNo"),rs.getString("streetName"),rs.getString("city"),rs.getString("state"),rs.getString("zipCode"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    /* ----------------------------------------
     *             ADD USER
     * ----------------------------------------           
     */
    
    public void addUser() {
    	String userName = "";
    	String password = "";
    	String firstName = "";
    	String lastName = "";
    	String emailAddress = "";
    	String phoneNumber = "";
    	String registrationDate = LocalDate.now().toString();
    	int streetNo = 0;
    	String streetName = "";
    	String city = "";
    	String state = "";
    	int zipCode = 0;
    	
    	System.out.println("\n\033[1;32m|-----------|\033[0m");
        System.out.println("\033[1;32m| Add User: |\033[0m");
        System.out.println("\033[1;32m|-----------|\033[0m\n");
        
    	boolean isValidInput = false;
    	
    	
    	while (isValidInput == false) {
    		System.out.println("\033[1;34mCreate a unique userName:\033[0m");
    		
    		userName = scanner.nextLine();
            try {
                // Check if the userName already exists
                PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM user WHERE userName = ?");
                checkStmt.setString(1, userName);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    System.out.println("\033[1;31mUsername already exists! Please enter a unique username.\033[0m");
                } else {
                    isValidInput = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    	isValidInput = false;
    	
    	System.out.println("\n\033[1;34mCreate password:\033[0m");
    	password = scanner.nextLine();
    	
    	System.out.println("\n\033[1;34mEnter firstName:\033[0m");
    	firstName = scanner.nextLine();
    	System.out.println("\n\033[1;34mEnter lastName:\033[0m");
    	lastName = scanner.nextLine();
    	
    	
    	while (!isValidInput) {
    		//validate email address @
    		System.out.println("\n\033[1;34mEnter Email Address:\033[0m");
    		emailAddress = scanner.nextLine();
    		
    		//search for @ symbol
    		for (int i = 0; i < emailAddress.length(); i++) {
                char ch = emailAddress.charAt(i);
                if (ch == '@') {
                	isValidInput = true;
                    break; // Exit the loop if '@' is found
                }
            }
    		if (isValidInput == false) {
    			System.out.println("\033[1;31mInvalid email address format. Please enter a full email address.\033[0m");
    		}
    	}
    	isValidInput = false;
    	
    	
    	
    	while (!isValidInput) {
            try {
                // Validate phone number format
                System.out.println("\n\033[1;34mEnter phone number in the format XXXXXXXXXX:\033[0m");
                phoneNumber = scanner.next();

                if (phoneNumber.matches("\\d{10}")) {
                    isValidInput = true;
                } else {
                    System.out.println("\033[1;31mInvalid phone number format. Please enter a 10-digit number.\033[0m");
                }
            } catch (InputMismatchException e) {
                System.out.println("\033[1;31mInput is not a valid string. Please enter a valid string.\033[0m");
                scanner.nextLine(); 
            }
        }
    	isValidInput = false;
    	
        
        while (!isValidInput) {
            try {
            	System.out.println("\n\033[1;34mEnter Street Number:\033[0m");
                streetNo = scanner.nextInt();
                scanner.nextLine(); 
                isValidInput = true; 
            } catch (InputMismatchException e) {
                System.out.println("\033[1;31mInput is not an integer. Please enter a valid integer.\033[0m");
                scanner.nextLine(); 
            }
        }
        isValidInput = false;
        
        
        System.out.println("\n\033[1;34mEnter Street Name:\033[0m");
    	streetName = scanner.nextLine();
    	System.out.println("\n\033[1;34mEnter City:\033[0m");
    	city = scanner.nextLine();
    	
    	while (!isValidInput) {
            System.out.println("\n\033[1;34mEnter State (2 characters):\033[0m");
            state = scanner.nextLine();
            if (state.length() == 2) {
                isValidInput = true;
            } else {
                System.out.println("\033[1;31mState must be exactly two characters long.\033[0m");
            }
        }
    	
    	isValidInput = false;
    	while (!isValidInput) {
            try {
                // Validate phone number format
                System.out.println("\n\033[1;34mEnter Zip Code:\033[0m");
                zipCode = scanner.nextInt();
                scanner.nextLine(); 
                if (String.valueOf(zipCode).length() == 5) {
                    isValidInput = true;
                } else {
                    System.out.println("Invalid phone number format. Please enter a 5-digit Zip Code.\033[0m");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInput is not a valid string. Please enter a valid string.\033[0m");
                scanner.nextLine(); 
            }
        }
    	isValidInput = false;
    	
    	
    	
    	try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO user (userName, password, firstName, lastName, emailAddress, phoneNumber, registrationDate, streetNo, streetName, city, state, zipCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            conn.setAutoCommit(false);
            
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            pstmt.setString(3, firstName);
            pstmt.setString(4, lastName);
            pstmt.setString(5, emailAddress);
            pstmt.setString(6, phoneNumber);
            pstmt.setString(7, registrationDate);
            pstmt.setInt(8, streetNo);
            pstmt.setString(9, streetName);
            pstmt.setString(10, city);
            pstmt.setString(11, state);
            pstmt.setInt(12, zipCode);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully added user!");
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    /* ----------------------------------------
     *             EDIT USER
     * ----------------------------------------           
     */
    public void editUser() {
        System.out.println("\n\033[1;32m|------------|\033[0m");
        System.out.println("\033[1;32m| Edit User: |\033[0m");
        System.out.println("\033[1;32m|------------|\033[0m\n");

        String usernameToEdit = "";
        
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.println("\033[1;34mEnter the username of the user you want to edit=):\033[0m");
            String input = scanner.nextLine();
            try {
            	usernameToEdit = input;
                isValidInput = true;
            } catch (NumberFormatException e) {
            	System.out.println("\033[1;31mUsername in invalid format! Please enter a valid username.\033[0m");
            }
        }
        
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user WHERE userName = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            conn.setAutoCommit(false);
            pstmt.setString(1, usernameToEdit);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\033[1;31mUser not found!\033[0m");
                return;
            }

            // Display existing user information
            System.out.println("\033[1;34mExisting User Information:\033[0m");
            System.out.printf("%-20s%-20s%-20s%-20s%-30s%-20s%-20s%-20s%-20s%-20s%-20s%n",
                    "User Name", "Password", "First Name", "Last Name", "Email Address", "Phone Number",
                    "Street Number", "Street Name", "City", "State", "Zip Code");
            System.out.printf("%-20s%-20s%-20s%-20s%-30s%-20s%-20s%-20s%-20s%-20s%-20s%n",
                    rs.getString("userName"), rs.getString("password"), rs.getString("firstName"),
                    rs.getString("lastName"), rs.getString("emailAddress"), rs.getString("phoneNumber"),
                    rs.getInt("streetNo"), rs.getString("streetName"), rs.getString("city"),
                    rs.getString("state"), rs.getInt("zipCode"));

            isValidInput = false;
            while (!isValidInput) {
                System.out.println("\n\033[1;34mSelect the attribute you want to edit (or press Enter to skip):\033[0m");
                System.out.println("1. Password");
                System.out.println("2. First Name");
                System.out.println("3. Last Name");
                System.out.println("4. Email Address");
                System.out.println("5. Phone Number");
                System.out.println("6. Street Number");
                System.out.println("7. Street Name");
                System.out.println("8. City");
                System.out.println("9. State");
                System.out.println("10. Zip Code");
                System.out.println("0. Exit");

                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        System.out.println("\033[1;34mNew Password:\033[0m");
                        rs.updateString("password", scanner.nextLine());
                        break;
                    case "2":
                        System.out.println("\033[1;34mNew First Name:\033[0m");
                        rs.updateString("firstName", scanner.nextLine());
                        break;
                    case "3":
                        System.out.println("\033[1;34mNew Last Name:\033[0m");
                        rs.updateString("lastName", scanner.nextLine());
                        break;
                    case "4":
                        System.out.println("\033[1;34mNew Email Address:\033[0m");
                        rs.updateString("emailAddress", scanner.nextLine());
                        break;
                    case "5":
                        System.out.println("\033[1;34mNew Phone Number:\033[0m");
                        rs.updateString("phoneNumber", scanner.nextLine());
                        break;
                    case "6":
                        System.out.println("\033[1;34mNew Street Number:\033[0m");
                        rs.updateInt("streetNo", Integer.parseInt(scanner.nextLine()));
                        break;
                    case "7":
                        System.out.println("\033[1;34mNew Street Name:\033[0m");
                        rs.updateString("streetName", scanner.nextLine());
                        break;
                    case "8":
                        System.out.println("\033[1;34mNew City:\033[0m");
                        rs.updateString("city", scanner.nextLine());
                        break;
                    case "9":
                        System.out.println("\033[1;34mNew State:\033[0m");
                        rs.updateString("state", scanner.nextLine());
                        break;
                    case "10":
                        System.out.println("\033[1;34mNew Zip Code:\033[0m");
                        rs.updateInt("zipCode", Integer.parseInt(scanner.nextLine()));
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("\033[1;31mInvalid choice. Please select a valid option.\033[0m");
                        continue;
                }
                isValidInput = true;
            }
            rs.updateRow();
            System.out.println("\n\033[1;32mUser updated successfully!\033[0m");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    /* ----------------------------------------
     *             REMOVE USER
     * ----------------------------------------           
     */
    public void removeUser() {
        System.out.println("\n\033[1;32m|--------------|\033[0m");
        System.out.println("\033[1;32m| Remove User: |\033[0m");
        System.out.println("\033[1;32m|--------------|\033[0m\n");

        String usernameToRemove = "";
        
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.println("\033[1;34mEnter the username of the user you want to remove:\033[0m");
            String input = scanner.nextLine();
            try {
            	usernameToRemove = input;
                isValidInput = true;
            } catch (NumberFormatException e) {
                System.out.println("\033[1;31mUsername in invalid format. Please enter a valid String for the username.\033[0m");
            }
        }
        
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM user WHERE userName = ?");
            conn.setAutoCommit(false);
            pstmt.setString(1, usernameToRemove);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("\033[1;32mUser removed successfully!\033[0m");
                conn.commit();
            } else {
                System.out.println("\033[1;31mUser not found!\033[0m");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /* ----------------------------------------
     *             ADD BOOK
     * ----------------------------------------           
     */
    public void addBook() {
        long ISBN = 0;
        String title = "";
        String author = "";
        String genre = "";
        int publicationYear = 0;
        String price = "";
        int availableQuantity = 0;

        System.out.println("\n\033[1;32m|-----------|\033[0m");
        System.out.println("\033[1;32m| Add Book: |\033[0m");
        System.out.println("\033[1;32m|-----------|\033[0m\n");

        boolean isValidInput = false;

        while (!isValidInput) {
            try {
                System.out.println("\033[1;34mEnter ISBN:\033[0m");
                ISBN = scanner.nextLong();
                scanner.nextLine();
                
                // Check if the ISBN already exists
                PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM book WHERE ISBN = ?");
                checkStmt.setLong(1, ISBN);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    System.out.println("\033[1;31mISBN already exists! Please enter a unique ISBN.\033[0m");
                } else {
                    isValidInput = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("\033[1;31mInput is not an integer. Please enter a valid integer.\033[0m");
                scanner.nextLine();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        isValidInput = false;

        System.out.println("\n\033[1;34mEnter Title:\033[0m");
        title = scanner.nextLine();
        System.out.println("\n\033[1;34mEnter Author:\033[0m");
        author = scanner.nextLine();
        System.out.println("\n\033[1;34mEnter Genre:\033[0m");
        genre = scanner.nextLine();

        while (!isValidInput) {
            try {
                System.out.println("\n\033[1;34mEnter Publication Year:\033[0m");
                publicationYear = scanner.nextInt();
                scanner.nextLine();
                isValidInput = true;
            } catch (InputMismatchException e) {
                System.out.println("\033[1;31mInput is not an integer. Please enter a valid integer.\033[0m");
                scanner.nextLine();
            }
        }
        isValidInput = false;

        while (!isValidInput) {
            try {
                System.out.println("\n\033[1;34mEnter Price:\033[0m");
                price = scanner.nextLine();
                isValidInput = true;
            } catch (InputMismatchException e) {
                System.out.println("\033[1;31mInput is not valid. Please enter a valid String.\033[0m");
                scanner.nextLine();
            }
        }
        isValidInput = false;

        while (!isValidInput) {
            try {
                System.out.println("\n\033[1;34mEnter Available Quantity:\033[0m");
                availableQuantity = scanner.nextInt();
                scanner.nextLine();
                isValidInput = true;
            } catch (InputMismatchException e) {
                System.out.println("\033[1;31mInput is not an integer. Please enter a valid integer.\033[0m");
                scanner.nextLine();
            }
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO book (ISBN, title, author, genre, publicationYear, price, availableQuantity) VALUES (?, ?, ?, ?, ?, ?, ?)");
            conn.setAutoCommit(false);
            pstmt.setLong(1, ISBN);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setString(4, genre);
            pstmt.setInt(5, publicationYear);
            pstmt.setString(6, price);
            pstmt.setInt(7, availableQuantity);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Successfully added book!");
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    /* ----------------------------------------
     *             EDIT BOOK
     * ----------------------------------------           
     */
    public void editBook() {
        System.out.println("\n\033[1;32m|--------------|\033[0m");
        System.out.println("\033[1;32m|  Edit Book:  |\033[0m");
        System.out.println("\033[1;32m|--------------|\033[0m\n");

        long isbnToEdit = 0;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.println("\033[1;34mEnter the ISBN of the book you want to edit:\033[0m");
            String input = scanner.nextLine();
            try {
                isbnToEdit = Long.parseLong(input);
                isValidInput = true;
            } catch (NumberFormatException e) {
                System.out.println("\033[1;31mInvalid input. Please enter an integer for ISBN.\033[0m");
                return;
            }
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM book WHERE ISBN = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            conn.setAutoCommit(false);
            pstmt.setLong(1, isbnToEdit);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\033[1;31mBook not found!\033[0m");
                return;
            }

            // Display existing book information
            System.out.println("\033[1;34mExisting Book Information:\033[0m");
            System.out.printf("%-30s%-20s%-20s%-20s%-20s%-20s%-20s%n",
                    "ISBN", "Title", "Author", "Genre", "Publication Year", "Price", "Available Quantity");
            System.out.printf("%-30d%-20s%-20s%-20s%-20d%-20s%-20d%n",
                    rs.getLong("ISBN"), rs.getString("title"), rs.getString("author"),
                    rs.getString("genre"), rs.getInt("publicationYear"), rs.getString("price"),
                    rs.getInt("availableQuantity"));

            isValidInput = false;
            while (!isValidInput) {
                System.out.println("\n\033[1;34mSelect the attribute you want to edit:\033[0m");
                System.out.println("1. Title");
                System.out.println("2. Author");
                System.out.println("3. Genre");
                System.out.println("4. Publication Year");
                System.out.println("5. Price");
                System.out.println("6. Available Quantity");
                System.out.println("0. Exit");

                String choice = scanner.nextLine();
                // Handle edit options
                switch (choice) {
                    case "1":
                        System.out.println("\033[1;34mEnter new title:\033[0m");
                        String newTitle = scanner.nextLine();
                        rs.updateString("title", newTitle);
                        break;
                    case "2":
                        System.out.println("\033[1;34mEnter new author:\033[0m");
                        String newAuthor = scanner.nextLine();
                        rs.updateString("author", newAuthor);
                        break;
                    case "3":
                        System.out.println("\033[1;34mEnter new genre:\033[0m");
                        String newGenre = scanner.nextLine();
                        rs.updateString("genre", newGenre);
                        break;
                    case "4":
                        System.out.println("\033[1;34mEnter new publication year:\033[0m");
                        int newPublicationYear = Integer.parseInt(scanner.nextLine());
                        rs.updateInt("publicationYear", newPublicationYear);
                        break;
                    case "5":
                        System.out.println("\033[1;34mEnter new price:\033[0m");
                        String newPrice = scanner.nextLine();
                        rs.updateString("price", newPrice);
                        break;
                    case "6":
                        System.out.println("\033[1;34mEnter new available quantity:\033[0m");
                        int newAvailableQuantity = Integer.parseInt(scanner.nextLine());
                        rs.updateInt("availableQuantity", newAvailableQuantity);
                        break;
                    case "0":
                        return; // Exit the method
                    default:
                        System.out.println("\033[1;31mInvalid choice. Please enter a valid option.\033[0m");
                }
                isValidInput = true;
            }
            rs.updateRow();
            System.out.println("\n\033[1;32mBook updated successfully!\033[0m");
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /* ----------------------------------------
     *             VIEW BOOKS
     * ----------------------------------------           
     */
    public void viewBooks() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ISBN, title, author, genre, publicationYear, price, availableQuantity FROM book");

            System.out.printf("%-30s%-30s%-20s%-20s%-20s%-10s%-10s%n", "ISBN", "Title", "Author", "Genre", "Publication Year", "Price", "Available Quantity");
            System.out.printf("%-30s%-30s%-20s%-20s%-20s%-10s%-10s%n", "----", "-----", "------", "-----", "----------------", "-----", "-----------------");

            while (rs.next()) {
                System.out.printf("%-30d%-30s%-20s%-20s%-20d%-20s%-10d%n",
                        rs.getLong("ISBN"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"),
                        rs.getInt("publicationYear"),
                        rs.getString("price"),
                        rs.getInt("availableQuantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /* ----------------------------------------
     *             REMOVE BOOK
     * ----------------------------------------           
     */
    public void removeBook() {
        System.out.println("\n\033[1;32m|--------------|\033[0m");
        System.out.println("\033[1;32m| Remove Book: |\033[0m");
        System.out.println("\033[1;32m|--------------|\033[0m\n");

        System.out.println("\033[1;34mEnter the ISBN of the book you want to remove:\033[0m");
        long isbnToRemove = 0;
        boolean isValidInput = false;

        while (!isValidInput) {
            String input = scanner.nextLine();
            try {
                isbnToRemove = Long.parseLong(input);
                isValidInput = true;
            } catch (NumberFormatException e) {
                System.out.println("\033[1;31mInvalid input. Please enter an integer for ISBN.\033[0m");
                return;
            }
        }

        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM book WHERE ISBN = ?");
            conn.setAutoCommit(false);
            pstmt.setLong(1, isbnToRemove);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("\033[1;32mBook removed successfully!\033[0m");
                conn.commit();
            } else {
                System.out.println("\033[1;31mBook with the given ISBN not found!\033[0m");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    /* ----------------------------------------
     *             VIEW ORDERS
     * ----------------------------------------           
     */
    public void viewOrders() {
    	System.out.println("\n\033[1;32m|--------------|\033[0m");
        System.out.println("\033[1;32m| View Orders: |\033[0m");
        System.out.println("\033[1;32m|--------------|\033[0m\n");
        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT o.orderID, o.description, o.orderDate, p.paymentDate, p.paymentMethod, p.transactionStatus, u.userName " +
                    "FROM `order` o " +
                    "INNER JOIN pay_for pf ON o.orderID = pf.orderID " +
                    "INNER JOIN payment p ON pf.paymentID = p.paymentID " +
                    "INNER JOIN has h ON p.paymentID = h.paymentID " +
                    "INNER JOIN user u ON h.userName = u.userName");

            System.out.printf("%-10s%-20s%-20s%-20s%-20s%-20s%-20s%n",
                    "Order ID", "Description", "Order Date", "Payment Date", "Payment Method", "Transaction Status", "Created By");
            System.out.printf("%-10s%-20s%-20s%-20s%-20s%-20s%-20s%n",
                    "---------", "-----------", "----------", "------------", "--------------", "------------------", "----------");

            while (rs.next()) {
                System.out.printf("%-10d%-20s%-20s%-20s%-20s%-20s%-20s%n",
                        rs.getInt("orderID"), rs.getString("description"), rs.getString("orderDate"),
                        rs.getString("paymentDate"), rs.getString("paymentMethod"),
                        rs.getString("transactionStatus"), rs.getString("userName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void viewOrdersByUser() {
    	System.out.println("\033[1;32m|----------------------|\033[0m");
        System.out.println("\033[1;32m| View Orders by User: |\033[0m");
        System.out.println("\033[1;32m|----------------------|\033[0m\n");
        try {
        	
            String username = "";
            System.out.println("\033[1;34mEnter the userName associated with the users you want to view:\033[0m");
            username = scanner.nextLine();

            // Check if the username exists in the database
            PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM user WHERE userName = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int count = rs.getInt("count");
            if (count == 0) {
                System.out.println("\033[1;31mUser not found! Please enter a valid username.\033[0m");
                return;
            }    
            

            String query = "SELECT o.orderID, o.description, o.orderDate, p.paymentDate, p.paymentMethod, p.transactionStatus, u.userName " +
                           "FROM `order` o " +
                           "INNER JOIN pay_for pf ON o.orderID = pf.orderID " +
                           "INNER JOIN payment p ON pf.paymentID = p.paymentID " +
                           "INNER JOIN has h ON p.paymentID = h.paymentID " +
                           "INNER JOIN user u ON h.userName = u.userName " +
                           "WHERE u.userName = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            rs = stmt.executeQuery();

            System.out.printf("\n%-10s%-20s%-20s%-20s%-20s%-20s%-20s%n",
                    "Order ID", "Description", "Order Date", "Payment Date", "Payment Method", "Transaction Status", "Created By");
            System.out.printf("%-10s%-20s%-20s%-20s%-20s%-20s%-20s%n",
                    "---------", "-----------", "----------", "------------", "--------------", "------------------", "----------");

            while (rs.next()) {
                System.out.printf("%-10d%-20s%-20s%-20s%-20s%-20s%-20s%n",
                        rs.getInt("orderID"), rs.getString("description"), rs.getString("orderDate"),
                        rs.getString("paymentDate"), rs.getString("paymentMethod"),
                        rs.getString("transactionStatus"), rs.getString("userName"));
            }
            
            stmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
    	//checks for valid amount of command-line arguments
        if (args.length < 4) {
            System.out.println("Usage: java BookCloud <url> <user> <pwd> <driver>");
            return;
        }
        
        
        /*String url = "jdbc:mysql://localhost:3306/bookcloud";
        String user = "root";
        String pwd = "password";
        String driver = "com.mysql.cj.jdbc.Driver";*/
        
        String url = args[0];
        String user = args[1];
        String pwd = args[2];
        String driver = args[3];
        
        BookCloud bookCloud = null;
        
        
        try {
        	bookCloud = new BookCloud(url, user, pwd, driver);
            
            
            System.out.println("\033[1;34m************************************************************\033[0m");
            System.out.println("\033[1;34m*               Book Cloud Admin Control Center            *\033[0m");
            System.out.println("\033[1;34m************************************************************\033[0m");
            
            while (true) {
            	System.out.println("\n\033[1;32m|------------|\033[0m");
                System.out.println("\033[1;32m| Main Menu: |\033[0m");
                System.out.println("\033[1;32m|------------|\033[0m\n");
                System.out.println("\033[1;33m1. View Users\033[0m");
                System.out.println("\033[1;33m2. Add User\033[0m");
                System.out.println("\033[1;33m3. Edit User\033[0m");
                System.out.println("\033[1;33m4. Remove User\033[0m");
                System.out.println("\033[1;33m5. View Books\033[0m");
                System.out.println("\033[1;33m6. Add Book\033[0m");
                System.out.println("\033[1;33m7. Edit Book\033[0m");
                System.out.println("\033[1;33m8. Remove Book\033[0m");
                System.out.println("\033[1;33m9. View Orders\033[0m");
                System.out.println("\033[1;33m10. Search for all Orders of a User\033[0m");
                System.out.println("\033[1;33m0. Exit\033[0m");

                System.out.print("\033[1;34mEnter your choice: \033[0m");
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline

                switch (choice) {
                    case 0:
                        System.out.println("\033[1;31mExiting...\033[0m");
                        return;
                    case 1:
                        bookCloud.viewUsers();
                        break;
                    case 2:
                    	bookCloud.addUser();
                        break;
                    case 3:
                    	bookCloud.editUser();
                        break;
                    case 4:
                    	bookCloud.removeUser();
                        break;
                    case 5:
                    	bookCloud.viewBooks();
                        break;
                    case 6:
                    	bookCloud.addBook();
                        break;
                    case 7:
                    	bookCloud.editBook();
                        break;
                    case 8:
                    	bookCloud.removeBook();
                        break;
                    case 9:
                        bookCloud.viewOrders();
                        break;
                    case 10:
                        bookCloud.viewOrdersByUser();
                        break;
                    default:
                        System.out.println("\033[1;31mInvalid choice. Please try again.\033[0m");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }   
        
        
    }
}