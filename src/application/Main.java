package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;


public class Main extends Application implements EventHandler<ActionEvent>{

	// Declare loaders (Used to change scenes)
	@FXML FXMLLoader optionsLoader = new FXMLLoader(getClass().getResource("Options.fxml"));
	@FXML FXMLLoader scoresLoader = new FXMLLoader(getClass().getResource("Scores.fxml"));
	@FXML FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("TestPane.fxml"));
	
	//Define an enumerable for directions
	public enum Direction{
		UP, DOWN, LEFT, RIGHT
	}
	
	//Declare variables
	private Direction direction = Direction.UP;
	private boolean moved = false; 
	private boolean running = false;
	
	//Create a timeline to allow animation
	private Timeline time = new Timeline();
	
	//Create a base for the snake
	private ObservableList<Node> snake;
	
	BufferedReader br;

	// Launch App
	public static void main(String[] args) {
		launch(args);
	}

	// What happens on launch
	@Override
	public void start(Stage primaryStage)throws Exception{
		// Initiate stage
		primaryStage.setTitle("Snek Game");			// Title
		primaryStage.setOnCloseRequest(x -> {
        	Platform.exit();
        });
		primaryStage.setResizable(false);			// Static window size

		// Set stage and display
		primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Snake.fxml"))));
		primaryStage.show();
	}

	// For any button press, do this
    @FXML
    protected void buttonClicked(ActionEvent evt) throws IOException {
    	// Store button pressed
        Button button = (Button)evt.getSource();	// Get button pressed
        final String buttonText = button.getText();	// Save button text

        // Perform action based on button text
        if(buttonText.matches("Start Game")){		// Start Game
        	//Load game scene
/// TODO : Add Game Play stage
			//create a new scene and set the stage
        	Stage gameStage =  new Stage();
        	Scene scene = new Scene(createGame());
			gameStage.setScene(scene);
			
			//Define the controls of the game
			scene.setOnKeyPressed(event -> {
				if(!moved){
					return;
				}
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
				
			});
			
			gameStage.setTitle("Snek Game");
        	gameStage.show();
        	startGame();
        }
        else if (buttonText.equals("Options")){     // Options
        	// Load options scene
            Parent root = optionsLoader.load();							// Declare root
        	OptionsController options = optionsLoader.getController();	// Create controller
        	options.fillComboBox();										// Fills ComboBox

        	// Set Options
        	br = new BufferedReader(new FileReader("Options.txt"));	 	// Read options from file
        	options.setComboBox(br.readLine());							// Store selected colour
        	options.setSlider(Double.parseDouble(br.readLine()));		// Store selected speed

        	// Set scene
            button.getScene().setRoot(root);
        }
        else if (buttonText.equals("High Scores")){	// High Scores
        	// Load Scores Scene
            Parent root = scoresLoader.load();							// Declare root
        	ScoresController scores = scoresLoader.getController();		// Create controller

        	// Set High Scores
/// TODO: connect to database
        	String[][] top5 = new String[5][2];

        	/*top5 = {{"pOne", "999"},  			// Scores can alse be added this way
        			  {"pTwo", "888"},
        			  {"pThree", "777"},
        			  {"pFour", "666"},
        			  {"pFive","555"}};*/

        	// Scores are read from File
        	br = new BufferedReader(new FileReader("HighScores.txt"));	// Read options from file

        	// Fill top5 array
        	for (int i = 0; i < 5; i++)									// 5 rows
        		for (int j = 0; j < 2; j++)								// 2 columns (name, score)
        			top5[i][j] = br.readLine();							// Add line to top5

        	scores.setTop5(top5);										// Print top5 on screen

        	// Set scene
        	button.getScene().setRoot(root);							// Makes scene visible
        }
        else {										// Close Window
        	System.exit(0);
        }
    }

    private Parent createGame(){
    	
    	//Create the scene
    	Pane root = new Pane();
    	root.setPrefSize(800,800);
    	
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
    		
    		if(!running){
    			
    			return;
    		}
    		
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
    		
    		if(toRemove){
    			
    			snake.add(0,tail);
    		
    		}
    		
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
    			if(tail.getTranslateX() < 0 || tail.getTranslateX() > 800){
    				endGame();
    			}
    			
    			if(tail.getTranslateY() < 0 || tail.getTranslateY() > 800){
    				endGame();
    			}
    			
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
    	
    	return root;
    }
    
    
    //Ends the game and clears the existing snake
    private void endGame(){
    	
    	running = false;
    	time.stop();
    	snake.clear();
    	
    }
    
    //Sets all variables to their deafault position and begins the timeline 
    private void startGame()
    {
    	direction = Direction.UP;
    	Rectangle head = new Rectangle(20,20);
    	snake.add(head);
    	time.play();
    	running = true;
    }
    
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}