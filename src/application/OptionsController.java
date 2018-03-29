package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

public class OptionsController {

	String colour = "Black";	// Game colour (default black)
	double speed = 1.0;			// Game speed (1-10)
	
	@FXML private Slider speedSlider;
	@FXML private ComboBox<String> colourChoice;
	
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
			
			// Update Game settings
			speed = speedSlider.getValue();
			colour = colourChoice.getValue();

			System.out.println(speed);
			System.out.println(colour);
			
			//create a new scene and set the stage
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Snake.fxml"))));
			stage.show();
        }
        else {
        	System.exit(0);
        }
    }
    
    public void fillComboBox(){
    	colourChoice.getItems().addAll("Black", "Blue", "Green", "White");
    }
    
    public void setSlider(double value){
    	speedSlider.setValue(value);
    }
    
    public void setComboBox(String value){
    	colourChoice.setValue(value);
    }
}
