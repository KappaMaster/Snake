package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

import application.Main.Direction;


public class Main extends Application implements EventHandler<ActionEvent>{

	
	// Declare loaders (Used to change scenes)
	@FXML FXMLLoader optionsLoader = new FXMLLoader(getClass().getResource("Options.fxml"));
	@FXML FXMLLoader scoresLoader = new FXMLLoader(getClass().getResource("Scores.fxml"));
	@FXML FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("PlayPane.fxml"));
	@FXML Rectangle head;
	
	//Define an enumerable for directions
	public enum Direction{
		UP, DOWN, LEFT, RIGHT
	}
	
	BufferedReader br;

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
		
	}

	/* Texting Purposes, delete later */
	//For any keypress, do this:
    @FXML
	protected void keyPressed(KeyEvent event){
    	System.out.println("Pressed");
    	//Define the controls of the game
		switch(event.getCode()){
			case W:
				head.setY(head.getY() - 20);
				break;
			case S:
				head.setY(head.getY() + 20);
				break;
			case A:
				head.setX(head.getX() - 20);
				break;
			case D:
				head.setX(head.getX() + 20);
				break;
			default:
				return;
			}
	}
    
	// For any button press, do this
    @FXML
    protected void buttonClicked(ActionEvent evt) throws IOException {
    	// Store button pressed
        Button button = (Button)evt.getSource();	// Get button pressed
        final String buttonText = button.getText();	// Save button text

        // Perform action based on button text
        if(buttonText.matches("Start Game")){		// Start Game
        	
        	// Load options scene
            Parent root = gameLoader.load();							// Declare root
        	PlayController play = gameLoader.getController();	// Create controller
                	
        	br = new BufferedReader(new FileReader("Options.txt"));	 	// Read options from file
        	
        	String colour = br.readLine();								// Store colour
        	switch(colour){
        		case "Blue":
        			play.background.setFill(Color.LIGHTBLUE);
        			break;
        		case "Green":
    				play.background.setFill(Color.DARKGREEN);
    				break;
        		case "Red":
    				play.background.setFill(Color.DARKRED);
    				break;
        		case "White":
    				play.background.setFill(Color.WHITE);
    				break;
        		default:
        			play.background.setFill(Color.BLACK);
        			break;
        	}
        	double speed = Double.parseDouble(br.readLine());			// Store speed
        	
			//create a new scene and set the stage
        	play.createGame();
        	play.startGame();
        	
        	// Set scene
            button.getScene().setRoot(root);
            
            EventHandler<ActionEvent> eventHandler = e -> {
        		play.moveSnake();
    		};
    	
    		// Create an animation for a running clock
    		Timeline animation = new Timeline(new KeyFrame(Duration.millis(speed), eventHandler));
    		animation.setCycleCount(Timeline.INDEFINITE);
    		animation.play(); 
    		
        }
        else if (buttonText.equals("Options")){     // Options
        	// Load options scene
            Parent root = optionsLoader.load();							// Declare root
        	OptionsController options = optionsLoader.getController();	// Create controller
        	options.fillComboBox();								// Fills ComboBox

        	// Set Options
        	br = new BufferedReader(new FileReader("Options.txt"));	 	// Read options from file
        	options.setComboBox(br.readLine());							// Store selected colour
        	options.setSlider(Double.parseDouble(br.readLine()));		// Store selected speed

        	// Set scene
            button.getScene().setRoot(root);
        }
        else if (buttonText.equals("High Scores")){	// High Scores
        	// Load Scores Scene
            Parent root = scoresLoader.load();							// Declare root
        	ScoresController scores = scoresLoader.getController();		// Create controller

        	// Set High Scores
/// TODO: connect to database
        	String[][] top5 = new String[5][2];

        	/*top5 = {{"pOne", "999"},  			// Scores can alse be added this way
        			  {"pTwo", "888"},
        			  {"pThree", "777"},
        			  {"pFour", "666"},
        			  {"pFive","555"}};*/

        	// Scores are read from File
        	br = new BufferedReader(new FileReader("HighScores.txt"));	// Read options from file

        	// Fill top5 array
        	for (int i = 0; i < 5; i++)									// 5 rows
        		for (int j = 0; j < 2; j++)								// 2 columns (name, score)
        			top5[i][j] = br.readLine();							// Add line to top5

        	scores.setTop5(top5);										// Print top5 on screen

        	// Set scene
        	button.getScene().setRoot(root);							// Makes scene visible
        }
        else {										// Close Window
        	System.exit(0);
        }
    }

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}