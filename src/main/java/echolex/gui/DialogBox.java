package echolex.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog; // Label containing the text of the dialog
    @FXML
    private ImageView displayPicture; // ImageView displaying the speaker's face

    /**
     * Constructs a new DialogBox with the specified text and image.
     * Initializes the layout and sets the text and image for the dialog box.
     *
     * @param text the text to be displayed in the dialog box
     * @param img the image to be displayed in the ImageView
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
        displayPicture.setClip(
                new Circle(displayPicture.getFitWidth() / 2, displayPicture.getFitHeight() / 2,
                        Math.max(displayPicture.getFitWidth(), displayPicture.getFitHeight()) / 2
                )
        );
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Creates and returns a dialog box for the user with the specified text and image.
     *
     * @param text the text to be displayed in the dialog box
     * @param img the image to be displayed in the ImageView
     * @return a new DialogBox for the user
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates and returns a dialog box for EchoLex with the specified text, image, and command type.
     * The dialog box is flipped and styled based on the command type.
     *
     * @param text the text to be displayed in the dialog box
     * @param img the image to be displayed in the ImageView
     * @param commandType the type of command, used to style the dialog box
     * @return a new DialogBox for EchoLex
     */
    public static DialogBox getEchoLexDialog(String text, Image img, String commandType) {
        var db = new DialogBox(text, img);
        db.flip();
        db.changeDialogStyle(commandType);
        return db;
    }

    /**
     * Changes the style of the dialog box based on the specified command type.
     * Adds corresponding CSS classes to style the dialog box.
     *
     * @param commandType the command type that determines the style
     */
    private void changeDialogStyle(String commandType) {
        switch(commandType) {
        case "todo":
        case "event":
        case "deadline":
            dialog.getStyleClass().add("add-label");
            break;
        case "mark":
            dialog.getStyleClass().add("marked-label");
            break;
        case "delete":
            dialog.getStyleClass().add("delete-label");
            break;
        default:
            // Do nothing
        }
    }

}
