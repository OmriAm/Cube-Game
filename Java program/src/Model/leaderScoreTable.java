package Model;

import java.io.PrintWriter;

public class leaderScoreTable {
	private player[] leadersTable;
	private final int MaxNumOfPlayers = 10; 

	public int getMaxNumOfPlayers() {
		return MaxNumOfPlayers;
	}

	public leaderScoreTable() {
		this.leadersTable = new player[MaxNumOfPlayers];
	}

	public void setNewLeader(player p) {
		player tempPlayer = new player("", 0);
		for (int i = 0; i < MaxNumOfPlayers; i++) {
			if (leadersTable[i] == null) {
				leadersTable[i] = p;
				break;
			} else if (leadersTable[i].getScore() < p.getScore()) {
				tempPlayer = leadersTable[i];
				leadersTable[i] = p;
				p = tempPlayer;
			}
		}
	}
	
	public void printTable(PrintWriter pw) {
		for (int i = 0; i < MaxNumOfPlayers; i++) {
			if (leadersTable[i] == null) {
				pw.println("no-one"+ "   " + 0);
			} else {
			pw.println(leadersTable[i].getName() + "  " + leadersTable[i].getScore());
			
			}
		}
	}

	public void setLeadersTable(player[] leadersTable) {
		this.leadersTable = leadersTable;
	}

	public player[] getLeadersTable() {
		return leadersTable;
	}
	
	
	
	

}
