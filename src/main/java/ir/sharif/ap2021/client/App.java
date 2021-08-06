package ir.sharif.ap2021.client;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.FxmlConfig;
import ir.sharif.ap2021.client.Config.ImageConfig;
import ir.sharif.ap2021.client.Config.NetworkConfig;
import ir.sharif.ap2021.client.Controller.Network.ClientController;
import ir.sharif.ap2021.client.Controller.Network.SocketEventSender;
import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.shared.Event.ExitEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class App extends Application {

    private FxmlConfig fxmlConfig = new FxmlConfig();
    private ImageConfig imageConfig = new ImageConfig();
    private ErrorConfig errorConfig = new ErrorConfig();
    private NetworkConfig networkConfig = new NetworkConfig();

    @FXML
    private Button signBtn, loginBtn;

    public App() throws IOException {
    }

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getApp())));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(imageConfig.getLogo()));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                if (StaticController.getMyUser() != null) {
                    StaticController.getClientController().addEvent(new ExitEvent());
                } else {
                    Platform.exit();
                    System.exit(0);
                }
            }
        });
        primaryStage.show();
    }


    public void handleButtonAction(ActionEvent event) throws IOException {

        Stage stage;
        Parent root;

        if (event.getSource() == signBtn) {

            Socket socket;
            try {
                socket = new Socket(networkConfig.getHost(), Integer.parseInt(networkConfig.getPort()));
            } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(errorConfig.getNoServer());
                alert.showAndWait();
                return;

            }

            ClientController clientController = new ClientController(new SocketEventSender(socket));
            StaticController.setClientController(clientController);
            clientController.start();

            stage = (Stage) signBtn.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getSignup())));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } else if (event.getSource() == loginBtn) {

            Socket socket;
            try {
                socket = new Socket(networkConfig.getHost(), Integer.parseInt(networkConfig.getPort()));
            } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(errorConfig.getNoServer());
                alert.showAndWait();
                return;
            }

            ClientController clientController = new ClientController(new SocketEventSender(socket));
            StaticController.setClientController(clientController);
            clientController.start();

            stage = (Stage) loginBtn.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getLogin())));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }


    }

}
