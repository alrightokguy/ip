package chattingheads;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A launcher class to start up the GUI
 */
public class Launcher extends Application {

    @Override
    public void start(Stage stage) {
        ChattingHeads chattingHeads = new ChattingHeads();

        // Set up GUI
    }

    public static void main(String[] args) {
        launch(args);
    }
}
