package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ScoresController {
	
	@FXML 
	private Label playerName1st, playerName2nd, playerName3rd, playerName4th, playerName5th,
				  playerScore1st, playerScore2nd, playerScore3rd, playerScore4th, playerScore5th;

	// For any button press, do this
    @FXML
    protected void buttonClicked(ActionEvent evt) throws IOException {
    	// Store button pressed
        Button button = (Button)evt.getSource();
        final String buttonText = button.getText();

        // Go Back
        if (buttonText.equals("Back")){
		    // get reference to the button's stage
			Stage stage = (Stage) button.getScene().getWindow();
			        	
        	// Create a new scene and set the stage
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Snake.fxml"))));
			stage.show();
        }
        // Exit
        else {
        	System.exit(0);
        }
    }
    
    public void setTop5Names(String playerName1st, String playerName2nd, String playerName3rd, 
    						 String playerName4th, String playerName5th){
		this.playerName1st.setText(playerName1st);
		this.playerName2nd.setText(playerName2nd);
		this.playerName3rd.setText(playerName3rd);
		this.playerName4th.setText(playerName4th);
		this.playerName5th.setText(playerName5th);
	}
    
	public void setTop5Scores(String playerScore1st, String playerScore2nd, String playerScore3rd, 
							  String playerScore4th, String playerScore5th){
		this.playerScore1st.setText(playerScore1st);
		this.playerScore2nd.setText(playerScore2nd);
		this.playerScore3rd.setText(playerScore3rd);
		this.playerScore4th.setText(playerScore4th);
		this.playerScore5th.setText(playerScore5th);
	}
}
