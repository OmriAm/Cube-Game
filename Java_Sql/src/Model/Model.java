package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import Controller.Database;
import javafx.scene.Group;

public class Model {
	private int scoreCount;
	private PrintWriter pw;
	private FileWriter fw;
	private File file;
	private leaderScoreTable table;
	private Scanner s;
	private int[][] chosenCubesIndexes;
	private cubeField startGame;
	private int countClickedCubes;
	private Database db;
	//private int level;

	public Model(File f, Database db) {
		this.db = db;
		this.file = f;
		this.scoreCount = 0;
		this.table = new leaderScoreTable();
		//this.table = readTableFromDB(1);
		this.chosenCubesIndexes = new int[4][2];
		this.startGame = new cubeField(4);
		this.countClickedCubes = 0;

	}

	
    // calculating the score depending the chosen cubes, calculate their area //
	public void setScoreCount() {
		int width = 0, length = 0, sum;
		for(int i = 1; i < 4; i++) {
			if(this.chosenCubesIndexes[0][0] == this.chosenCubesIndexes[i][0]) {
				length = Math.abs(this.chosenCubesIndexes[0][1] - this.chosenCubesIndexes[i][1]) + 1;
			} else if(this.chosenCubesIndexes[0][1] == this.chosenCubesIndexes[i][1]) {
				width = Math.abs(this.chosenCubesIndexes[0][0] - this.chosenCubesIndexes[i][0]) + 1;
			}
			
		}
		sum = length * width;
		this.scoreCount += sum;
	}
	
	public void resetScore() {
		this.scoreCount = 0;
	}
	
	public void readTableFromDB(int level) {
		this.table = db.getLeadersBoard(level);
	}


	// check if the chosen cubes are in square shape //
	public boolean checkcubesShape() {
		int sameLine = 0;
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 4; i++) {
				for (int k = 0; k < 4; k++) {
					if (this.chosenCubesIndexes[i][j] == this.chosenCubesIndexes[k][j]) {
						sameLine++;
					}
				}
				if (sameLine != 2)
					return false;

				sameLine = 0;
			}

		}
		return true;
	}

	
	public void resetCountCubes() {
		if (countClickedCubes == 4) {
			this.countClickedCubes = 0;
			
		}
	}

	// write the leadertable into txt file //
	public void writeToText() {
		try {
			this.fw = new FileWriter(this.file, false);
			pw = new PrintWriter(new FileOutputStream(this.file, true));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.table.printTable(pw);
		pw.close();
		pw.flush();

	}

	// importent to know!! //
	public void setLeaderToTable(String str) {

		player p = new player(str, this.scoreCount);
		try {
			db.addPlayer(str); //add player to db
			int PID = db.getIDByName(str); // get PID by the name
			db.insert_newScore(PID, this.scoreCount, this.getLevel() - 3); // add score to leaders Table
			db.delWeakPlayer(db.getLeadersBoard(this.getLevel()), this.getLevel() - 3);
			db.addGame(PID, this.getLevel() - 3); // add game ID to db
			
			//System.out.println(this.getLevel());	
			
			db.addScore(this.scoreCount); // add game id and score to score db
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.table.setNewLeader(p);

	}
	
	
    //   read leaders table from file   //
	public void readTableFromFile() {

		try {
			this.s = new Scanner(this.file);
			while (s.hasNext()) {
				this.table.setNewLeader(new player(s));
			}
		} catch (FileNotFoundException e) {
			this.table.printTable(pw);
			e.printStackTrace();
			pw.close();
			pw.flush();
		}
		
		s.close();

	}

	
	
	// method get X,Y position and return index of one chosen cube from game field //
		public int[] choseCube(double x, double y, Group root) {
			int[] chosenCubeIndex = new int[2];
			
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (x >= i * this.startGame.getCubeSize() + root.getLayoutX()
							&& x < (i + 1) * this.startGame.getCubeSize() + root.getLayoutX()) {
						if (y >= j * this.startGame.getCubeSize() +root.getLayoutY()
								&& y < (j + 1) * this.startGame.getCubeSize() + root.getLayoutY()) {
							countClickedCubes++;
							
							chosenCubeIndex[0] = i;
							chosenCubeIndex[1] = j;
							
							return chosenCubeIndex;
						}

					}
				}
			}
			
			chosenCubeIndex[0] = -1;
			return chosenCubeIndex;
		}
	
	public void setCubesIndex(int[] indexArray) {
		if(indexArray[0] != -1) {
		this.chosenCubesIndexes[this.countClickedCubes - 1][0] = indexArray[0];
		this.chosenCubesIndexes[this.countClickedCubes - 1][1] = indexArray[1];
		}
		
	}
	
	//   getters and setters   //

	public int getCountClickedCubes() {
			return countClickedCubes;
		}
	
	
	public void setLevel(int level) {
		startGame.setLevel(level);
		
	}
	
	public int getLevel() {
		return startGame.getLevel();
		
	}
	
	public File getFile() {
		return file;
	}
	
	public int[][] getChosenCubesIndexes() {
		return chosenCubesIndexes;
	}
	
	public int getScoreCount() {
		return scoreCount;
	}
	
	public leaderScoreTable getTable() {
		return table;
	}
	
	
	
	

}
