package application;

import static application.PlayController.blockSize;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Entity extends Pane {

	public Entity(double xPosition, double yPosition, String colour){
		this.setTranslateX(xPosition);
		this.setTranslateY(yPosition);

		Rectangle entity = new Rectangle(blockSize, blockSize);
		entity.setStyle("-fx-fill: " + colour.toUpperCase() + ";");

		super.getChildren().addAll(entity);
	}

}
