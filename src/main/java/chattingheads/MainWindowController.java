package chattingheads;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
        dialogContainer.getChildren().add(
                DialogBox.getBotDialog(
                        chattingHeads.getStartupMessage()
                )
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
                DialogBox.getUserDialog("> " + input),
                DialogBox.getBotDialog(result.response())
        );

        if (result.isExit()) {
            userInput.setDisable(true);
        }

        userInput.clear();
    }
}
