package echolex.gui;

import java.io.IOException;

import echolex.EchoLex;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private final EchoLex echoLex = new EchoLex("./data/EchoLex.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setEchoLex(echoLex);  // inject the EchoLex instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

