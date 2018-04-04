package application;

import java.io.IOException;
import java.util.Random;

import com.sun.javafx.scene.traversal.Direction;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PlayController extends Pane{

	//Define the size of the entities 
    public static final int blockSize = 20;
    
    //Define the dimensions of the game window
    public static final int width = 800; 
    public static final int height = 800; 
    
    //Define the speed of the snake
    private static double speed = 0.15;
    
    //Define the default direction of the snake
    private Direction direction = Direction.UP; 
    
    //Define flags to signify whether the gae is running or not
    private boolean moved = false; 
    private boolean running = false; 
	
    //Define a timeline for the animation
    private Timeline time = new Timeline(); 

    //Create an Observable list to hold our snake elements
    private ObservableList<Node> snake;
	
	// For any button press, do this
    @FXML
    protected void buttonClicked(ActionEvent evt) throws IOException {
    	// Store button pressed
        Button button = (Button)evt.getSource();	// Get button pressed
        final String buttonText = button.getText();	// Get button text

        // Perform action based on button text
        if (buttonText.equals("Reset")){       		// Go Back
        	restartGame();
        }
        else {										// Exit
        	System.exit(0);
        }
    }

  
    public Parent createGame() throws Exception{
    	
    	//Create an new pane to design the scene, and set its dimensions
    	Pane root = new Pane();
    	root.setPrefSize(width, height);
    	double getRootX= root.getTranslateX();
        double getRootY = root.getTranslateY();
    	
        //Define a group to add to the snake body 
        Group snakeBody = new Group(); 
        snake = snakeBody.getChildren();
        
        //Create food 
        Entity food = new Entity(getRootX, getRootY, Color.RED);
        //Position food 
        randomizeFood(food);
        
        //Define animation conditions
        KeyFrame frame = new KeyFrame(Duration.seconds(speed), event -> {
            if(!running){
                return; 
            }
                
            boolean toRemove = snake.size() > 1; 
            Node tail = toRemove ? snake.remove(snake.size()-1) : snake.get(0); 

            //Define positions for the tail of the snake 
            double tailX = tail.getTranslateX();
            double tailY = tail.getTranslateY();

            //Define what occurs when each direction is used 
            switch (direction) {
                case UP:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() - blockSize);
                    break;
                case DOWN:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY()+ blockSize);
                    break;
                case LEFT:
                    tail.setTranslateX(snake.get(0).getTranslateX()- blockSize);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
                case RIGHT:
                    tail.setTranslateX(snake.get(0).getTranslateX()+ blockSize);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
            }

            //Begin the animation
            moved = true; 

            if(toRemove){
                snake.add(0, tail); 
            }
                
            //Define what happens when the snake collides with itself (restart game)
            for(Node rect : snake) {
                if(rect != tail && tail.getTranslateX() == rect.getTranslateX() && tail.getTranslateY() == rect.getTranslateY()){ 
                    restartGame();
                    break;
                }
            }
            
            //If the snake reaches the edge of the game window, spawn it at the opposite end but maintain direction
            if(tail.getTranslateX() < 0){ 
                tail.setTranslateX(width - blockSize);
            }
            if(tail.getTranslateX() >= width){ 
                tail.setTranslateX(0.0);
            }
            if(tail.getTranslateY() < 0){ 
                tail.setTranslateY(height - blockSize);
            }
            if(tail.getTranslateY() >= height){ 
                tail.setTranslateY(0.0);
            }

            if (tail.getTranslateX() == food.getTranslateX() && tail.getTranslateY() == food.getTranslateY()) {
                randomizeFood(food); // setting x, and y of food to random value
                Entity bodyPart = new Entity(tailX,tailY, Color.BLUE);
                snake.add(bodyPart); //adding rectangle to snake
            }
        });
        
        //Make the animation continue indefinitely unless ended manually
        time.getKeyFrames().addAll(frame); 
        time.setCycleCount(Timeline.INDEFINITE);
        
        //Show the game elements
        root.getChildren().addAll(food, snakeBody);
        
    	return root;
    	
    }
    
  //Method that restarts the game
    private void restartGame() {
        stopGame();
        startGame();
    }
    
    //Stop the game and clear the snake 
    private void stopGame() {
        running = false;
        time.stop();
        snake.clear();  
    }
    
    //Set start conditions
    public void startGame() {
        direction = Direction.RIGHT;
        Entity head = new Entity(100, 100, Color.BLUE);
        snake.add(head);
        time.play();
        running = true;
    }

    //Method that randomizes the location of the food on each spawn
    private void randomizeFood(Entity food){
    	food.setTranslateX((int)(Math.random() * (width - blockSize))/ blockSize * blockSize);
        food.setTranslateY((int)(Math.random() * (height - blockSize))/ blockSize * blockSize); 
    }
    
    //Method assigning keys to the directions of the snake's movement
    public void moveSnake(Scene scene){
    	scene.setOnKeyPressed(event -> {
            if (!moved){
                return;
            }
            
            switch (event.getCode()) {
            case UP:
                if (direction != Direction.DOWN)
                    direction = Direction.UP;
                break;
            case DOWN:
                if(direction != Direction.UP)
                    direction = Direction.DOWN;
                break;
            case LEFT:
                if(direction != Direction.RIGHT)
                    direction = Direction.LEFT;
                break;
            case RIGHT:
                if(direction != Direction.LEFT)
                    direction = Direction.RIGHT;
                break;
            }
            
            moved = false;
            
    	});
    }
    
   

    
}
