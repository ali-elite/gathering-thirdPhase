package ir.sharif.ap2021.client.View.Menu;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.FxmlConfig;
import ir.sharif.ap2021.client.Config.ItemConfig;

import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.MainMenuListener;
import ir.sharif.ap2021.client.Listener.NotifListener;
import ir.sharif.ap2021.client.Listener.UserSelectionListener;
import ir.sharif.ap2021.client.View.ModelView.Profile;
import ir.sharif.ap2021.shared.Event.MainMenuEvent;
import ir.sharif.ap2021.shared.Event.UserSelectionEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


public class Mainmenu {

    ItemConfig itemConfig = new ItemConfig();
    ErrorConfig errorConfig = new ErrorConfig();
    FxmlConfig fxmlConfig = new FxmlConfig();


    @FXML
    private TabPane mainTabPane;
    @FXML
    private Tab gatherTab, timelineTab, exploreTab, chatsTab, setingTab;
    @FXML
    private Button editBtn, blackListBtn, newThoughtBtn, notifBtn;
    @FXML
    private ScrollPane gatherScroll, timeLineScroll, exploreScroll, chatScroll;
    @FXML
    private ToolBar bar, categoryBar;
    @FXML
    private AnchorPane searchPane;
    @FXML
    private TextField searchTextField, password, repeat;
    @FXML
    private ChoiceBox<String> privacy;
    @FXML
    private CheckBox diactive, privateCheck;

    private static final ArrayList<Pane> gatherThoughts = new ArrayList<>();
    private static final ArrayList<Pane> timeThoughts = new ArrayList<>();
    private static final ArrayList<Pane> exploreThoughts = new ArrayList<>();
    private static final ArrayList<Pane> chats = new ArrayList<>();



    private final MainMenuListener mainMenuListener = new MainMenuListener(this);
    private final UserSelectionListener userSelectionListener = new UserSelectionListener();

    private Notifications notifications = new Notifications();


    public Mainmenu() throws IOException {

    }


    public ScrollPane getGatherScroll() {
        return gatherScroll;
    }

    public static ArrayList<Pane> getGatherThoughts() {
        return gatherThoughts;
    }

    public static ArrayList<Pane> getTimeThoughts() {
        return timeThoughts;
    }

    public static ArrayList<Pane> getExploreThoughts() {
        return exploreThoughts;
    }

    public static ArrayList<Pane> getChats() {
        return chats;
    }

