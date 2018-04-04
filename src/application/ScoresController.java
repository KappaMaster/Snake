package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ScoresController {

	@FXML
	private Label playerName1st, playerName2nd, playerName3rd, playerName4th, playerName5th,
				  playerScore1st, playerScore2nd, playerScore3rd, playerScore4th, playerScore5th;

	@FXML Label playerRank, playerName, playerScore;
	@FXML Pane newScorePane;

	// For any button press, do this
    @FXML
    protected void buttonClicked(ActionEvent evt) throws IOException {
    	// Store button pressed
        Button button = (Button)evt.getSource();	// Get button pressed
        final String buttonText = button.getText();	// Get button text

        // Perform action based on button text
        if (buttonText.equals("Back")){       		// Go Back
		    // Get the button's stage
			Stage stage = (Stage) button.getScene().getWindow();

        	// Create a new scene and set the stage
			stage.setScene(							// Set new scene
					new Scene(FXMLLoader.load(getClass().getResource("Snake.fxml"))));
			// Display scene
			stage.show();
        }
        else {										// Exit
        	System.exit(0);
        }
    }

    // Set scores
    public void setTop5(String[][] top5){
		this.playerName1st.setText(top5[0][0]);
		this.playerName2nd.setText(top5[1][0]);
		this.playerName3rd.setText(top5[2][0]);
		this.playerName4th.setText(top5[3][0]);
		this.playerName5th.setText(top5[4][0]);
		this.playerScore1st.setText(top5[0][1]);
		this.playerScore2nd.setText(top5[1][1]);
		this.playerScore3rd.setText(top5[2][1]);
		this.playerScore4th.setText(top5[3][1]);
		this.playerScore5th.setText(top5[4][1]);
	}
}
