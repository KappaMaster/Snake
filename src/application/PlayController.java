package application;

import java.io.IOException;

import com.sun.javafx.scene.traversal.Direction;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayController{
	//Creation Properties    
	double getRootX, getRootY;
    private ObservableList<Node> snake;		// Create an Observable list to hold our snake elements
    
    public static int blockSize = 40;		// Define the size of the entities
    public static final int width = 800,	// Define the dimensions of the game window
    					    height = 800;
    
    // Graphics and display variables
	Pane root;								// Background / Window game is drawn on
	Stage stage;							// game stage
	Group snakeBody;						// Store body of snake
	Entity food, head;						// Snake head and food entities
	
    @FXML Label scoreText, lengthText;		// Text Labels
    @FXML Pane scorePane, controlsPane;		// Panels holding labels
	
    //Options
    double speed = 0.2;						// Snake Speed
    String colour = "Black";				// Snake Colour
  
    //Score
    int score = 0;

    //Animation
    private Timeline time = new Timeline();		// Define a timeline for the animation
    private Direction direction = Direction.UP;	// Define the default direction of the snake
    
    private boolean moved = false;				// Has snake moved
    private boolean running = false;			// Is game running

    
	public void launch(Button source) throws Exception{
		// Create new scene
    	Scene scene = new Scene(createGame(), 800, 800);

    	// get reference to the button's stage
		stage = (Stage) source.getScene().getWindow();

		//create a new scene and set the stage
		stage.setScene(scene);
		stage.show();

		MoveSnake(scene);
		startGame();
	}

	private void animate(){
		if(!running){
            return;
        }

        boolean toRemove = snake.size() > 1;
        Node tail = toRemove ? snake.remove(snake.size()-1) : snake.get(0);

        //Define positions for the tail of the snake
        double tailX = tail.getTranslateX(),
        	   tailY = tail.getTranslateY();

        //Define what occurs when each direction is used
        switch (direction) {
            case UP:
                tail.setTranslateX(snake.get(0).getTranslateX());
                tail.setTranslateY(snake.get(0).getTranslateY() - blockSize);
                break;
            case DOWN:
                tail.setTranslateX(snake.get(0).getTranslateX());
                tail.setTranslateY(snake.get(0).getTranslateY() + blockSize);
                break;
            case LEFT:
                tail.setTranslateX(snake.get(0).getTranslateX() - blockSize);
                tail.setTranslateY(snake.get(0).getTranslateY());
                break;
            case RIGHT:
                tail.setTranslateX(snake.get(0).getTranslateX() + blockSize);
                tail.setTranslateY(snake.get(0).getTranslateY());
                break;
            case NEXT:
            	break;
        }

        //Begin the animation
        moved = true;

        if(toRemove)
            snake.add(0, tail);

        //Define what happens when the snake collides with itself (restart game)
        for(Node rect : snake)
            if(rect != tail && tail.getTranslateX() == rect.getTranslateX() 
             				&& tail.getTranslateY() == rect.getTranslateY()){
                restartGame();
                break;
            }

        //If the snake reaches the edge of the game window, spawn it at the opposite end but maintain direction
        if(tail.getTranslateX() < 0 || tail.getTranslateX() >= width 
        || tail.getTranslateY() < 0 || tail.getTranslateY() >= height)
        	restartGame();
        
        // If snake hits food, add a new snake segment and add points
        if (tail.getTranslateX() == food.getTranslateX() && tail.getTranslateY() == food.getTranslateY()) {
            randomizeFood(food); // setting x, and y of food to random value
            Entity bodyPart = new Entity(tailX,tailY, colour);
            snake.add(bodyPart); //adding rectangle to snake
            
            // Add points => 10 points + speed bonus points (0 - 15) + size bonus points (1 - 8)
            score += 10 + (20 - 100 * speed) + (10 * blockSize/100);	
            
            lengthText.setText("" + snake.size());
            scoreText.setText("" + score);
        }
	}

	private Parent createGame() throws Exception{
    	//Create an new pane to design the scene, and set its dimensions
    	root = new Pane();
    	//root.setStyle("-fx-background: " + colour.toUpperCase() + ";");
    	root.setPrefSize(width, height);
    	getRootX = root.getTranslateX();
        getRootY = root.getTranslateY();

        //Define a group to add to the snake body
        snakeBody = new Group();
        snake = snakeBody.getChildren();

        //Create food
        food = new Entity(getRootX, getRootY, "Red");
        //Position food
        randomizeFood(food);

        //Define animation conditions
        KeyFrame frame = new KeyFrame(Duration.seconds(speed), event -> {
            animate();
        });

        //Make the animation continue indefinitely unless ended manually
        time.getKeyFrames().addAll(frame);
        time.setCycleCount(Timeline.INDEFINITE);

        //Show the game elements
        root.getChildren().addAll(controlsPane, scorePane, food, snakeBody);

    	return root;

    }

    //Set start conditions
    private void startGame() {
        direction = Direction.RIGHT;								// Starting direction
        Entity head = new Entity(blockSize, blockSize, colour);		// Build snake head
        snake.add(head);											// Add body to head
        time.play();												// Start animation
        running = true;												// Program is running
    }

    //Method that restarts the game
    private void restartGame() {
        stopGame();
        startGame();

        score = 0;
        
        // Update Labels
        lengthText.setText("" + snake.size());
        scoreText.setText("" + 0);
    }

    //Stop the game and clear the snake
    private void stopGame() {
        running = false;
        time.stop();
        snake.clear();
    }

    //Method assigning keys to the directions of the snake's movement
    private void MoveSnake(Scene scene){
    	scene.setOnKeyPressed(event -> {
            if (!moved)
                return;

            switch (event.getCode()) {
	            case UP:
	            case W:
	                if (direction != Direction.DOWN)
	                    direction = Direction.UP;
	                break;
	            case DOWN:
	            case S:
	                if(direction != Direction.UP)
	                    direction = Direction.DOWN;
	                break;
	            case LEFT:
	            case A:
	                if(direction != Direction.RIGHT)
	                    direction = Direction.LEFT;
	                break;
	            case RIGHT:
	            case D:
	                if(direction != Direction.LEFT)
	                    direction = Direction.RIGHT;
	                break;
	            case R:
	            	restartGame();
	                break;
	            case Q:
	    			//create a new scene and set the stage
	            	try {
	            		stage.setScene(							// Set new scene
	            				new Scene(FXMLLoader.load(getClass().getResource("Snake.fxml"))));
					} catch (IOException e) {
						e.printStackTrace();
					}
	    			stage.show();								// Show scene							// Show scene
	            	break;
	            case P:
	            	direction = direction.NEXT;
	            	break;
	            	
            }

            moved = false;

    	});
    }

    //Method that randomizes the location of the food on each spawn
    private void randomizeFood(Entity food){
    	food.setTranslateX( (int) (Math.random() * ( width - blockSize)) / blockSize * blockSize);
        food.setTranslateY( (int) (Math.random() * (height - blockSize)) / blockSize * blockSize);
    }
}