    public Pane shapeProfile() throws IOException {

        Profile profile = new Profile();
        profile.setUser(StaticController.getMyUser());

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlConfig.getProfile()));
        loader.setController(profile);

        return loader.load();
    }

    public void show() throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getMainmenu())));
        Scene scene = new Scene(root);
        Platform.runLater(() -> StaticController.getMyStage().setScene(scene));

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                reload();
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask,1000,5000);

    }


    public void timeLineUpdate(Event event) throws InterruptedException {

            VBox vbox = new VBox();

            for (Pane p : timeThoughts) {
                vbox.getChildren().add(p);
            }

            timeLineScroll.setContent(vbox);

    }

    public void exploreUpdate(Event event) {

            VBox vbox = new VBox();

            for (Pane p : exploreThoughts) {
                vbox.getChildren().add(p);
            }

            exploreScroll.setContent(vbox);

    }

    public void gatherUpdate(Event event) throws InterruptedException {

        VBox gatherVBox = new VBox();

        try {
            gatherVBox.getChildren().add(shapeProfile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Pane p : gatherThoughts) {
            gatherVBox.getChildren().add(p);
        }

        gatherScroll.setContent(gatherVBox);
    }

    public void chatUpdate(Event event) throws InterruptedException {

            VBox vbox = new VBox();

            for (Pane p : chats) {
                vbox.getChildren().add(p);
            }

            chatScroll.setContent(vbox);
    }

    public void settingUpdate(Event event) {

        String[] items = {itemConfig.getAnyOne(), itemConfig.getNoOne(), itemConfig.getJustFollowers()};
        privacy.getItems().addAll(items);

        diactive.setSelected(!StaticController.getMyUser().isActive());
    }




    public void makeThought(ActionEvent event) throws IOException {

        Stage stage = (Stage) newThoughtBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getNewThought())));
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    public void editProfile(ActionEvent event) throws IOException {

        Stage stage = (Stage) editBtn.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getEditProfile())));
        Scene scene = new Scene(root);
        stage.setScene(scene);

    }

    public void showNotif(ActionEvent event) throws IOException {
        notifications.show();
    }

    public void showBlackList(ActionEvent event) throws IOException {

        if (StaticController.getMyUser().getBlackList().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getEmptyBlacklist());
            alert.showAndWait();

        } else {
            mainMenuListener.listen(new MainMenuEvent("blacklist", StaticController.getMyUser().getId()));
        }

    }


    public void doSearch(ActionEvent event) {

        if (searchTextField.getText().equals(StaticController.getMyUser().getUserName())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getSearchYourself());
            alert.showAndWait();
        } else {
            userSelectionListener.setMainMenu(this);
            userSelectionListener.listen(new UserSelectionEvent("load", searchTextField.getText(), null));
        }

    }

    public void userSelectionNotExist() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getNoUserExists());
            alert.showAndWait();

        });
    }

    public void makeGroup(ActionEvent event) throws IOException {

        if (StaticController.getMyUser().getFollowers().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(errorConfig.getEmptyFollower());
            alert.showAndWait();
        } else {
            mainMenuListener.listen(new MainMenuEvent("group", StaticController.getMyUser().getId()));
        }

    }

    public void groupMessage(ActionEvent event) throws IOException {

        if (StaticController.getMyUser().getFollowers().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(errorConfig.getEmptyFollower());
            alert.showAndWait();
        } else {
            mainMenuListener.listen(new MainMenuEvent("groupMessage", StaticController.getMyUser().getId()));
        }

    }


    public void changePassword(ActionEvent event) throws IOException {

        if (!password.getText().equals(repeat.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorConfig.getMatchPassword());
            alert.showAndWait();
        } else if (password.getText().equals("") | repeat.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorConfig.getEnterBoth());
            alert.showAndWait();
        } else {
            MainMenuEvent mainMenuEvent = new MainMenuEvent("changePassword", StaticController.getMyUser().getId());
            mainMenuEvent.setPassword(password.getText());
            mainMenuEvent.setDiactive(false);
            mainMenuListener.listen(mainMenuEvent);

        }

    }

    public void confirmLsPrivacy(ActionEvent event) throws IOException {

        MainMenuEvent mainMenuEvent = new MainMenuEvent("lastSeen", StaticController.getMyUser().getId());
        mainMenuEvent.setLastSeen(privacy.getValue());
        mainMenuEvent.setDiactive(false);
        mainMenuListener.listen(mainMenuEvent);


    }

    public void confirmActivity(ActionEvent event) throws IOException {

        MainMenuEvent mainMenuEvent = new MainMenuEvent("changeActivity", StaticController.getMyUser().getId());
        mainMenuEvent.setDiactive(diactive.isSelected());
        mainMenuListener.listen(mainMenuEvent);

    }

    public void confirmPrivacy(ActionEvent event) throws IOException {

        MainMenuEvent mainMenuEvent = new MainMenuEvent("changePrivacy", StaticController.getMyUser().getId());
        mainMenuEvent.setDiactive(privateCheck.isSelected());
        mainMenuListener.listen(mainMenuEvent);

    }

    public void deleteUser(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(errorConfig.getSureDelete());

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                mainMenuListener.listen(new MainMenuEvent("delete", StaticController.getMyUser().getId()));
            }
        });


    }

    public void logOut(ActionEvent event) throws IOException {
        mainMenuListener.listen(new MainMenuEvent("logOut", StaticController.getMyUser().getId()));
    }

    public void reload(){

        mainMenuListener.listen(new MainMenuEvent("loadUser",StaticController.getMyUser().getId()));
        mainMenuListener.listen(new MainMenuEvent("gatherThought", StaticController.getMyUser().getId()));
        mainMenuListener.listen(new MainMenuEvent("timeLineThought", StaticController.getMyUser().getId()));
        mainMenuListener.listen(new MainMenuEvent("exploreThought", StaticController.getMyUser().getId()));
        mainMenuListener.listen(new MainMenuEvent("chats", StaticController.getMyUser().getId()));
        mainMenuListener.listen(new MainMenuEvent("forwards", StaticController.getMyUser().getId()));
        notifications.load();

    }

}