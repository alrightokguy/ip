package chattingheads;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainWindowController.class.getResource("/view/DialogBox.fxml")
            );
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialog.setText(text);
    }

    private DialogBox(String text, Image image) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    MainWindowController.class.getResource("/view/DialogBox.fxml")
            );
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        displayPicture.setImage(image);
        dialog.setText(text);
    }

    public static DialogBox getDialog(String text) {
        return new DialogBox(text);
    }

    public static DialogBox getStartupDialog(String text, Image image) {
        return new DialogBox (text, image);
    }
}
