package ir.sharif.ap2021.client.View.Menu;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.FxmlConfig;

import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.LoginListener;
import ir.sharif.ap2021.client.Listener.MainMenuListener;
import ir.sharif.ap2021.shared.Event.LoginEvent;
import ir.sharif.ap2021.shared.Event.MainMenuEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Login {

    ErrorConfig errorConfig = new ErrorConfig();
    FxmlConfig fxmlConfig = new FxmlConfig();

    @FXML
    private TextField usernameTF, passwordTF;

    @FXML
    private Label myLabel;

    @FXML
    private Button subBtn, signBtn;

    public Login() throws IOException {
    }


    public void submit(ActionEvent event) throws IOException {

        boolean isReady = true;

        if (usernameTF.getText().equals("")) {
            isReady = false;
            myLabel.setText(errorConfig.getEnterUser());
        }
        if (passwordTF.getText().equals("")) {
            isReady = false;
            myLabel.setText(errorConfig.getEnterPassword());
        }


        if (isReady) {
            LoginEvent loginEvent =
                    new LoginEvent(usernameTF.getText(), passwordTF.getText());

            LoginListener loginListener = new LoginListener(this);
            loginListener.listen(loginEvent);
        }
    }

    public void SignUp(ActionEvent event) throws IOException {

        Stage stage = (Stage) signBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getSignup())));
        Scene scene = new Scene(root);

        stage.setScene(scene);
    }

    public void login(boolean isEntered, String message) throws IOException {

        if (isEntered) {


            StaticController.setMyStage(((Stage) subBtn.getScene().getWindow()));
            Mainmenu mainmenu = new Mainmenu();
            StaticController.setMyMainMenu(mainmenu);
            MainMenuListener mainMenuListener = new MainMenuListener(mainmenu);
            MainMenuEvent mainMenuEvent = new MainMenuEvent("load", 0);
            mainMenuEvent.setUsername(usernameTF.getText());
            mainMenuListener.listen(mainMenuEvent);


        }

        else{
            Platform.runLater(() -> myLabel.setText(message));
        }

    }
}

