package application;

import java.io.IOException;

import com.sun.javafx.scene.traversal.Direction;

import javafx.animation.Animation;
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
	Scene scene;							// game Scene
	Group snakeBody;						// Store body of snake
	Entity food, head;						// Snake head and food entities

	@FXML Label scoreText, lengthText;		// Text Labels
	@FXML Pane scorePane, controlsPane;		// Panels holding labels
	@FXML FXMLLoader scoresLoader = new FXMLLoader(getClass().getResource("Scores.fxml"));

	//Options
	double speed = 0.2;						// Snake Speed
	String colour = "Black";				// Snake Colour
	boolean infiniteWindow = false;			// Whether the window edge will kill you

	//Score
	int score = 0;

	//Animation
	private Timeline time = new Timeline();		// Define a timeline for the animation
	private Direction direction = Direction.UP;	// Define the default direction of the snake

	private boolean moved = false;				// Has snake moved
	private boolean running = false;			// Is game running


	public void launch(Button source) throws Exception{
		// Create new scene
		scene = new Scene(createGame(), 800, 800);

		// get reference to the button's stage
		stage = (Stage) source.getScene().getWindow();

		//create a new scene and set the stage
		stage.setScene(scene);
		stage.show();

		MoveSnake();
		startGame();
	}

	private void animate(){
		if(!running)
			return;

		if(direction == Direction.NEXT){
			moved = true;
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
			default:
				tail.setTranslateX(snake.get(0).getTranslateX());
				tail.setTranslateY(snake.get(0).getTranslateY());
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
				endGame();
				break;
			}
		
		// What to do if the snake hits the border
		if(infiniteWindow){ 	// Spawn it at the opposite end, maintain direction
			if(tail.getTranslateX() < 0)
				tail.setTranslateX(width);
			else if (tail.getTranslateX() > width)
				tail.setTranslateX(0);
			else if (tail.getTranslateY() < 0)
				tail.setTranslateY(height);
			else if (tail.getTranslateY() > height)
				tail.setTranslateY(0);
		}
		else 					// End Game
			if(tail.getTranslateX() < 0 || tail.getTranslateX() >= width
			 || tail.getTranslateY() < 0 || tail.getTranslateY() >= height)
			endGame();

		// If snake hits food, add a new snake segment and add points
		if (tail.getTranslateX() == food.getTranslateX() && tail.getTranslateY() == food.getTranslateY()) {
			randomizeFood(food); // setting x, and y of food to random value

			snake.add(new Entity(tailX,tailY, colour)); //adding rectangle to snake
			snake.add(new Entity(tailX,tailY, colour)); //adding rectangle to snake

			// Add points => 10 points + speed bonus points (0 - 15) + size bonus points (1 - 8)
			int points = (int) (10 + (20 - 100 * speed) + (10 * blockSize/100));
			
			// Half points if infinite window selected
			if (infiniteWindow)
				points /= 2;
			
			// Add to score
			score += points;
			
			// Update display
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
		time.setCycleCount(Animation.INDEFINITE);

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
	private void MoveSnake() throws IOException{
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
					endGame();
					break;
				case P:
					direction = Direction.NEXT;
					break;
				default:
					break;
			}

			moved = false;

		});
	}

	private void endGame(){
		stopGame();
		//create a new scene and set the stage
		try {
			// Load Scores Scene
			Parent root = scoresLoader.load();							// Declare root
			ScoresController scores = scoresLoader.getController();		// Create controller

			// Print High Scores
			scores.setTop5();
			scores.playerScore.setText("" + score);
			scores.newScorePane.setVisible(true);
			scores.inputPane.setVisible(true);

			// Set scene
			scene.setRoot(root);							// Makes scene visible
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Method that randomizes the location of the food on each spawn
	private void randomizeFood(Entity food){
		boolean collision;
		// prevent food from spawning inside snake
		do{
			collision = false;
			food.setTranslateX( (int) (Math.random() * ( width - blockSize)) / blockSize * blockSize);
			food.setTranslateY( (int) (Math.random() * (height - blockSize)) / blockSize * blockSize);

			// If food spawns inside snake, collision
			for(Node rect : snake)
				if (rect.getTranslateX() == food.getTranslateX()
				&& rect.getTranslateY() == food.getTranslateY())
					collision = true;
		} while (collision);
	}
}
