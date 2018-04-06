package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUse {

	// URL Addresses
	final static String url = "jdbc:sqlite:Scores.db",
					    sql = "SELECT name,score FROM Scores ORDER BY score DESC";

	// Add record to Database and return rank (row number)
	public static int add(String name, int score){
		int rank = 0;			// Return Rank
		int oldScore = -1;		// Hold score of record if it already exists

		try{
			// Build Connection
			Connection conn = DriverManager.getConnection(url);
			Statement statement = conn.createStatement();

			// Check if record with same name exists
			ResultSet set = statement.executeQuery("Select score FROM Scores WHERE name = '" + name + "'");

			// If exists, save stored score to compare with new score
			if (!set.isClosed())
				oldScore = Integer.parseInt(set.getString(1));

			// Record with largest score is added into the database
			if (oldScore < score)
				statement.execute("REPLACE INTO Scores (name, score) VALUES ('" + name +"' , '" + score +"')");


			// Get Rank (Loop until rank is found, or not found)
			set = statement.executeQuery(sql);
			while(set.next())
				if (name.contentEquals(set.getString("name")))		// If name = name in Database
					rank = set.getRow();							// Rank = row number

			// Close connections
			statement.close();
			conn.close();
		}
		catch (SQLException e){
			System.out.println("Something went wrong: " + e.getMessage());
		}

		return rank;
	}

	// Get record from specific column at specified row in the Database
	public static String getRecord(int position, String column){  	// Modified code from here https://www.tutorialspoint.com/jdbc/jdbc-sorting-data.htm
		String record = "0";							// Hold record (default 0)

		try{
			// Establish Connection
			Connection conn = DriverManager.getConnection(url);
			ResultSet rs = conn.createStatement().executeQuery(sql);

			// Loop to get to the correct node
			int i = 0;
			while(rs.next() && i < position)
				i++;

			// Save Score
			record =  rs.getString(column);

			// Close Connection
			conn.close();
		}
		catch (SQLException e){
			System.out.println("Something went wrong: " + e.getMessage());
		}

		return record;
	}

	// Initialize Database, if it doesn't exist
	public static void initialize(){
		try (Connection conn = DriverManager.getConnection(url)) {
			// Create Database
			conn.createStatement().execute("CREATE TABLE IF NOT EXISTS Scores (name TEXT, score INTEGER)");

			// Make the name column unique
			conn.createStatement().execute("CREATE UNIQUE INDEX IF NOT EXISTS idx_Scores_name ON Scores (name)");

			// Close Connection
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
