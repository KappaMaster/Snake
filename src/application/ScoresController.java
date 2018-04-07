package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ScoresController {
	// Private Object
	@FXML private TextField input;
	@FXML private Label playerName1st, playerName2nd, playerName3rd, playerName4th, playerName5th,
				  		playerScore1st, playerScore2nd, playerScore3rd, playerScore4th, playerScore5th;
	
	@FXML private FXMLLoader startLoader = new FXMLLoader(getClass().getResource("Snake.fxml")),
							 gameLoader = new FXMLLoader(getClass().getResource("PlayPane.fxml"));
	
	// Public Objects
	@FXML Pane newScorePane, inputPane;
	@FXML Label playerRank, playerName, playerScore;

	// File Reader
	private BufferedReader br;

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
		else if (buttonText.equals("Submit"))  {    // Add Score, then it takes you back to main menu
			submit(button);
			setTop5();
		}
		else 										// Exit
			System.exit(0);
	}

	// Set scores
	public void setTop5() throws IOException{
		// Create array of labels
		Label[] players = {playerName1st, playerScore1st, playerName2nd, playerScore2nd,
						   playerName3rd, playerScore3rd, playerName4th, playerScore4th,
						   playerName5th, playerScore5th};

		// Fill labels from database
		for (int i = 0; i < players.length; i += 2)	{						// for 5 rows
			// Add line to top5
			players[i].setText(DatabaseUse.getRecord(i/2, "name"));			// Add name	
			players[i + 1].setText(DatabaseUse.getRecord(i/2, "score"));	// Add score
		}
	}

	// Launch game
	private void start(Button button) throws Exception {
		// File reader
		br = new BufferedReader(new FileReader("Options.txt"));
		
		// Create controller
		Parent root = gameLoader.load();						// Declare root
		PlayController play = gameLoader.getController();		// Create controller
		
		// Initiate Game
		play.colour = br.readLine();							// Store colour
		play.speed = Double.parseDouble(br.readLine());			// Store speed
		PlayController.blockSize = Main.size(br.readLine());	// Store Size
		play.launch(button);									// Launch Game

		// Make Scene Visible
		button.getScene().setRoot(root);
	}

	// Return to main screen
	private void back(Button button) throws Exception {
		button.getScene().setRoot(startLoader.load());
	}

	// Submit highscore to database
	private void submit(Button button) throws Exception {
		// Get Input
		String name = input.getText();							// Player Name
		int score = Integer.parseInt(playerScore.getText());	// Score

		// Make input pane invisible and update info
		playerName.setText(name);
		playerRank.setText(DatabaseUse.add(name, score) + "");
		inputPane.setVisible(false);
	}
}
