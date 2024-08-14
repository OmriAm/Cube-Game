// omri David Yonatans//

package Main;

import java.io.File;

import Controller.Controller;
import Controller.Database;
import Model.Model;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		File file = new File("Scores.txt");
		Database db = new Database();
		Model model = new Model(file, db);
		View view = new View(stage);
		Controller controller = new Controller(model, view);
		

	}

	

}


