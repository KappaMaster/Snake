package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent>{

	// Declare loaders (Used to change scenes)
	@FXML private FXMLLoader startLoader = new FXMLLoader(getClass().getResource("Snake.fxml")),
					 		 optionsLoader = new FXMLLoader(getClass().getResource("Options.fxml")),
					 		 scoresLoader = new FXMLLoader(getClass().getResource("Scores.fxml")),
					 		 gameLoader = new FXMLLoader(getClass().getResource("PlayPane.fxml"));

	// File reader
	BufferedReader br;

	// String arrays for options combo boxes (Can be added to or removed from here)
	public static final String[] colours = {"Black", "Blue", "Brown", "Green", "Gold", "Purple"},
								 sizes = {"Small", "Default", "Big", "Very Big"};

	// Window Size
	public static final int width = 800,	// Define the dimensions of the game window
							height = 800;

	// Launch App
	public static void main(String[] args) {
		launch(args);
	}

	// What happens on launch
	@Override
	public void start(Stage primaryStage)throws Exception{
		// Initialize Files
		createFile();								// Create Options.txt if it doesn't exist
		DatabaseUse.initialize();					// Create Scores.db if it doesn't exist
		
		// Initiate stage
		primaryStage.setTitle("Snek Game");			// Title
		primaryStage.setResizable(false);			// Static window size
		
		// Set stage and display
		primaryStage.setScene(new Scene(startLoader.load()));
		primaryStage.show();
	}

	// Calculate blocksize
	public static int size(String s){
		if (s.equals(sizes[0]))
			return 10;
		if (s.equals(sizes[1]))
			return 20;
		if (s.equals(sizes[2]))
			return 40;

		return 80;
	}

	// Launch game play scene
	private void start(Button button) throws Exception {		
		br = new BufferedReader(new FileReader("Options.txt"));	 	// Read options from file

		// Load game controller
		Parent root = gameLoader.load();							// Declare root
		PlayController play = gameLoader.getController();

		// Initiate Game
		play.colour = br.readLine();								// Store Colour
		play.speed = Double.parseDouble(br.readLine());				// Store Speed
		PlayController.blockSize = size(br.readLine());						// Store Size
		play.infiniteWindow = Boolean.parseBoolean(br.readLine());	// Store Border rules
		play.launch(button);										// Launch Game

		// Set scene Visible
		button.getScene().setRoot(root);
	}

	// Launch option scene
	private void options(Button button) throws Exception{
		// Load options controller
		Parent root = optionsLoader.load();
		OptionsController options = optionsLoader.getController();	// Create controller

		// Set Options
		br = new BufferedReader(new FileReader("Options.txt"));	 	// Read options from file
		options.fillComboBox();										// Fill ComboBoxes
		options.setColourBox(br.readLine());						// Get Colour
		options.setSlider(Double.parseDouble(br.readLine()));		// Get Speed
		options.setSizeBox(br.readLine());							// Get Size
		options.setCheckBox(Boolean.parseBoolean(br.readLine()));	// Get Border Rule

		// Set Scene Visible
		button.getScene().setRoot(root);
	}

	// Launch scores scene
	private void scores(Button button) throws Exception{
		// Load Scores Controller
		Parent root = scoresLoader.load();							// Declare root
		ScoresController scores = scoresLoader.getController();

		// Print High Scores
		scores.setTop5();											// Fills top 5 labels
		scores.newScorePane.setVisible(false);						// New Score pane is hidden

		// Make Scene Visible
		button.getScene().setRoot(root);
	}

	// For any button press, do this
	@Override
	public void handle(ActionEvent evt) {
		try{
			// Store button pressed
			Button button = (Button)evt.getSource();		// Get button pressed
			final String buttonText = button.getText();		// Save button text

			// Perform action based on button text
			if(buttonText.matches("Start Game"))			// Start Game
				start(button);
			else if (buttonText.equals("Options"))     		// Options
				options(button);
			else if (buttonText.equals("High Scores"))		// High Scores
				scores(button);
			else											// Close Window
				System.exit(0);
		}
		catch (Exception e){
			System.out.println("Something went wrong: " + e.getMessage());
		}
	}
	
	// Check if options.txt exists, and creates file if it doesn't
	public static void createFile() throws IOException {
		File options = new File("Options.txt");
		
		if(!options.exists()){
			options.createNewFile();
			
			PrintWriter writer = new PrintWriter("Options.txt", "UTF-8");
			writer.println("Black\n0.1\nDefault\nfalse");
			writer.close();
		}
	}
}