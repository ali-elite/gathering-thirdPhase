package ir.sharif.ap2021.client.View.Menu;

import ir.sharif.ap2021.client.Config.FxmlConfig;
import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.NotifListener;
import ir.sharif.ap2021.shared.Event.NotifEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;


public class Notifications implements Initializable {

    FxmlConfig fxmlConfig = new FxmlConfig();

    private final NotifListener notifListener;
    private static final ArrayList<Pane> notifViews = new ArrayList<>();

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ToolBar bar;
    @FXML
    private Button backBtn;

    public Notifications() throws IOException {
        notifListener = new NotifListener();
    }

    public static ArrayList<Pane> getNotifViews() {
        return notifViews;
    }

    public NotifListener getNotifListener() {
        return notifListener;
    }

    public void show() throws IOException {

        Stage stage = (Stage) StaticController.getMyStage().getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getNotifications())));
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }


    public void back(ActionEvent event) throws IOException {
        StaticController.getMyMainMenu().show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        VBox vBox = new VBox();
        vBox.getChildren().add(bar);

        for (Pane p : notifViews) {
            vBox.getChildren().add(p);
        }

        scrollPane.setContent(vBox);

    }

    public void load(){
        notifListener.listen(new NotifEvent("load"));
    }

}
