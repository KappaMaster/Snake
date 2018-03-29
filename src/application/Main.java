package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent>{

	@FXML FXMLLoader optionsLoader = new FXMLLoader(getClass().getResource("Options.fxml"));
	@FXML FXMLLoader scoresLoader = new FXMLLoader(getClass().getResource("Scores.fxml"));
    
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage)throws Exception{

		// Initiate stage
		primaryStage.setTitle("Snek Game");		// Title
		primaryStage.setOnCloseRequest(x -> {
        	Platform.exit();
        });
		primaryStage.setResizable(false);

		// Set stage and display
		primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Snake.fxml"))));
		primaryStage.show();
	}

	// For any button press, do this
    @FXML
    protected void buttonClicked(ActionEvent evt) throws IOException {
    	
    	// Store button pressed
        Button button = (Button)evt.getSource();
        final String buttonText = button.getText();

    	// Start Game
        if(buttonText.matches("Start Game")){
        	System.out.println("Start Game ");
/// TODO : Add Game Play stage
			//create a new scene and set the stage
//			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Options.fxml"))));
//			stage.show();
        }
        // Options
        else if (buttonText.equals("Options")){
//        	// Load Scores Scene
            Parent root = optionsLoader.load();
        	OptionsController options = optionsLoader.getController();
  
        	// Set Options
        	BufferedReader br = new BufferedReader(new FileReader("Options.txt"));
        	options.fillComboBox();
        	options.setComboBox(br.readLine());
        	options.setSlider(Double.parseDouble(br.readLine()));
        	
            button.getScene().setRoot(root);
        }
        // High Scores
        else if (buttonText.equals("High Scores")){
        	// Load Scores Scene
            Parent root = scoresLoader.load();
        	ScoresController scores = scoresLoader.getController();
        	
        	// Set High Scores
        	String[] pNames = {"pOne", "pTwo", "pThree", "pFour", "pFive"};
        	String[] pScores = {"555", "444", "333", "222", "111"};
        	scores.setTop5Names(pNames[0], pNames[1], pNames[2], pNames[3], pNames[4]);
        	scores.setTop5Scores(pScores[0], pScores[1], pScores[2], pScores[3], pScores[4]);
          
        	button.getScene().setRoot(root);
        }
        else {
        	System.exit(0);
        }
    }
    
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}