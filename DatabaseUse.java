import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUse {

	 public static void Add(String name, int score){ //I believe this was taken from Aladdin's example, it doesn't check if the same entry already exists because I don't think that's how it should work 
													//Just call this method after the person loses
													//system.out.println("Enter Name");
			
			try{
				Connection conn = DriverManager.getConnection("jdbc:sqlite:F:\\SQLite\\databases\\test.db");
				Statement statement = conn.createStatement();
				statement.execute("CREATE TABLE IF NOT EXISTS Scores (name TEXT, score INTEGER)");
				statement.execute("INSERT INTO Scores (name, score) VALUES ('" + name +"' , '" + score +"')");
				statement.close();
				conn.close();
				}catch (SQLException e){
					System.out.println("Something went wrong: " + e.getMessage());
				}
			
			}
	 
	public static void Print(){ //I have no idea how this works but you just replace the println with setting the textfields 
								//Method should be called when leaderboard is selected from main menu
								//I got it from here https://www.tutorialspoint.com/jdbc/jdbc-sorting-data.htm
		
		try{
			Connection conn = DriverManager.getConnection("jdbc:sqlite:F:\\SQLite\\databases\\test.db");
			Statement stmt = conn.createStatement();
			
			System.out.println("Fetching records in ascending order...");
			String sql = "SELECT name,score FROM Scores" +" ORDER BY score DESC";
			ResultSet rs = stmt.executeQuery(sql);
			
			int i = 0;
			while(rs.next()&& i < 5){
				//Retrieve by column name
				int id  = rs.getInt("score");
				String name = rs.getString("name");
				
				//Display values
				System.out.print("Score: " + id);
				System.out.println(", Name: " + name);
				i++; 
			}
			
			}catch (SQLException e){
				
				System.out.println("Something went wrong: " + e.getMessage());
			}
		
		}
	public static void Initialize(){
		try{
            /*accepts connection string and returns a connection instance. How do I know that? DOCUMENTATION! :)

            Instead of DriverManager, we can utilize Data Source Objects as well, if anyone writes a 200 word
            explanation of what that is and the difference between data source object and driver manager you will be
            awarded 1% as EC.

            If this database did not exist, the Driver Manager will create one for us.
             */
            Connection conn = DriverManager.getConnection("jdbc:sqlite:F:\\SQLite\\databases\\test.db"); 
            //change this to change file name, can navigate with GUI given in class 
            
            /* This is a statement object, recall that Java is an OOP language therefore we created an object
            statement and we will execute this instance of statement we have created wth a .execute method.
             */
            
            //creating the database http://www.sqlitetutorial.net/sqlite-java/create-database/
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
            conn.close();
		}catch (SQLException e){
			System.out.println("Something went wrong" + e.getMessage());
		}
	}
}
