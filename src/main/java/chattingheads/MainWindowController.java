package chattingheads;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Controller for the main GUI.
 */
public class MainWindowController extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button enterButton;

    private ChattingHeads chattingHeads;

    @FXML
    public void initialize() {
        dialogContainer.setAlignment(Pos.BOTTOM_LEFT);
        dialogContainer.minHeightProperty().bind(
                scrollPane.heightProperty()
        );
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects an instance of the chatbot into the controller.
     *
     * @param chattingHeads Instance of the chatbot to be injected.
     */
    public void setChattingHeads(ChattingHeads chattingHeads) {
        this.chattingHeads = chattingHeads;

        ImageView startupImage = new ImageView(
                new Image(getClass().getResourceAsStream("/images/TalkingHeadsRemaininLight.png"))
        );
        startupImage.setFitWidth(100);
        startupImage.setFitHeight(100);
        startupImage.setPreserveRatio(true);

        dialogContainer.getChildren().add(startupImage);
        dialogContainer.getChildren().add(
                DialogBox.getDialog(chattingHeads.getStartupMessage())
        );
    }

    /**
     * Starts user input.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        CommandResult result = chattingHeads.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getDialog("> " + input),
                DialogBox.getDialog(result.response())
        );
        userInput.clear();

        if (result.isExit()) {
            userInput.setDisable(true);
            enterButton.setDisable(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> Platform.exit());
            pause.play();
        }
    }
}
