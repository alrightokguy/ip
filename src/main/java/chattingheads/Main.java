package chattingheads;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for ChattingHeads using FXML.
 */
public class Main extends Application {

    private final ChattingHeads chattingHeads = new ChattingHeads();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(400);
            stage.setMinWidth(600);
            fxmlLoader.<MainWindowController>getController().setChattingHeads(chattingHeads);  // inject the Duck instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
