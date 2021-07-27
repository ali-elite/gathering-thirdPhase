package ir.sharif.ap2021.client.View.Menu;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.NewGroupListener;
import ir.sharif.ap2021.shared.Event.NewGroupEvent;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.io.ByteArrayOutputStream;
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


    private byte[] data;


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
            if(data != null){
                newGroupEvent.setImage(data);
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

            BufferedImage bImage = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos);
            data = bos.toByteArray();

            Image img = new Image(file.toURI().toString());
            messageIMG.setImage(img);
        }

    }

    public void back(ActionEvent event) throws IOException {
        StaticController.getMyMainMenu().show();
    }

    public void resetList(ActionEvent event) {

        followerList.getItems().clear();
        selectedList.getItems().clear();
        initialize(null, null);

    }

}
