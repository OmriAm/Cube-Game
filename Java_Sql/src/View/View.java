package View;

import Model.leaderScoreTable;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class View {
	private Stage primaryStage;
	private Group root;
	private Scene scene;
	private BorderPane bp;
	private Pane drawPane;
	private VBox vbLeft, vbRight;
	private Button NG;
	private Button L1;
	private Button L2;
	private Button L3;
	private cubeFieldView startGame;

	public Group getRoot() {
		return root;
	}

	private Text score, leadersTable, scoreTitle;
	private leaderScoreTableView LS;

	public View(Stage stage) {
		primaryStage = stage;
		root = new Group();
		

		// create buttons //

		NG = new Button("New Game - save score");

		L1 = new Button("Level 1");

		L2 = new Button("Level 2");

		L3 = new Button("Level 3");

		// leftBox //

		vbLeft = new VBox();
		vbLeft.getChildren().addAll(NG, L1, L2, L3);
		vbLeft.setAlignment(Pos.CENTER_LEFT);

		// Right top Box //

		vbRight = new VBox();
		this.scoreTitle = new Text("Your Score:");
		this.score = new Text("" + 0);
		this.leadersTable = new Text("leaders Table (L: 1)");
		vbRight.getChildren().addAll(scoreTitle, score, leadersTable);
		vbRight.setAlignment(Pos.TOP_RIGHT);

		// Set Title //

		Text title = new Text("Cube Color Game");
		HBox topBox = new HBox();
		topBox.setPadding(new Insets(15, 0, 15, 0));
		topBox.getChildren().add(title);
		topBox.setAlignment(Pos.TOP_CENTER);

		

		// draw pane //

		drawPane = new Pane();

		
		startGame(4); // showing the game field when the game begin at the first time

		bp = new BorderPane();
		bp.setLeft(vbLeft);
		bp.setRight(vbRight);
		bp.setTop(topBox);
		bp.setCenter(root);

		scene = new Scene(bp, 700, 550);
		scene.setFill(Color.LIGHTGRAY);

		primaryStage.setTitle("Color Cube Game");
		primaryStage.setScene(scene);

		primaryStage.show();

	}

	

	public void setLevel(int level) {
		this.startGame.setLevelView(level);
		startGame(level);
	}

	// start the game by showing the game field on screen //
	public void startGame(int level) {
		vbRight.getChildren().removeAll(leadersTable);
		this.leadersTable = new Text("leaders Table (L: " + (level -3) + "): ");
		vbRight.getChildren().addAll(leadersTable);

		this.startGame = new cubeFieldView(level);
		startGame.show(root);

	}

	// check if the chosen cube are in the same color //
	public boolean checkSameColor(int[][] cubeIndexArray) {
		if (this.startGame.checkSameColor(cubeIndexArray))
			return true;
		else
			return false;

	}
	
    // show the score on the screen  //
	public void setScoreView(int s, leaderScoreTable leaderTable) {
		vbRight.getChildren().removeAll(this.score, leadersTable);
		this.score = new Text(String.valueOf(s));
		vbRight.getChildren().addAll(score);
		vbRight.getChildren().addAll(leadersTable);
		printTableView(leaderTable);

	}

	public void AddMouseClick(EventHandler<MouseEvent> click) {
		scene.addEventHandler(MouseEvent.MOUSE_CLICKED, click);
	}

	public void bolbCube(int[] cubeIndex) {

		this.startGame.bolbCube(cubeIndex[0], cubeIndex[1]);
		startGame.show(root);
	}

	public void unBolbCube(int countClickedCube) {
		if (countClickedCube == 4) {
			this.startGame.unBolbCube();
			startGame.show(root);
		}
	}

	private void removeVbRigth() {
		vbRight.getChildren().clear();
		vbRight.getChildren().addAll(scoreTitle, score, leadersTable);

	}
	
    
	//   showing leaders on screen   //
	public void printTableView(leaderScoreTable leaderTable) {
		removeVbRigth();

		this.LS = new leaderScoreTableView(leaderTable);
		LS.printTableView(vbRight);
	}

	// change cube color, depends on level //
	public void changeColor(int[][] chosenCubesArray, int level) {
		for (int i = 0; i < chosenCubesArray.length; i++) {
			startGame.setColorByIndex(chosenCubesArray[i][0], chosenCubesArray[i][1], level);
		}
		startGame.show(root);
	}
	
	
	// getter and setters //

		public void setScore(Text score) {
			this.score = score;
		}

		public Button getNG() {
			return NG;
		}

		public Button getL1() {
			return L1;
		}

		public Button getL2() {
			return L2;
		}

		public Button getL3() {
			return L3;
		}

		public int getLevel() {
			return this.startGame.getLevelView();
		}

}
