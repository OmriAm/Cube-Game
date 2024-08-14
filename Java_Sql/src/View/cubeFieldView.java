package View;

import java.util.Random;

import Model.cubeField;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class cubeFieldView {
	private cubeField cubesArray;

	public cubeFieldView(int level) {
		this.cubesArray = new cubeField(level);
		for (int i = 0; i < this.cubesArray.getNumOfLine(); i++) {
			for (int j = 0; j < this.cubesArray.getNumOfLine(); j++) {
				cubesArray.getCubesArray()[i][j] = addCube(i, j, level);
			}
		}
	}

	// creating new cube //
	private Rectangle addCube(int i, int j, int level) {
		Rectangle cube = new Rectangle(i * cubesArray.getCubeSize(), j * cubesArray.getCubeSize(),
				cubesArray.getCubeSize(), cubesArray.getCubeSize());
		cube.setStroke(Color.BLACK);
		return setCubeColor(cube, level);
	}

	public void bolbCube(int i, int j) {
		if (i != -1)
			this.cubesArray.getCubesArray()[i][j].setStrokeWidth(4.00);
	}

	public void unBolbCube() {
		for (int i = 0; i < this.cubesArray.getNumOfLine(); i++) {
			for (int j = 0; j < this.cubesArray.getNumOfLine(); j++) {
				cubesArray.getCubesArray()[i][j].setStrokeWidth(1.00);
			}
		}
	}

	// Choosing random color for cube, number of the colors depending on the level
	// of the game //
	private Rectangle setCubeColor(Rectangle cube, int level) {
		Random r = new Random();
		int rndNum = r.nextInt(level);
		switch (rndNum) {
		case 0:
			cube.setFill(Color.RED);
			return cube;
		case 1:
			cube.setFill(Color.BLUE);
			return cube;
		case 2:
			cube.setFill(Color.GREEN);
			return cube;
		case 3:
			cube.setFill(Color.PURPLE);
			return cube;
		case 4:
			cube.setFill(Color.YELLOW);
			return cube;
		default:
			cube.setFill(Color.LIGHTPINK);
			return cube;

		}
	}

	public boolean checkSameColor(int[][] cubeIndexArray) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (getCubeColor(getCube(cubeIndexArray[i][0], cubeIndexArray[1][1])) != getCubeColor(
						getCube(cubeIndexArray[j][0], cubeIndexArray[j][1])))
					return false;
			}
		}
		return true;
	}

	public void show(Group root) {
		root.getChildren().clear();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				root.getChildren().addAll(this.cubesArray.getCubesArray()[i][j]);
			}
		}

	}

	// getters and setters //

	public void setColorByIndex(int i, int j, int level) {
		setCubeColor(cubesArray.getCubesArray()[i][j], level);
	}

	public int getCubeSize() {
		return cubesArray.getCubeSize();
	}

	public Paint getCubeColor(Rectangle rtc) {
		return rtc.getFill();
	}

	public int getLevelView() {
		return this.cubesArray.getLevel();
	}

	public void setLevelView(int level) {
		this.cubesArray.setLevel(level);
	}

	public Rectangle[][] getCubesArray() {
		return cubesArray.getCubesArray();
	}

	public Rectangle getCube(int i, int j) {
		return this.cubesArray.getCubesArray()[i][j];
	}

}
