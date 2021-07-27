package ir.sharif.ap2021.client.View.Menu;

import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.MainMenuListener;
import ir.sharif.ap2021.shared.Event.MainMenuEvent;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Blacklist implements Initializable {

    MainMenuListener mainMenuListener = new MainMenuListener(null);
    private static ArrayList<String> users = new ArrayList<>();


    @FXML
    private ListView<String> blackList;

    public Blacklist() throws IOException {
    }


    public static ArrayList<String> getUsers() {
        return users;
    }

    public static void setUsers(ArrayList<String> users) {
        Blacklist.users = users;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        blackList.getItems().addAll(users);


        blackList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                String selectedUser = blackList.getSelectionModel().getSelectedItem();

                if (selectedUser != null){

                    MainMenuEvent mainMenuEvent = new MainMenuEvent("unblock", StaticController.getMyUser().getId());
                    mainMenuEvent.setSelectedUser(selectedUser);
                    mainMenuListener.listen(mainMenuEvent);

                    Platform.runLater(() -> {
                    blackList.getItems().remove(selectedUser);
                    blackList.getSelectionModel().select(-1);
                });

            }

            }
        });


    }


    public void back(ActionEvent event) throws IOException {
        StaticController.getMyMainMenu().show();
    }

}
