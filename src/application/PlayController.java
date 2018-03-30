package application;

import java.io.IOException;
import java.util.Random;

import application.Main.Direction;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PlayController {
	@FXML Rectangle background;
	@FXML Rectangle head = new Rectangle(20, 20, Color.GRAY);
	
	//Declare variables
	private Direction direction = Direction.UP;
	private boolean moved = false; 
	private boolean running = false;
	
	//Create a timeline to allow animation
	private Timeline time = new Timeline();
	
	//Create a base for the snake
	private ObservableList<Node> snake;
	
	// For any button press, do this
    @FXML
    protected void buttonClicked(ActionEvent evt) throws IOException {
    	// Store button pressed
        Button button = (Button)evt.getSource();	// Get button pressed
        final String buttonText = button.getText();	// Get button text

        // Perform action based on button text
        if (buttonText.equals("Reset")){       		// Go Back
        	System.exit(0);
        }
        else {										// Exit
        	System.exit(0);
        }
    }
    
    //For any keypress, do this:
    @FXML
	protected void keyPressed(KeyEvent event){
    	
    	//Define the controls of the game
		if(!moved)
			return;

    	System.out.println("press");
		switch(event.getCode()){
			case W:
				if(direction != Direction.DOWN){
					direction = Direction.UP;
				}
				break;
			case S:
				if(direction != Direction.UP){
					direction = Direction.DOWN;
				}
				break;
			case A:
				if(direction != Direction.RIGHT){
					direction = Direction.LEFT;
				}	
				break;
			case D:
				if(direction != Direction.LEFT){
					direction = Direction.RIGHT;
				}
				break;
			default:
				return;
			}
			
		moved = false;
	}
    
    public void createGame(){
    	//Create random variable
    	Random rand = new Random();
    	
    	//Define the body of the snake
    	Group bodyOfSnake = new Group();
    	snake = bodyOfSnake.getChildren();
    	
    	//Define the food of the snake
    	Rectangle food = new Rectangle(20,20);
    	food.setFill(Color.RED);
    	food.setTranslateX(rand.nextInt(780));
    	food.setTranslateY(rand.nextInt(780));
    	
    	//Create a keyframe for movement
    	KeyFrame frame = new KeyFrame(Duration.seconds(0.1), event -> {
    		
    		if(!running)
    			return;
    		
    		boolean toRemove = snake.size() < 1;
    		Node tail = toRemove ? snake.remove(snake.size()-1) : snake.get(0);
    		
    		//Defines the position of the tail
    		double tailX = tail.getTranslateX();
    		double tailY = tail.getTranslateY();
    		
    		//Switch statement defining movement of the snake itself
    		switch(direction){
	    		case UP: 
	    			tail.setTranslateX(snake.get(0).getTranslateX());
	    			tail.setTranslateY(snake.get(0).getTranslateY() - 20);
	    			break;
	    			
	    		case DOWN: 
	    			tail.setTranslateX(snake.get(0).getTranslateX());
	    			tail.setTranslateY(snake.get(0).getTranslateY() + 20);
	    			break;
	    			
	    		case RIGHT:
	    			tail.setTranslateX(snake.get(0).getTranslateX() + 20);
	    			tail.setTranslateY(snake.get(0).getTranslateY());
	    			break;
	    		
	    		case LEFT:
	    			tail.setTranslateX(snake.get(0).getTranslateX() - 20);
	    			tail.setTranslateY(snake.get(0).getTranslateY());
	    			break;
	    		
	    		default:
	    			return;
	    		}
    		
    		moved = true;
    		
    		if(toRemove)
    			snake.add(0,tail);
    		
    		for(Node rect: snake){
    			//Define death by running into yourself
    			if(rect != tail && tail.getTranslateX() == rect.getTranslateX() 
    					&& tail.getTranslateY() == rect.getTranslateY())
    			{
    				endGame();
    				break;
    			}
    		}
    			
			//Define what happens if you run into a wall 
			if(tail.getTranslateX() < 0 || tail.getTranslateX() > 800)
				endGame();
			
			if(tail.getTranslateY() < 0 || tail.getTranslateY() > 800)
				endGame();
			
    		//Define what happens when the food is eaten
    		if(tail.getTranslateX() == food.getTranslateX() && tail.getTranslateY() == food.getTranslateY()){
    			
    			food.setTranslateX(rand.nextInt(780));
    	    	food.setTranslateY(rand.nextInt(780));
    			
    	    	Rectangle rect = new Rectangle(20,20);
    	    	rect.setTranslateX(tailX);
    	    	rect.setTranslateY(tailY);
    	    	
    	    	snake.add(rect);
    		}
    		
    	});
    	
    	time.getKeyFrames().add(frame);
    	time.setCycleCount(Timeline.INDEFINITE);
    	
    }
    
    
    //Ends the game and clears the existing snake
    public void endGame(){
    	running = false;
    	time.stop();
    	snake.clear();
    }
    //Sets all variables to their default position and begins the timeline 
    public void startGame(){
    	direction = Direction.UP;
    	snake.add(head);
    	time.play();
    	running = true;
    	
    }
    
}
