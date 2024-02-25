# Book Cloud: A Java Application with a MySQL RDBMS

<h2>What is Book Cloud?</h2>
Book Cloud is a an Admin Control Panel for and online bookstore. It is a Java application with a relational database running inside a MySQL Workbench. Once setup is complete, a user can can access the online bookstore's database by selecting commands the the command line GUI interface. The interface provides the following fuctionality:

  * View Users
  * Add User
  * Edit User
  * Remove User
  * View Books
  * Add Book
  * Edit Book
  * Remove Book
  * View Orders
  * Search for all Orders of a User
  * Exit

<h2>Getting Started</h2>

The following text will provide intstructions to create and connect a database for this Java application, Book Cloud, using MySQL and JDBC. The setup will proivde access to the Admin Control Center for the Book Cloud application. This control center utilizes and manipulates data from the connected database. To ensure proper setup of the Java application, the following tools will be needed:
  * An instance of MySQLWorkbench to create and manipulate a database
  * A Java Database Connector (JDBC), provided as .jar file here: https://dev.mysql.com/downloads/connector/j/
    * A JDBC is inlcuded in the github repo as part of the project in `./BookCloud/lib`
  * A command line to run the application from (this will also act as the GUI)

<h2>Verify Contents of Cloned Repository</h2>

The repository can be cloned from here: https://github.com/mdoran3/BookCloud

Once you have cloned the repository, verify that it contains the following entities:

 - [x]  `BookCLoud` directory (parent)
 
 - [x]  `README.md` file inside the parent `BookCloud` directory 
   
 - [x]  3 additional driectories should be contained in the `BookCloud` directory: `lib`, `mysql`, `src`
   
 - [x]  The `lib` directory contains: `mysql-connector-j-8.3.0.jar` (this is the jdbc driver)
   
 - [x]  The `mysql` directory contains 2 files: `create.sql` and `insert_data.sql`
   
 - [x]  The `src` directory contains a Java source code file: `BookCloud.java`

<h2>Instructions</h2>

* Clone the repo from: https://github.com/zhintze/SER322-Application

* Open and instance or your MySQLWorkbench and login.

* In your MySQLWorkbench, you will load two files. These files are located in the repo at ./BookCloud/mysql.

* First, load create.sql in the MySQLWorkbench and execute (this will create the database schema and create the tables).

* Second, load insert-data.sql and execute to insert all of the data. 

* With the MySQLWorkbench open and running, navigate back to the repo and do the following:

* `cd` into the BookCloud directory

* Compile:
`javac -cp ./lib/mysql-connector-j-8.3.0.jar ./src/BookCloud.java`

* Run:
`java -cp ./lib/mysql-connector-j-8.3.0.jar ./src/BookCloud.java jdbc:mysql://localhost:3306/BookCloud <user> <password> com.mysql.cj.jdbc.Driver`
  * NOTE: This is in the format: `java -cp ./path/to/JDBC(.jar) ./src/BookCloud.java <url> <user> <password> <driver>`

* NOTE:
  * The `<url>` for your instance of the MySQLWorkbench may vary
    * In the example above the `<url>` is: `jdbc:mysql://localhost:3306/BookCloud`
  * For `<user>` please enter the user name to your specific MySQLWorkbench
  * For `<password>` please enter the password specific to your MySQLWorkbench
  * For `<driver>`, enter the name of your speicific JDBC .jar file (driver)
      * In the example above the `<driver>` is: `com.mysql.cj.jdbc.Driver`

* From here you will be prompted with a GUI interface on the command line. Select an integer for a command you would like to run and follow the on-screen instructions.
