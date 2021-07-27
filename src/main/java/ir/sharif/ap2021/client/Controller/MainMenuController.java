package ir.sharif.ap2021.client.Controller;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.FxmlConfig;
import ir.sharif.ap2021.client.View.Menu.*;
import ir.sharif.ap2021.client.View.ModelView.ChatForwardView;
import ir.sharif.ap2021.client.View.ModelView.ChatView;
import ir.sharif.ap2021.client.View.ModelView.ThoughtView;
import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Message;
import ir.sharif.ap2021.shared.Model.Thought;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Response.MainMenuResponse;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainMenuController {

    FxmlConfig fxmlConfig = new FxmlConfig();
    ErrorConfig errorConfig = new ErrorConfig();


    public MainMenuController() throws IOException {
    }


    public void load(Mainmenu mainmenu, MainMenuResponse response) throws IOException {

        User user = response.getUser();
        StaticController.setMyUser(user);
        mainmenu.show();

    }

    public void loadUser(Mainmenu mainmenu, MainMenuResponse response) throws IOException {
        User user = response.getUser();
        StaticController.setMyUser(user);
    }


    public void gatherThought(Mainmenu mainmenu, MainMenuResponse response) throws IOException {

        Mainmenu.getGatherThoughts().clear();

        for (int i = response.getThoughts().size() - 1; i > -1; i--) {

            ThoughtView thoughtView = new ThoughtView();
            Thought thought = response.getThoughts().get(i);

            thoughtView.setThought(thought);
            thoughtView.setOwnerUser(thought.getUser());
            thoughtView.setMainUser(StaticController.getMyUser());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlConfig.getThought()));
            loader.setController(thoughtView);
            Mainmenu.getGatherThoughts().add((Pane) loader.load());
        }

    }

    public void timeLineThought(Mainmenu mainmenu, MainMenuResponse response) throws IOException {

        Mainmenu.getTimeThoughts().clear();
        for (int i = response.getThoughts().size() - 1; i > -1; i--) {

            ThoughtView thoughtView = new ThoughtView();
            Thought thought = response.getThoughts().get(i);

            thoughtView.setThought(thought);
            thoughtView.setOwnerUser(thought.getUser());
            thoughtView.setMainUser(StaticController.getMyUser());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlConfig.getThought()));
            loader.setController(thoughtView);
            Mainmenu.getTimeThoughts().add((Pane) loader.load());
        }


    }

    public void exploreThought(Mainmenu mainmenu, MainMenuResponse response) throws IOException {

        Mainmenu.getExploreThoughts().clear();
        for (int i = response.getThoughts().size() - 1; i > -1; i--) {

            ThoughtView thoughtView = new ThoughtView();
            Thought thought = response.getThoughts().get(i);

            thoughtView.setThought(thought);
            thoughtView.setOwnerUser(thought.getUser());
            thoughtView.setMainUser(StaticController.getMyUser());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlConfig.getThought()));
            loader.setController(thoughtView);
            Mainmenu.getExploreThoughts().add((Pane) loader.load());
        }


    }


    public void chats(Mainmenu mainmenu, MainMenuResponse response) throws IOException {

        Mainmenu.getChats().clear();
        for (int i = response.getChats().size() - 1; i > -1; i--) {

            Chat chat = response.getChats().get(i);
            int seenCount = chat.getMessages().size();

            for (Message message : chat.getMessages()) {

                if (message.getSeenUsers().contains(StaticController.getMyUser())) {
                    seenCount--;
                }

            }

            if (seenCount != 0) {
                ChatView chatView = new ChatView();
                chatView.setChat(chat);
                chatView.setUnseen(seenCount);

                if (!chat.isGroup()) {
                    if (chat.getName().equals(errorConfig.getSavedMessages()))
                        chatView.setUser(StaticController.getMyUser());
                    else {
                        for(User us : chat.getUsers()){
                            if(!us.equals(StaticController.getMyUser()))
                                chatView.setUser(us);
                            break;
                        }

                    }
                }


                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlConfig.getChat()));
                loader.setController(chatView);


                Mainmenu.getChats().add((Pane) loader.load());
            }
        }

        //___________________________


        for (int i = response.getChats().size() - 1; i > -1; i--) {

            Chat chat = response.getChats().get(i);
            int seenCount = chat.getMessages().size();

            for (Message message : chat.getMessages()) {

                if (message.getSeenUsers().contains(StaticController.getMyUser())) {
                    seenCount--;
                }

            }

            if (seenCount == 0) {
                ChatView chatView = new ChatView();
                chatView.setChat(chat);
                chatView.setUnseen(seenCount);
                if (!chat.isGroup()) {
                    if (chat.getName().equals(errorConfig.getSavedMessages()))
                        chatView.setUser(StaticController.getMyUser());
                    else {
                        for(User us : chat.getUsers()){
                            if(!us.equals(StaticController.getMyUser()))
                                chatView.setUser(us);
                            break;
                        }

                    }
                }


                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlConfig.getChat()));
                loader.setController(chatView);


                Mainmenu.getChats().add((Pane) loader.load());
            }
        }


    }

    public void group(Mainmenu mainmenu, MainMenuResponse response) throws IOException {

        ArrayList<String> userItems = new ArrayList<>(response.getUsernames());
        NewGroup.setUsers(userItems);
        NewGroup newGroup = new NewGroup();
        System.out.println("come");
        newGroup.show();

    }

    public void forwards(Mainmenu mainmenu, MainMenuResponse response) throws IOException {

        ForwardSelection.getChats().clear();
        for (int i = response.getChats().size() - 1; i > -1; i--) {

            Chat chat = response.getChats().get(i);
            int seenCount = chat.getMessages().size();

            for (Message message : chat.getMessages()) {

                if (message.getSeenUsers().contains(StaticController.getMyUser())) {
                    seenCount--;
                }

            }

            if (seenCount != 0) {
                ChatForwardView chatForwardView = new ChatForwardView();
                chatForwardView.setChat(chat);
                chatForwardView.setUnseen(seenCount);
                if (!chat.isGroup()) {
                    if (chat.getName().equals(errorConfig.getSavedMessages()))
                        chatForwardView.setUser(StaticController.getMyUser());
                    else {
                        for(User us : chat.getUsers()){
                            if(!us.equals(StaticController.getMyUser()))
                                chatForwardView.setUser(us);
                            break;
                        }

                    }
                }


                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlConfig.getChatForward()));
                loader.setController(chatForwardView);


                ForwardSelection.getChats().add((Pane) loader.load());
            }
        }

        //___________________________


        for (int i = response.getChats().size() - 1; i > -1; i--) {

            Chat chat = response.getChats().get(i);
            int seenCount = chat.getMessages().size();

            for (Message message : chat.getMessages()) {

                if (message.getSeenUsers().contains(StaticController.getMyUser())) {
                    seenCount--;
                }

            }

            if (seenCount == 0) {
                ChatForwardView chatForwardView = new ChatForwardView();
                chatForwardView.setChat(chat);
                chatForwardView.setUnseen(seenCount);
                if (!chat.isGroup()) {
                    if (chat.getName().equals(errorConfig.getSavedMessages()))
                        chatForwardView.setUser(StaticController.getMyUser());
                    else {
                        for(User us : chat.getUsers()){
                            if(!us.equals(StaticController.getMyUser()))
                                chatForwardView.setUser(us);
                            break;
                        }

                    }
                }


                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlConfig.getChatForward()));
                loader.setController(chatForwardView);


                ForwardSelection.getChats().add((Pane) loader.load());
            }
        }


    }

    public void blacklist(MainMenuResponse response) throws IOException {

        Blacklist.getUsers().clear();

        for (String u : response.getUsernames()) {
            Blacklist.getUsers().add(u);
        }

        Stage stage = StaticController.getMyStage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getBlacklist())));
        Scene scene = new Scene(root);

        Platform.runLater(() -> stage.setScene(scene));

    }

    public void groupMessage(MainMenuResponse response) throws IOException {

        ArrayList<String> userItems = new ArrayList<>(response.getUsernames());
        GroupMessage.setUsers(userItems);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getGroupMessage())));
        Scene scene = new Scene(root);
        Platform.runLater(() -> StaticController.getMyStage().setScene(scene));

    }

    public void logOut(Mainmenu mainmenu) throws IOException {

        Stage stage = StaticController.getMyStage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getApp())));
        Scene scene = new Scene(root);
        Platform.runLater(() -> stage.setScene(scene));

    }


    public void changePassword(MainMenuResponse response) {

        StaticController.setMyUser(response.getUser());

        Platform.runLater(() -> {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getPasswordChanged());
            alert.showAndWait();
        });

    }

    public void lastSeen(MainMenuResponse response) {

        StaticController.setMyUser(response.getUser());

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getLastSeenChanged());
            alert.showAndWait();

        });
    }

    public void changeActivity(MainMenuResponse response) {

        StaticController.setMyUser(response.getUser());

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getActivityChanged());
            alert.showAndWait();

        });
    }

    public void changePrivacy(MainMenuResponse response) {

        StaticController.setMyUser(response.getUser());
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getPrivacyChanged());
            alert.showAndWait();

        });

    }

    public void deleteUser(Mainmenu mainmenu) throws IOException {

        logOut(null);
    }

    public void unblock(MainMenuResponse response) {

        StaticController.setMyUser(response.getUser());

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("User unblocked");
            alert.showAndWait();

        });

    }

}
