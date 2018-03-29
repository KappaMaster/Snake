package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

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

	@FXML private Slider speedSlider;				// Manage game Speed (1-10)
	@FXML private ComboBox<String> colourChoice;	// Manage game Colour
	
	String[] colours = {"Black", "Blue", "Green", "White"};
	
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
			PrintWriter writer = new PrintWriter("Options.txt", "UTF-8");
			writer.println(colourChoice.getValue());
			writer.println(speedSlider.getValue());
			writer.close();
			
			//create a new scene and set the stage
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Snake.fxml"))));
			stage.show();
        }
        // Reset Defaults
        else if (buttonText.equals("Reset")){
        	setComboBox("Black");
        	setSlider(1);
        }
        // Randomize
        else if (buttonText.equals("Random")){
        	Random r = new Random();
        	
        	setSlider(1 + r.nextDouble() * 9);
        	setComboBox(colours[r.nextInt(colours.length)]);
        }
        else {
        	System.exit(0);
        }
    }
    
    public void fillComboBox(){
    	for (int i = 0; i < colours.length; i++)
    		colourChoice.getItems().add(colours[i]);
    }
    
    public void setSlider(double value){
    	speedSlider.setValue(value);
    }
    
    public void setComboBox(String value){
    	colourChoice.setValue(value);
    }
}
