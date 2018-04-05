package application;

import java.io.BufferedReader;
import java.io.FileReader;

import javafx.application.Application;
import javafx.application.Platform;
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
	@FXML FXMLLoader optionsLoader = new FXMLLoader(getClass().getResource("Options.fxml"));
	@FXML FXMLLoader scoresLoader = new FXMLLoader(getClass().getResource("Scores.fxml"));
	@FXML FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("PlayPane.fxml"));

	BufferedReader br;

	public static final String[] colours = {"Black", "Blue", "Green", "White", "Purple", "Brown"},
			sizes = {"Small", "Default", "Big", "Very Big"};

	// Launch App
	public static void main(String[] args) {
		launch(args);
	}

	// What happens on launch
	@Override
	public void start(Stage primaryStage)throws Exception{
		// Initiate stage
		primaryStage.setTitle("Snek Game");			// Title
		primaryStage.setOnCloseRequest(x -> {
			Platform.exit();
		});
		primaryStage.setResizable(false);			// Static window size

		// Set stage and display
		primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Snake.fxml"))));
		primaryStage.show();

		DatabaseUse.initialize();
	}

	// For any button press, do this
	@FXML
	public void buttonClicked(ActionEvent evt) throws Exception { //MAKING THIS TATIC
		// Store button pressed
		Button button = (Button)evt.getSource();	// Get button pressed
		final String buttonText = button.getText();	// Save button text

		// Perform action based on button text
		if(buttonText.matches("Start Game"))		// Start Game
			start(button);
		else if (buttonText.equals("Options"))     	// Options
			options(button);
		else if (buttonText.equals("High Scores"))	// High Scores
			scores(button);
		else									// Close Window
			System.exit(0);
	}

	public static int size(String s){
		if (s.equals(sizes[0]))
			return 10;
		if (s.equals(sizes[1]))
			return 20;
		if (s.equals(sizes[2]))
			return 40;

		return 80;
	}

	private void start(Button button) throws Exception {
		br = new BufferedReader(new FileReader("Options.txt"));	 	// Read options from file

		Parent root = gameLoader.load();
		PlayController play = gameLoader.getController();			// Create controller

		play.colour = br.readLine();								// Store colour
		play.speed = Double.parseDouble(br.readLine());				// Store speed
		PlayController.blockSize = size(br.readLine());						// Store Size
		play.launch(button);

		button.getScene().setRoot(root);
	}

	private void options(Button button) throws Exception{
		// Load options scene
		Parent root = optionsLoader.load();							// Declare root
		OptionsController options = optionsLoader.getController();	// Create controller
		options.fillComboBox();								// Fills ComboBox

		// Set Options
		br = new BufferedReader(new FileReader("Options.txt"));	 	// Read options from file
		options.setColourBox(br.readLine());						// Store selected colour
		options.setSlider(Double.parseDouble(br.readLine()));		// Store selected speed
		options.setSizeBox(br.readLine());							// Store selected Size

		// Set scene
		button.getScene().setRoot(root);
	}

	private void scores(Button button) throws Exception{
		// Load Scores Scene
		Parent root = scoresLoader.load();							// Declare root
		ScoresController scores = scoresLoader.getController();		// Create controller

		// Print High Scores
		scores.setTop5();
		scores.newScorePane.setVisible(false);

		// Set scene
		button.getScene().setRoot(root);							// Makes scene visible
	}
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}