package application;

import static application.Main.*;

import java.io.PrintWriter;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;


public class OptionsController {
	// Declare objects we want to control (object name = fx:ID)
	@FXML private ComboBox<String> colourChoice;	// Manage game colour
	@FXML private ComboBox<String> sizeChoice;		// Manage game size
	@FXML private CheckBox check;					// Manage border rules
	@FXML private Slider speedSlider;				// Manage game speed (1-10)
	
	// Loader
	@FXML public FXMLLoader startLoader = new FXMLLoader(getClass().getResource("Snake.fxml"));
	
	// For any button press, do this
	@FXML
	protected void buttonClicked(ActionEvent evt) throws Exception {
		// Store button pressed
		Button button = (Button)evt.getSource();	// Get button pressed
		final String buttonText = button.getText();	// Get button text

		// Do action based off button text
		if (buttonText.equals("Back"))       		// Go Back
			back(button);
		else if (buttonText.equals("Reset"))        // Reset Defaults
			reset(button);
		else if (buttonText.equals("Random"))		// Randomize options
			random(button);
		else										// Close window
			System.exit(0);
	}
	
	private void back(Button button) throws Exception {
		// Update Game settings (write to file)
		PrintWriter writer = new PrintWriter("Options.txt", "UTF-8");
		writer.println(colourChoice.getValue());	// Store colour
		writer.println(speedSlider.getValue());		// Store speed
		writer.println(sizeChoice.getValue());		// Store size
		writer.println(check.isSelected());			// Store border rules
		writer.close();								// close writer

		//create a new scene and set the stage
		button.getScene().setRoot(startLoader.load());
	}

	private void reset(Button button) throws Exception {
		setColourBox(colours[0]);					// Set Combobox to default value
		setSizeBox(sizes[1]);						// Set Combobox to default value
		setSlider(0.1);								// Set Slider to default value
		check.setSelected(false);					// Set Checkbox to unchecked
	}

	private void random(Button button) throws Exception {
		// Create random number generator
		Random r = new Random();

		setSlider(0.05 + r.nextDouble() * 0.15);			// Set slider to random double (0.05 and 0.2)
		setColourBox(colours[r.nextInt(colours.length)]);	// Set Combobox to random colour from colours[]
		setSizeBox(sizes[r.nextInt(sizes.length)]);			// Set Combobox to random size from sizes[]
		check.setSelected(r.nextBoolean());					// Set Checkbox to randomly be selected or not
	}

	// Fill comboBoxes
	public void fillComboBox(){
		colourChoice.setItems(FXCollections.observableArrayList(colours));	// Fill Colours
		sizeChoice.setItems(FXCollections.observableArrayList(sizes));		// Fill Sizes
	}

	// Set slider value
	public void setSlider(double value){
		speedSlider.setValue(value);
	}

	// Set colour combobox value
	public void setColourBox(String value){
		colourChoice.setValue(value);
	}

	// Set size combobox value
	public void setSizeBox(String value){
		sizeChoice.setValue(value);
	}

	// Set checkbox value
	public void setCheckBox(boolean value){
		check.setSelected(value);
	}
}
