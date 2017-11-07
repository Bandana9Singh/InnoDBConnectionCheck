import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;


public class InnoConnection {

	/**
	 * @param args
	 * @author Bandana Singh
	 */
	public static void main(String[] args) throws Exception{
		int choice = 1;
		Scanner scanner = new Scanner(System.in);
		int inputNum;
		
		//Setting connection with MySQl server
		Connection conn = getConnections();
		
		while(choice == 1) {
			System.out.println("Enter your choice of operation:\n" +
					"1. Get the default engine\n" +
					"2. Get the disk write by MySQL\n" + 
					"3. Get name of all engines running\n" +
					"4. Get name of all tables running on InnoDB\n");
			inputNum = Integer.parseInt(scanner.nextLine());
			//Switch case dependent on input number
			switch(inputNum) {
				case 1 : /**
					 	  * Function call to get the default engine name
					 	  */
						getDefaultEngine(conn);
				case 2 : /**
						  * Function call to disk write by MySql
						  */
						getDiskWrite(conn);
						break;
				case 3 : /**
					  	  * Function call to get name of all the engines running
					  	  */
						getEngines(conn);
						break;
				case 4 : /**
				  	      * Function call to get all the tables that run on InnoDB engine.
				  	      */
						getInnoTables(conn);
						break;
				default : 
						choice = 0;
			}
			System.out.println("Do you want to continue??? \t 1 to continue, 0 to exit\n");
			choice = Integer.parseInt(scanner.nextLine()); 
		}
		System.out.println("Out of program");
	}
	
	/** 
	 * @return
	 * @throws Exception
	 * This function sets the connection with MySQL server
	 */
	public static Connection getConnections() throws Exception {
		try {
			/* url of the database, if using localhost.
			 * use Ip address instead of localhost if, database is hosted elsewhere 
			 */
			String url = "jdbc:mysql://localhost:3306";
			//JDBC driver 
			String driver = "com.mysql.jdbc.Driver";
			String username = "root";
			String password = "root";
			Class.forName(driver);
			
			Connection con = DriverManager.getConnection(url, username, password);
			System.out.println("Connected to MySQL");
			return con;
		}	catch (Exception ex) {
			System.out.println(ex);
		}
		return null;
	}
	/**
	 * @throws Exception
	 * This function gets default engine name
	 */
	public static void  getDefaultEngine(Connection con) throws Exception {
		try {
			//Prepare statement with SQL query
			PreparedStatement statement = con.prepareStatement("select @@default_storage_engine");
			ResultSet result = statement.executeQuery();
			//As long as there is a result
			while(result.next()) {
				//can give either a number or the column name in result.getString
				System.out.println("Dafault storage engine: "+ result.getString("@@default_storage_engine"));
			}
		}	catch(Exception ex) {
			System.out.println("Exception from getDefaultEngine method" + ex);
		}
	}
	
	/*
	 * @throws Exception
	 * Get disc write by MySQL
	 */
	public static void  getDiskWrite(Connection con) throws Exception {
		try {
			//Prepare statement with SQL query
			PreparedStatement statement = con.prepareStatement("show global status");
			ResultSet result = statement.executeQuery();
			//As long as there is a result
			while(result.next()) {
				if(result.getString(1).equals("Handler_write")) {
					System.out.println("Disk write by SQL:" + result.getString(2));
				}
			}
		}	catch(Exception ex) {
			System.out.println("Exception from getDiskWrite method" + ex);
		}
	}
	
	/*
	 * @throws Exception
	 * Get list of all engines running on MySQL
	 */
	public static void  getEngines(Connection con) throws Exception {
		try {
			//Prepare statement with SQL query
			PreparedStatement statement = con.prepareStatement("show engines");
			ResultSet result = statement.executeQuery();
			//As long as there is a result
			while(result.next()) {
				//can give either a number or the column name in result.getString
				System.out.println(result.getString(1));
			}
		}	catch(Exception ex) {
			System.out.println("Exception from getEngines method" + ex);
		}
	}
	
	/*
	 * @throws Exception
	 * Get list of tables using InnoDB engine
	 */
	public static void  getInnoTables(Connection con) throws Exception {
		try {
			//Prepare statement with SQL query
			PreparedStatement statement = con.prepareStatement("select table_name from INFORMATION_SCHEMA.TABLES where engine='InnoDB'");
			ResultSet result = statement.executeQuery();
			//As long as there is a result
			while(result.next()) {
				//can give either a number or the column name in result.getString
				System.out.println(result.getString("table_name"));
			}
		}	catch(Exception ex) {
			System.out.println("Exception from getInnoTables method" + ex);
		}
	}
}
