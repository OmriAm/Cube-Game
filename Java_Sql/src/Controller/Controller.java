package Controller;

import javax.swing.JOptionPane;
import Model.*;
import View.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class Controller {

	private View view;
	private Model model;

	public Controller(Model m, View v) {
		model = m;
		view = v;
		
		model.readTableFromDB(model.getLevel() -3); // read table from db
		view.printTableView(model.getTable()); // show view of the table
		/*
		 * if (model.getFile().exists()) { model.readTableFromFile();
		 * view.printTableView(model.getTable()); }
		 */
		// start new game button action //

		view.getNG().setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				view.setLevel(view.getLevel());
				String setName = JOptionPane.showInputDialog(null, "Please enter your name", "Save your score",
						JOptionPane.INFORMATION_MESSAGE);
				if (setName != null)
					model.setLeaderToTable(setName);
				model.writeToText();
				model.resetScore();
				view.setScoreView(model.getScoreCount(), model.getTable());
				//model.readTableFromDB();
				view.printTableView(model.getTable());
				//view.printTableView(model.readTableFromDB(model.getLevel() - 3));

			}

		});

		// level 1 button action //

		view.getL1().setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				view.setLevel(4);
				model.setLevel(4);
				model.resetScore();
				view.setScoreView(model.getScoreCount(), model.getTable());
				model.readTableFromDB(1);//model.getLevel()
				view.printTableView(model.getTable());
			}

		});

		// level 2 button action //

		view.getL2().setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				view.setLevel(5);
				model.setLevel(5);
				model.resetScore();
				view.setScoreView(model.getScoreCount(), model.getTable());
				model.readTableFromDB(2);
				view.printTableView(model.getTable());
			}

		});

		// level 3 button action //

		view.getL3().setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				view.setLevel(6);
				model.setLevel(6);
				model.resetScore();
				view.setScoreView(model.getScoreCount(), model.getTable());
				model.readTableFromDB(3);
				view.printTableView(model.getTable());
			}

		});

		// clicked action //

		EventHandler<MouseEvent> click = new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				int[] cubeIndex = model.choseCube(event.getX(), event.getY(), view.getRoot());

				model.setCubesIndex(cubeIndex);
				view.bolbCube(cubeIndex); // mark cube when clicked

				if (model.getCountClickedCubes() == 4) {
					if (model.checkcubesShape()) { // check if the cube in square shape
						if (view.checkSameColor(model.getChosenCubesIndexes())) {// check if in same color
							model.setScoreCount(); // Calculate the score
							view.setScoreView(model.getScoreCount(), model.getTable());// showing the sew score
							view.changeColor(model.getChosenCubesIndexes(), view.getLevel());// changing the cubes color

						}

					}

				}

				view.unBolbCube(model.getCountClickedCubes());
				model.resetCountCubes();
			}
		};

		view.AddMouseClick(click);

	}

}
