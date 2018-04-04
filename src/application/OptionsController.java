package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

	// Declare objects we want to control (object name = fx:ID)
	@FXML private Slider speedSlider;				// Manage game Speed (1-10)
	@FXML private ComboBox<String> colourChoice;	// Manage game Colour

	// Colour Options
	String[] colours = {"Black", "Blue", "Green", "White"};
	
	// For any button press, do this
    @FXML
    protected void buttonClicked(ActionEvent evt) throws IOException {
    	// Store button pressed
        Button button = (Button)evt.getSource();	// Get button pressed
        final String buttonText = button.getText();	// Get button text

        // Do action based off button text
        if (buttonText.equals("Back")){       		// Go Back
        	// get the button's stage
			Stage stage = (Stage) button.getScene().getWindow();

			// Update Game settings (write to file)
			PrintWriter writer = new PrintWriter("Options.txt", "UTF-8");
			writer.println(colourChoice.getValue());	// Store colour
			writer.println(speedSlider.getValue());		// store speed
			writer.close();								// close writer

			//create a new scene and set the stage
			stage.setScene(								// Set new scene
					new Scene(FXMLLoader.load(getClass().getResource("Snake.fxml"))));
			stage.show();								// Show scene
        }
        else if (buttonText.equals("Reset")){        	// Reset Defaults
        	setComboBox(colours[0]);					// Set combobox to default value
        	setSlider(0.15);								// Set slider to default value
        }
        else if (buttonText.equals("Random")){		    // Randomize options
        	// Create random number generator
        	Random r = new Random();

        	setSlider(0.05 + r.nextDouble() * 0.15);	// Set slider to random double (0.05 and 0.2)
        	setComboBox(								// Set combobox to random colour from colours[]
        			colours[r.nextInt(colours.length)]);
        }
        else {										// Close window
        	System.exit(0);
        }
    }

    // Fill comboBox with colours[]
    public void fillComboBox(){
    	colourChoice.setItems(FXCollections.observableArrayList(colours));
    }

    // Set slider value
    public void setSlider(double value){
    	speedSlider.setValue(value);
    }

    // Set combobox value
    public void setComboBox(String value){
    	colourChoice.setValue(value);
    }
}
