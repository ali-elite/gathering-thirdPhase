package ir.sharif.ap2021.client.View.Menu;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.NewGroupListener;
import ir.sharif.ap2021.shared.Event.NewGroupEvent;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GroupMessage implements Initializable {

    ErrorConfig errorConfig = new ErrorConfig();
    NewGroupListener newGroupListener = new NewGroupListener();

    private static ArrayList<String> users;
    private String selectedUser;

    @FXML
    private ListView<String> selectedList, followerList;
    @FXML
    private TextArea messageText;
    @FXML
    private ImageView messageIMG;


    private boolean isChanged;


    public GroupMessage() throws IOException {
    }


    public static void setUsers(ArrayList<String> users) {
        GroupMessage.users = users;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        followerList.getItems().addAll(users);


        followerList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                selectedUser = followerList.getSelectionModel().getSelectedItem();
                if (!selectedList.getItems().contains(selectedUser)) {

                    Platform.runLater(() -> {
                        if (selectedUser != null) {
                            selectedList.getItems().add(selectedUser);
                            followerList.getItems().remove(selectedUser);
                            followerList.getSelectionModel().select(-1);
                        }
                    });
                }


            }
        });


    }


    public void sendMessage(ActionEvent event) throws IOException {

        ArrayList<String> names = new ArrayList<>(selectedList.getItems());

        if (messageText.getText() != null) {

            NewGroupEvent newGroupEvent = new NewGroupEvent("groupMessage", names, messageText.getText());

            if (isChanged) {
                newGroupEvent.setChanged("changed");
            } else {
                newGroupEvent.setChanged("no");
            }


            if (messageText.getText().length() <= 300) {
                newGroupListener.listen(newGroupEvent);
            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(errorConfig.getMessageLength());
                alert.showAndWait();

            }


        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getEnterMessage());
            alert.showAndWait();

        }


    }

    public void attach(ActionEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        File file = fileChooser.showOpenDialog(StaticController.getMyStage());


        if (file != null) {

            Image img = new Image(file.toURI().toString());

            saveToFile(img, "311");
            messageIMG.setImage(img);

            isChanged = true;
        } else isChanged = false;

    }

    public void back(ActionEvent event) throws IOException {
        StaticController.getMyMainMenu().show();
    }

    public void resetList(ActionEvent event) {

        followerList.getItems().clear();
        selectedList.getItems().clear();
        initialize(null, null);
    }

    public void saveToFile(Image image, String name) throws IOException {

        File fileOutput = new File(errorConfig.getMainConfig().getResourcesPath() + "/MessageImages/" + name + ".png");

        if (fileOutput.exists()) {
            fileOutput.delete();
        }

        BufferedImage Bi = SwingFXUtils.fromFXImage(image, null);
        ImageIO.write(Bi, "png", fileOutput);
    }

}
