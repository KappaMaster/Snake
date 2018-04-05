package application;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUse {

	final static String url = "jdbc:sqlite:Scores.db",
					    sql = "SELECT name,score FROM Scores ORDER BY score DESC",
					    create = "CREATE TABLE IF NOT EXISTS Scores (name TEXT, score INTEGER)";

	public static void add(String name, int score){

		try{
			Connection conn = DriverManager.getConnection(url);
			Statement statement = conn.createStatement();
			statement.execute(create);
			statement.execute("INSERT INTO Scores (name, score) VALUES ('" + name +"' , '" + score +"')");
			statement.close();
			conn.close();
		}
		catch (SQLException e){
			System.out.println("Something went wrong: " + e.getMessage());
		}

	}

	public static int getScore(int position){  //Modified code from here https://www.tutorialspoint.com/jdbc/jdbc-sorting-data.htm
		try{
			Connection conn = DriverManager.getConnection(url);
			ResultSet rs = conn.createStatement().executeQuery(sql);

			//loop to get to the correct node
			int i = 0;
			while(rs.next()&& i < position){
				i++;
			}

			//returning the value in the node
			int monika =  rs.getInt("score"); //Can't just return rs.getInt() because then you can't close the connection
			conn.close();

			return monika;

		}
		catch (SQLException e){
			System.out.println("Something went wrong: " + e.getMessage());
		}
		return 0; //default if that score position doesn't exist
	}

	public static String getName(int position){ //same code source as getScore
		try{
			Connection conn = DriverManager.getConnection(url);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			//loop to get to correct node
			int i = 0;
			while(rs.next()&& i < position){
				i++;
			}
			//returning the value in the node
			String monika = rs.getString("name"); //Can't just return rs.getInt() because then you can't close the connection
			conn.close();
			return monika;

		}
		catch (SQLException e){
			System.out.println("Something went wrong: " + e.getMessage());
		}
		return "N/A";// the default if that record doesn't exist
	}

	public static void initialize(){ //initializes database in the project folder
		try (Connection conn = DriverManager.getConnection(url)) {
			conn.createStatement().execute(create);
			conn.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

}
