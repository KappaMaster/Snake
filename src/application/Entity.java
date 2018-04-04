package application;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static application.PlayController.blockSize;

public class Entity extends Pane {
	
	public Entity(double xPosition, double yPosition, String colour){
		this.setTranslateX(xPosition);
		this.setTranslateY(yPosition);
		
		Rectangle entity = new Rectangle(blockSize, blockSize);
		entity.setStyle("-fx-fill: " + colour.toUpperCase() + ";");
		
		super.getChildren().addAll(entity);
	}

}
