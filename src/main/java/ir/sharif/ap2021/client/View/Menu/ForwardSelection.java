package ir.sharif.ap2021.client.View.Menu;

import ir.sharif.ap2021.client.Config.FxmlConfig;
import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.MainMenuListener;
import ir.sharif.ap2021.shared.Event.MainMenuEvent;
import ir.sharif.ap2021.shared.Model.Thought;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ForwardSelection implements Initializable {

    FxmlConfig fxmlConfig = new FxmlConfig();
    MainMenuListener mainMenuListener = new MainMenuListener(null);
    private static Thought thought;
    private final static ArrayList<Pane> chats = new ArrayList<>();

    @FXML
    private ScrollPane chatScroll;

    public ForwardSelection() throws IOException {
    }


    public static Thought getThought() {
        return thought;
    }

    public static void setThought(Thought thought) {
        ForwardSelection.thought = thought;
    }

    public static ArrayList<Pane> getChats() {
        return chats;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        VBox vbox = new VBox();

        for (Pane p : chats) {
            vbox.getChildren().add(p);
        }

        chatScroll.setContent(vbox);
    }

    public void back(ActionEvent event) throws IOException {
        StaticController.getMyMainMenu().show();
    }

    public void show() throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getForwardSelection())));
        Scene scene = new Scene(root);
        StaticController.getMyStage().setScene(scene);

    }
}
