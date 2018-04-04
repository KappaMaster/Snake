package application;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static application.Main.blockSize;

public class Entity extends Pane {
	
	public Entity(double xPosition, double yPosition, Color color){
		this.setTranslateX(xPosition);
		this.setTranslateY(yPosition);
		
		Rectangle entity = new Rectangle(blockSize, blockSize);
		entity.setFill(color);
		super.getChildren().addAll(entity);
		
	}

}
