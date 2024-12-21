package zs1.timetabler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TimetablerApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Parent root = FXMLLoader.load(this.getClass().getResource("timetabler_fxml.fxml"));
        Scene scene = new Scene(root, 300, 275);
        stage.setTitle("Timetabler");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
