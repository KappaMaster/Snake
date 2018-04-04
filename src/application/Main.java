package application;
	
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
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

import com.sun.javafx.scene.traversal.Direction;

public class Main extends Application implements EventHandler<ActionEvent>{


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
    
	
	// Declare loaders (Used to change scenes)
	@FXML FXMLLoader optionsLoader = new FXMLLoader(getClass().getResource("Options.fxml"));
	@FXML FXMLLoader scoresLoader = new FXMLLoader(getClass().getResource("Scores.fxml"));
	@FXML FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("PlayPane.fxml"));

	BufferedReader br;

	
private Parent createGame() throws Exception{
    	
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
    private void startGame() {
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
    private void MoveSnake(Scene scene){
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
    protected void buttonClicked(ActionEvent evt) throws Exception {
    	// Store button pressed
        Button button = (Button)evt.getSource();	// Get button pressed
        final String buttonText = button.getText();	// Save button text

        // Perform action based on button text
        if(buttonText.matches("Start Game")){		// Start Game
        	
        	// Load options scene
        	//TODO
        	//1. Add the background back to the Snake.fxml file
        	//2. Translate the following code for use with controllers
        	//This initializes/starts the game (refer to solo program for details)
        	/*
        	Scene scene = new Scene(createGame(), 800, 800);
			MoveSnake(scene);
			primaryStage.setScene(scene);
			primaryStage.show();
			startGame();
        	 */
        	
            /*Parent root = gameLoader.load();							// Declare root
        	PlayController play = gameLoader.getController();			// Create controller
                	
        	br = new BufferedReader(new FileReader("Options.txt"));	 	// Read options from file
        	
        	String colour = br.readLine();								// Store colour
        	double speed = Double.parseDouble(br.readLine());			// Store speed
        	
        	Scene scene = new Scene(play.createGame(), 800, 800);
			play.moveSnake(scene);
			play.startGame();
			
        	// Set scene
            button.getScene().setRoot(root);
            */
    
        	Scene scene = new Scene(createGame(), 800, 800);
        	// get reference to the button's stage         
			Stage stage = (Stage) button.getScene().getWindow();
		    
			//create a new scene and set the stage
			stage.setScene(scene);
			stage.show();
        				
    		MoveSnake(scene);
    		startGame();
        }
        else if (buttonText.equals("Options")){     // Options
        	// Load options scene
            Parent root = optionsLoader.load();							// Declare root
        	OptionsController options = optionsLoader.getController();	// Create controller
        	options.fillComboBox();								// Fills ComboBox

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

    
	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}