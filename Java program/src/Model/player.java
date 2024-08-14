package Model;

import java.util.Scanner;

public class player {
	private String name;
	private int score;
	
	
	public player(String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	public player(Scanner s) {
		this.name = s.next();
		this.score = s.nextInt();
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}
	
	public String playerToString() {
		return this.score + "  :S    " + this.name;
	}
	

	

}


