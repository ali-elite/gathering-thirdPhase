package ir.sharif.ap2021.client.View.Menu;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.FxmlConfig;
import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.MainMenuListener;
import ir.sharif.ap2021.client.Listener.SignupListener;
import ir.sharif.ap2021.shared.Event.MainMenuEvent;
import ir.sharif.ap2021.shared.Event.SignupEvent;
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

public class Signup {

    FxmlConfig fxmlConfig = new FxmlConfig();
    ErrorConfig errorConfig = new ErrorConfig();


    private SignupListener signupListener;
    @FXML
    private TextField firstNameTF;
    @FXML
    private TextField lastNameTf;
    @FXML
    private TextField userNameTF;
    @FXML
    private TextField passwordTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField repeatTF;
    @FXML
    private Label myLabel;
    @FXML
    private Button subBtn, loginBtn;

    public Signup() throws IOException {
    }


    public void submit(ActionEvent event) throws IOException {

        boolean isReady = true;

        if (firstNameTF.getText().equals("")) {
            isReady = false;
            myLabel.setText(errorConfig.getEnterFirst());
        }
        if (lastNameTf.getText().equals("")) {
            isReady = false;
            myLabel.setText(errorConfig.getEnterLast());
        }
        if (userNameTF.getText().equals("")) {
            isReady = false;
            myLabel.setText(errorConfig.getEnterUser());
        }
        if (passwordTF.getText().equals("")) {
            isReady = false;
            myLabel.setText(errorConfig.getEnterPassword());
        }
        if (emailTF.getText().equals("")) {
            isReady = false;
            myLabel.setText(errorConfig.getEnterEmail());
        }
        if (repeatTF.getText().equals("")) {
            isReady = false;
            myLabel.setText(errorConfig.getEnterRepeat());
        }
        if (!repeatTF.getText().equals(passwordTF.getText())) {
            isReady = false;
            myLabel.setText(errorConfig.getMatchPassword());
        }
        if (!(emailTF.getText().length() > 6) || !emailTF.getText().contains("@") || !emailTF.getText().endsWith(".com")) {
            isReady = false;
            myLabel.setText(errorConfig.getValidEmail());
        }


        if (isReady) {
            SignupEvent signupEvent =
                    new SignupEvent(firstNameTF.getText(),
                            lastNameTf.getText(),
                            userNameTF.getText(),
                            emailTF.getText(),
                            passwordTF.getText());
            signupListener = new SignupListener(this);
            signupListener.listen(signupEvent);
        }


    }


    public void login(ActionEvent event) throws IOException {

        Stage stage = (Stage) loginBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getLogin())));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void signup(boolean isEntered, String message) throws IOException {
        if (isEntered) {

            StaticController.setMyStage(((Stage) subBtn.getScene().getWindow()));
            Mainmenu mainmenu = new Mainmenu();
            StaticController.setMyMainMenu(mainmenu);
            MainMenuListener mainMenuListener = new MainMenuListener(mainmenu);
            MainMenuEvent mainMenuEvent = new MainMenuEvent("load", 0);
            mainMenuEvent.setUsername(userNameTF.getText());
            mainMenuListener.listen(mainMenuEvent);

        } else {
            Platform.runLater(() -> myLabel.setText(message));
        }
    }
}



