package ir.sharif.ap2021.client.View.Menu;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.ShareThoughtListener;
import ir.sharif.ap2021.shared.Event.ShareThoughtEvent;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NewThought {

    ShareThoughtListener shareThoughtListener = new ShareThoughtListener(this);
    ErrorConfig errorConfig = new ErrorConfig();

    @FXML
    private TextArea text;
    @FXML
    private Button shareBtn, backBtn, photoBtn;
    @FXML
    private ImageView IMG;

    private boolean isChanged;

    public NewThought() throws IOException {
    }


    public void share(ActionEvent event) throws IOException {

        ShareThoughtEvent shareThoughtEvent = new ShareThoughtEvent(text.getText(), StaticController.getMyUser().getId());
        if (isChanged) {
            shareThoughtEvent.setChange("changed");
            isChanged = false;
        } else {
            shareThoughtEvent.setChange("no");
        }

        if (text.getText().length() <= 300) {
            shareThoughtListener.listen(shareThoughtEvent);
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorConfig.getThoughtLength());
            alert.showAndWait();

        }

    }

    public void putPhoto(ActionEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        File file = fileChooser.showOpenDialog(StaticController.getMyStage());


        if (file != null) {

            Image img = new Image(file.toURI().toString());

            saveToFile(img, "733");

            IMG.setImage(img);

            isChanged = true;
        } else isChanged = false;


    }

    public void saveToFile(Image image, String name) throws IOException {

        File fileOutput = new File(errorConfig.getMainConfig().getResourcesPath() + "/ThoughtImages/" + name + ".png");

        if (fileOutput.exists()) {
            fileOutput.delete();
        }

        BufferedImage Bi = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(Bi, "png", fileOutput);
    }

    public void back(ActionEvent event) throws IOException {
        StaticController.getMyMainMenu().show();
    }
}
