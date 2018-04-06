package application;

import static application.PlayController.blockSize;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Entity extends Pane {

	// Actual entity manifests as a rectangle
	private Rectangle entity;
	
	// Construct new coloured entity at specific position
	public Entity(double xPosition, double yPosition, String colour){
		// Position entity
		this.setTranslateX(xPosition);
		this.setTranslateY(yPosition);

		// Build entity
		entity = new Rectangle(blockSize, blockSize);
		entity.setStyle("-fx-fill: " + colour.toUpperCase() + ";");

		// Add to parent pane
		super.getChildren().addAll(entity);
	}

}
