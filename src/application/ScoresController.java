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
	Label playerName1st, playerName2nd, playerName3rd, playerName4th, playerName5th,
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
    
	public void setPlayerName1st(String name) {
		this.playerName1st.setText(name);
	}

	public void setPlayerName2nd(String name) {
		this.playerName2nd.setText(name);
	}

	public void setPlayerName3rd(String name) {
		this.playerName3rd.setText(name);
	}

	public void setPlayerName4th(String name) {
		this.playerName4th.setText(name);
	}

	public void setPlayerName5th(String name) {
		this.playerName5th.setText(name);
	}

	public void setPlayerScore1st(String name) {
		this.playerScore1st.setText(name);
	}

	public void setPlayerScore2nd(String name) {
		this.playerScore2nd.setText(name);
	}
	
	public void setPlayerScore3rd(String name) {
		this.playerScore3rd.setText(name);
	}

	public void setPlayerScore4th(String name) {
		this.playerScore4th.setText(name);
	}

	public void setPlayerScore5th(String name) {
		this.playerScore5th.setText(name);
	}
}
