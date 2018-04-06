package application;

import static application.Main.colours;
import static application.Main.sizes;

import java.io.PrintWriter;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;


public class OptionsController {

	// Declare objects we want to control (object name = fx:ID)
	@FXML private Slider speedSlider;				// Manage game speed (1-10)
	@FXML private ComboBox<String> colourChoice;	// Manage game colour
	@FXML private ComboBox<String> sizeChoice;		// Manage game size
	@FXML private CheckBox check;					// Manage border rules

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
		// get the button's stage
		Stage stage = (Stage) button.getScene().getWindow();

		// Update Game settings (write to file)
		PrintWriter writer = new PrintWriter("Options.txt", "UTF-8");
		writer.println(colourChoice.getValue());	// Store colour
		writer.println(speedSlider.getValue());		// store speed
		writer.println(sizeChoice.getValue());
		writer.println(check.isSelected());
		writer.close();								// close writer

		//create a new scene and set the stage
		stage.setScene(								// Set new scene
				new Scene(FXMLLoader.load(getClass().getResource("Snake.fxml"))));
		stage.show();
	}

	private void reset(Button button) throws Exception {
		setColourBox(colours[0]);					// Set combobox to default value
		setSizeBox(sizes[1]);						// Set combobox to default value
		setSlider(0.1);								// Set slider to default value
	}

	private void random(Button button) throws Exception {
		// Create random number generator
		Random r = new Random();

		setSlider(0.05 + r.nextDouble() * 0.15);	// Set slider to random double (0.05 and 0.2)
		setColourBox(								// Set combobox to random colour from colours[]
				colours[r.nextInt(colours.length)]);
		setSizeBox(									// Set combobox to random size from sizes[]
				sizes[r.nextInt(sizes.length)]);

		if(r.nextInt(2) == 1)						// Set checkboc to randomly be selected or not
			check.setSelected(true);
		else
			check.setSelected(false);
	}

	// Fill comboBox with colours[]
	public void fillComboBox(){
		colourChoice.setItems(FXCollections.observableArrayList(colours));
		sizeChoice.setItems(FXCollections.observableArrayList(sizes));
	}

	// Set slider value
	public void setSlider(double value){
		speedSlider.setValue(value);
	}

	// Set combobox value
	public void setColourBox(String value){
		colourChoice.setValue(value);
	}

	public void setSizeBox(String value){
		sizeChoice.setValue(value);
	}

	// Check checkbox value
	public void setCheckBox(String value){
		check.setSelected(Boolean.parseBoolean(value));
	}
}
