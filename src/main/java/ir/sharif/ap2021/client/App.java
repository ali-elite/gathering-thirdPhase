package ir.sharif.ap2021.client;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.FxmlConfig;
import ir.sharif.ap2021.client.Config.ImageConfig;
import ir.sharif.ap2021.client.Controller.Network.ClientController;
import ir.sharif.ap2021.client.Controller.Network.SocketEventSender;
import ir.sharif.ap2021.client.Controller.StaticController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

public class App extends Application {


    private static final Logger logger = LogManager.getLogger(App.class);
    private FxmlConfig fxmlConfig = new FxmlConfig();
    private ImageConfig imageConfig = new ImageConfig();
    private ErrorConfig errorConfig = new ErrorConfig();

    @FXML
    private Button signBtn, loginBtn, offlineBtn;

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
        primaryStage.show();

        logger.info("App Started");
    }


    public void handleButtonAction(ActionEvent event) throws IOException {

        Stage stage;
        Parent root;

        if (event.getSource() == signBtn) {

            Socket socket;
            try {
                socket = new Socket("localhost", 8000);
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

        } else if (event.getSource() == loginBtn) {

            Socket socket;
            try {
                socket = new Socket("localhost", 8000);
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
        } else {
            stage = (Stage) loginBtn.getScene().getWindow();
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getLogin())));
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

}
