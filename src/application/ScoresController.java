package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ScoresController {

	@FXML
	private Label playerName1st, playerName2nd, playerName3rd, playerName4th, playerName5th,
				  playerScore1st, playerScore2nd, playerScore3rd, playerScore4th, playerScore5th;

	@FXML Pane newScorePane, inputPane;
	@FXML TextField input;
	@FXML Label playerRank, playerName, playerScore;	
	@FXML FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("PlayPane.fxml"));

	// For any button press, do this
    @FXML
    protected void buttonClicked(ActionEvent evt) throws Exception {
    	// Store button pressed
        Button button = (Button)evt.getSource();	// Get button pressed
        final String buttonText = button.getText();	// Get button text

        // Perform action based on button text
        if (buttonText.equals("Back"))      		// Go Back
        	back(button);
        else if (buttonText.equals("Play"))     	// Start Game
        	start(button);
        else if (buttonText.equals("Submit"))       // Add Score
        	submit(button);
        else 										// Exit
        	System.exit(0);
    }

    // Set scores
    public void setTop5() throws IOException{
    	Label[] players = {playerName1st, playerScore1st, playerName2nd, playerScore2nd,
    				       playerName3rd, playerScore3rd, playerName4th, playerScore4th,
				  		   playerName5th, playerScore5th};

    	// Scores are read from File
    	BufferedReader br = new BufferedReader(new FileReader("HighScores.txt"));	// Read options from file

    	// Fill labels
    	for (int i = 0; i < players.length; i++)									// 5 rows
    		players[i].setText(br.readLine());							// Add line to top5
	}

    private void start(Button button) throws Exception {
    	BufferedReader br = new BufferedReader(new FileReader("Options.txt"));	 	// Read options from file

    	Parent root = gameLoader.load();
    	PlayController play = gameLoader.getController();			// Create controller

    	play.colour = br.readLine();								// Store colour
    	play.speed = Double.parseDouble(br.readLine());				// Store speed
    	play.blockSize = Main.size(br.readLine());					// Store Size
    	play.launch(button);

        button.getScene().setRoot(root);
    }

    private void back(Button button) throws Exception {
    	// get the button's stage
		Stage stage = (Stage) button.getScene().getWindow();

		//create a new scene and set the stage
		stage.setScene(								// Set new scene
				new Scene(FXMLLoader.load(getClass().getResource("Snake.fxml"))));
		stage.show();
    }

    private void submit(Button button) throws Exception {
    	// Get Input
    	String name = input.getText();			// Player Name
    	int score = Integer.parseInt(playerScore.getText());	// Score
    	String rank = "0";						// Rank

    	// Make input pane invisible and update info
    	playerName.setText(name);
    	playerRank.setText(rank);
    	inputPane.setVisible(false);
    }
}
