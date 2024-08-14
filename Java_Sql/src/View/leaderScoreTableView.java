package View;

import Model.leaderScoreTable;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class leaderScoreTableView {

	private leaderScoreTable leadersTable;
	private Text player;

	public leaderScoreTableView(leaderScoreTable table) {
		this.leadersTable = table;
	}

	public void printTableView(VBox vbRigth) {
		for (int i = 0; i < leadersTable.getMaxNumOfPlayers(); i++) {
			if (this.leadersTable.getLeadersTable()[i] == null) {
				String str = "no-one" + "   " + 0;
				player = new Text(str);
			} else {
				String str = this.leadersTable.getLeadersTable()[i].playerToString() + "   " + (i + 1);
				player = new Text(str);
			}
			vbRigth.getChildren().addAll(player);
		}

	}

}
