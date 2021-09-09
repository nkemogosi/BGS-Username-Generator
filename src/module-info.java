module usernameGeneratorV2 {
	requires org.apache.poi.poi;
	requires org.apache.poi.ooxml;
	requires org.apache.commons.compress;
	// requires google.api.client;
	requires java.desktop;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;

	opens main to javafx.graphics, javafx.fxml;
	opens main.controllers to javafx.graphics, javafx.fxml, javafx.controls;
}
