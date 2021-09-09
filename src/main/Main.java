package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	public static Image logo;

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane) FXMLLoader
					.load(getClass().getResource("/main/resources/views/fxml/Main.fxml"));
			root.setTop(new FXMLLoader(getClass().getResource("/main/resources/views/fxml/HelpBar.fxml")).load());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/main/resources/css/application.css").toExternalForm());
			logo = new Image(getClass().getClassLoader().getResourceAsStream("logo.png"));
			primaryStage.getIcons().add(Main.logo);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
