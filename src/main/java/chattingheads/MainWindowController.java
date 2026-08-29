package chattingheads;

import javafx.fxml.FXML;
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
    @FXML
    private Button sendButton;

    private ChattingHeads chattingHeads;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects an instance of the chatbot into the controller.
     *
     * @param chattingHeads Instance of the chatbot to be injected.
     */
    public void setChattingHeads(ChattingHeads chattingHeads) {
        this.chattingHeads = chattingHeads;
    }

    /**
     * Starts user input.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = chattingHeads.getResponse(input);
        userInput.clear();
    }
}
