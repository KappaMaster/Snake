import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;
public class Main extends Application implements EventHandler<ActionEvent>{

	@Override
	public void start(Stage primaryStage)throws Exception{
		
		primaryStage.setTitle("This is the window");
		StackPane layout = new StackPane();
		GridPane layout2 = new GridPane();
		StackPane layout4 = new StackPane();
		StackPane layout3 = new StackPane();
		//layout2.setVgap(0);
		//layout2.setHgap(0);
		//layout2.setPadding(new Insets(200,200,200,200));
		//layout2.setMinSize(800,800);
		
		Scene mainMenu = new Scene(layout,800,800);
		Scene optionsMenu = new Scene(layout4, 800, 800);
		Scene leaderboard = new Scene(layout3, 800, 800);
		
		//Background Images
		Image imageMain = new Image("https://i.imgur.com/YnxWR3g.png");
		ImageView iv = new ImageView();
		iv.setImage(imageMain);
		layout.getChildren().add(iv);
		
		Image imageMain2 = new Image("https://i.imgur.com/cDyk6Bh.png");
		ImageView iv2 = new ImageView();
		iv2.setImage(imageMain2);
		layout4.getChildren().add(iv2);
		layout4.getChildren().add(layout2);
		
		Image imageMain3 = new Image("https://i.imgur.com/HhgihTY.png");
		ImageView iv3 = new ImageView();
		iv3.setImage(imageMain3);
		layout3.getChildren().add(iv3);

		
		//should probably move to the end 
		primaryStage.setScene(mainMenu);
		primaryStage.show();
		
		primaryStage.setScene(mainMenu);
		
		//first scene, main menu
	
		
		Button btStart = new Button("Start Game");
		Button btOption = new Button("Options");
		Button btLeaderboard = new Button("High Scores");
		Button btExit = new Button("Exit :(");
		
		btStart.setPrefSize(200, 50);
		btStart.setStyle("-fx-font-size: 2em; ");
		btOption.setPrefSize(200, 50);
		btOption.setStyle("-fx-font-size: 2em; ");
		btLeaderboard.setPrefSize(200, 50);
		btLeaderboard.setStyle("-fx-font-size: 2em; ");
		btExit.setPrefSize(200, 50);
		btExit.setStyle("-fx-font-size: 2em; ");
		
		VBox vbox1 = new VBox();
		vbox1.getChildren().add(btStart);
		vbox1.getChildren().add(btOption);
		vbox1.getChildren().add(btLeaderboard);
		vbox1.getChildren().add(btExit);

		vbox1.setSpacing(50);
		vbox1.setAlignment(Pos.CENTER);
		layout.getChildren().add(vbox1);
		
		btStart.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				System.out.println("Game Started");
			}
		});
		btOption.setOnAction(e -> primaryStage.setScene(optionsMenu));
		btLeaderboard.setOnAction(e -> primaryStage.setScene(leaderboard));
		btExit.setOnAction(e -> System.exit(5));
		
		//stuff for the 2nd scene OPtions
		
		//Hbox containing label for title 
		HBox title = new HBox();
		Label optionsTitle = new Label("Welcome to Options");
		title.setAlignment(Pos.TOP_CENTER);
		optionsTitle.setStyle("-fx-font-size: 2em; ");
		layout2.getChildren().add(title);
		GridPane.setConstraints(title,2,1);
		title.getChildren().add(optionsTitle);

		//VBox made of hbox and labels for speed setting
		VBox speedSetting = new VBox();
		speedSetting.setPadding(new Insets(50,50,50,50));
		speedSetting.setAlignment(Pos.CENTER_LEFT);
		GridPane.setConstraints(speedSetting,3,5);
		layout2.getChildren().add(speedSetting);
	
		
		Label speedLabel = new Label("Snake Speed");
		speedLabel.setStyle("-fx-font-size: 3em; ");
		speedSetting.getChildren().add(speedLabel);
		
		HBox plusMinus = new HBox();
		plusMinus.setAlignment(Pos.CENTER_LEFT);
		plusMinus.setSpacing(120);
		speedSetting.getChildren().add(plusMinus);
		
		Button btPlus = new Button(" + ");
		Button btMinus = new Button(" - ");
		btPlus.setStyle("-fx-font-size: 2em; ");
		btMinus.setStyle("-fx-font-size: 2em; ");
		btPlus.setOnAction(e -> System.out.println("Plus"));
		btMinus.setOnAction(e -> System.out.println("Minus"));

		plusMinus.getChildren().add(btMinus);
		plusMinus.getChildren().add(btPlus);
		

		//Back to main Menu button
		HBox hBack = new HBox();
		hBack.setAlignment(Pos.BOTTOM_RIGHT);
		layout2.getChildren().add(hBack);
		Button btBack = new Button("BACK");
		hBack.setPadding(new Insets(10,10,10,10));
		btBack.setOnAction(e -> primaryStage.setScene(mainMenu));
		btBack.setAlignment(Pos.BOTTOM_RIGHT);
		btBack.setStyle("-fx-font-size: 2em; ");
		GridPane.setConstraints(hBack,1,10);
		hBack.getChildren().add(btBack);
		//hBack.setStyle("-fx-background-color:POWDERBLUE");

		
		//Gonna try to add a dropdown for color
		//When instancing the snake, set the color as layout2.colorChoice.getValue();
		HBox hChoice = new HBox();
		hChoice.setSpacing(50);
		hChoice.setAlignment(Pos.CENTER_RIGHT);
		hChoice.setPadding(new Insets(30,30,30,30));
		GridPane.setConstraints(hChoice,1,5);
		layout2.getChildren().add(hChoice);
		
		Label lbColor = new Label("Color:");
		lbColor.setStyle("-fx-font-size: 3em; ");
		hChoice.getChildren().add(lbColor);
		
		ChoiceBox<String> colorChoice = new ChoiceBox<String>();
		colorChoice.getItems().addAll("Purple", "Blue", "Black");
		hChoice.getChildren().add(colorChoice);
		colorChoice.setValue("Purple");
		//String test = colorChoice.getValue();

		
		
		//Leaderboard
		
		
		
		VBox leaderBox = new VBox();
		leaderBox.setAlignment(Pos.TOP_CENTER);
		HBox hScore1 = new HBox();
		HBox hScore2 = new HBox();
		HBox hScore3 = new HBox();
		HBox hScore4 = new HBox();
		HBox hScore5 = new HBox();
		
		Label lbLeaderboard = new Label("Top Scores");
		lbLeaderboard.setPadding(new Insets(100,100,100,100));

		lbLeaderboard.setStyle("-fx-font-size: 3em; -fx-text-fill: purple;");
		
		//leaderBox.setStyle("-fx-background-color:POWDERBLUE");
		layout3.getChildren().add(leaderBox);
		leaderBox.getChildren().add(lbLeaderboard);
		leaderBox.getChildren().add(hScore1);
		leaderBox.getChildren().add(hScore2);
		leaderBox.getChildren().add(hScore3);
		leaderBox.getChildren().add(hScore4);
		leaderBox.getChildren().add(hScore5);
		
		hScore1.setSpacing(50);
		hScore2.setSpacing(50);
		hScore3.setSpacing(50);
		hScore4.setSpacing(50);
		hScore5.setSpacing(50);
		
		Label lb1 = new Label("1)");
		Label lb2 = new Label("2)");
		Label lb3 = new Label("3)");
		Label lb4 = new Label("4)");
		Label lb5 = new Label("5)");
	
		//when data base is implemented use if name != null set as the name, else set as blank string 
		//also I'd kinda like to justify the numbers to the left side but I'm not sure how to do that 
		Label firstPlace = new Label("First Place Name");
		Label secondPlace = new Label("Second Place Name");
		Label thirdPlace = new Label("Third Place Name");
		Label fourthPlace = new Label("Fourth Place Name");
		Label fifthPlace = new Label("Fifth Place Name");
		Label firstScore = new Label("First Score");
		Label secondScore = new Label("Second Score");
		Label thirdScore = new Label("Third Score");
		Label fourthScore = new Label("Fourth Score");
		Label fifthScore= new Label("Fifth Score");

		
		hScore1.getChildren().add(lb1);
		hScore2.getChildren().add(lb2);
		hScore3.getChildren().add(lb3);
		hScore4.getChildren().add(lb4);
		hScore5.getChildren().add(lb5);
		
		hScore1.getChildren().add(firstPlace);
		hScore2.getChildren().add(secondPlace);
		hScore3.getChildren().add(thirdPlace);
		hScore4.getChildren().add(fourthPlace);
		hScore5.getChildren().add(fifthPlace);

		
		hScore1.getChildren().add(firstScore);
		hScore2.getChildren().add(secondScore);
		hScore3.getChildren().add(thirdScore);
		hScore4.getChildren().add(fourthScore);
		hScore5.getChildren().add(fifthScore);
		
		hScore1.setAlignment(Pos.CENTER);
		hScore2.setAlignment(Pos.CENTER);
		hScore3.setAlignment(Pos.CENTER);
		hScore4.setAlignment(Pos.CENTER);
		hScore5.setAlignment(Pos.CENTER);
		
		//back button
				HBox kevin = new HBox();
				kevin.setAlignment(Pos.BOTTOM_RIGHT);
				layout3.getChildren().add(kevin);
				Button btBackMain = new Button("BACK");
				kevin.setPadding(new Insets(10,10,10,10));
				btBackMain.setOnAction(e -> primaryStage.setScene(mainMenu));
				btBackMain.setAlignment(Pos.BOTTOM_RIGHT);
				btBackMain.setStyle("-fx-font-size: 2em; ");
				kevin.getChildren().add(btBackMain);
	}
	@Override
	public void handle(ActionEvent event){
		System.out.println("Another Button was pressed");
	}
	public static void main(String[] args) {
		launch(args);

	}

}
