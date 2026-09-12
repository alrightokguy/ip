package chattingheads.ui.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box in the GUI containing a message.
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialogLabel;

    @FXML
    private ImageView displayPicture;

    /**
     * Creates a dialog box containing the specified text.
     *
     * @param text Text to display in the dialog box.
     */
    private DialogBox(String text) {
        FXMLLoader fxmlLoader = new FXMLLoader(
                MainWindowController.class.getResource("/view/DialogBox.fxml")
        );
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialogLabel.setText(text);
    }

    /**
     * Creates and returns a dialog box containing the specified text.
     *
     * @param text Text to display in the dialog box.
     * @return A dialog box containing the specified text.
     */
    public static DialogBox getDialog(String text) {
        return new DialogBox(text);
    }

    /**
     * Creates and returns a dialog box containing the specified text for the user.
     *
     * @param text Text to display in the dialog box.
     * @return A dialog box containing the specified text.
     */
    public static DialogBox getUserDialog(String text) {
        DialogBox dialogBox = new DialogBox(text);
        dialogBox.styleAsUserDialog();
        return dialogBox;
    }

    private void styleAsUserDialog() {
        getStyleClass().add("user-dialog");
        dialogLabel.getStyleClass().add("user-label");
    }
}
