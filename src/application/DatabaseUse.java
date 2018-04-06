package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUse {

	// URL Addresses
	private static ResultSet rs;
	private static Connection connection;
	private final static String url = "jdbc:sqlite:Scores.db",
								sql = "SELECT name,score FROM Scores ORDER BY score DESC";

	// Add record to Database and return rank (row number)
	public static int add(String name, int score){
		int rank = 0;			// Return Rank
		int oldScore = -1;		// Hold score of record if it already exists

		try{
			// Build Connection
			connection = DriverManager.getConnection(url);
			Statement statement = connection.createStatement();

			// Select record from database if name already exists
			rs = statement.executeQuery("Select score FROM Scores WHERE name = '" + name + "'");
			if (!rs.isClosed())		// If exists, save stored score to compare with new score
				oldScore = Integer.parseInt(rs.getString(1));

			// Record with largest score is added into the database (If a tie, new score is added below old)
			if (oldScore < score)
				statement.execute("REPLACE INTO Scores (name, score) VALUES ('" + name +"' , '" + score +"')");

			// Get Rank
			rs = statement.executeQuery(sql);
			while(rs.next())
				if (name.contentEquals(rs.getString("name")))		// If name = name in Database
					rank = rs.getRow();								// Rank = row number

			// Close connections
			statement.close();
			connection.close();
		}
		catch (SQLException e){
			System.out.println("Something went wrong: " + e.getMessage());
		}
		
		// Return Rank
		return rank;
	}

	// Get record from specific column at specified row in the Database
	public static String getRecord(int position, String column){  	// Modified code from here https://www.tutorialspoint.com/jdbc/jdbc-sorting-data.htm
		String record = "0";	// Hold record (default 0)

		try{
			// Establish Connection
			connection = DriverManager.getConnection(url);
			rs = connection.createStatement().executeQuery(sql);

			// Loop to get to the correct node
			int i = 0;
			while(rs.next() && i < position)
				i++;

			// Save Score
			record =  rs.getString(column);

			// Close Connection
			connection.close();
		}
		catch (SQLException e){
			System.out.println("Something went wrong: " + e.getMessage());
		}

		// Return record
		return record;
	}

	// Initialize Database, if it doesn't exist
	public static void initialize(){
		try{
			// Establish Connection
			connection = DriverManager.getConnection(url);
			
			// Create Database with unique names, if it doesn't already exist
			connection.createStatement().execute("CREATE TABLE IF NOT EXISTS Scores (name TEXT, score INTEGER)");
			connection.createStatement().execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_Scores_name ON Scores (name)");

			// Close Connection
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
