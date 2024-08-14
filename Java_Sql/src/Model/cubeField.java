package Model;

import javafx.scene.shape.Rectangle;

public class cubeField {
	
	private final int numOfLine = 10;
	private final int cubeSize = 40;
	private Rectangle[][] cubesArray;
	private int level;
	
	public cubeField(int level) {
		this.level = level;
		this.cubesArray = new Rectangle[numOfLine][numOfLine];
		
	}

	public void setLevel(int level) {
		this.level = level;
	}



	public int getLevel() {
		return level;
	}



	public int getNumOfLine() {
		return numOfLine;
	}

	public int getCubeSize() {
		return cubeSize;
	}

	public Rectangle[][] getCubesArray() {
		return cubesArray;
	}

	public Rectangle getCube(int i, int j) {
		return this.cubesArray[i][j];
	}


	

	}
	

	


