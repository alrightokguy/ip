package chattingheads;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A launcher class to start up the GUI
 */
public class Launcher extends Application {

    @Override
    public void start(Stage stage) {
        ChattingHeads chattingHeads = new ChattingHeads();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ChattingHeads.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            fxmlLoader.<MainWindowController>getController().setChattingHeads(chattingHeads);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
