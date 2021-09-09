package main.controllers;

import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;

public class HelpPopup extends Application {

	Stage stage = new Stage();

	public HelpPopup() {
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		TextFlow tf = new TextFlow();
		tf.setLineSpacing(1.0);

		for (String sentence : WORDS) {
			Text text = new Text();
			text.setFont(Font.font("Chandra", FontPosture.REGULAR, 15));
			text.setFill(Color.WHITE);
			if (sentence.startsWith("-h-")) {
				text.setFont(Font.font("Chandra", FontPosture.REGULAR, 20));
				sentence = sentence.substring(3);
			}
			text.setText(sentence);
			tf.getChildren().add(text);
		}
		StackPane layout = new StackPane();
		layout.getChildren().add(tf);
		Scene scene = new Scene(layout, 600, 290);
		scene.getStylesheets().add(getClass().getResource("/main/resources/css/application.css").toExternalForm());
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
	}

	public void show(Bounds bounds) {
		try {
			start(stage);
			stage.setX(bounds.getCenterX());
			stage.setY(bounds.getCenterY());
			stage.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void hide() {
		stage.close();
	}

	private static final String[] WORDS = new String[] { "-h-Creating the user names for year groups\n\n",
			"Click on the choice year group and the year of addition. ",
			"For a single username only the forename and surname is required. ",
			"For multiple usernames you can click the browse button for an .xlsx and then click generate to create the usernames.\n",
			"The file must have the first row allocated to stating what each coloumn is.",
			" The file must be in the order: Surname,Forename\n\n",
			"Once you see the message that the usernames have been create, you can preview them with the preview button. "
					+ "This allows you to change some of the usernames as you wish. \n\n",
			"Press the save button to create and save files needed to add the student(s) to the school system" };
}
