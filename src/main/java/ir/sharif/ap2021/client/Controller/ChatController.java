package ir.sharif.ap2021.client.Controller;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.FxmlConfig;
import ir.sharif.ap2021.client.Listener.ChatListener;
import ir.sharif.ap2021.client.View.Menu.ChatMenu;
import ir.sharif.ap2021.client.View.ModelView.MessageView;
import ir.sharif.ap2021.shared.Event.ChatEvent;
import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Message;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Response.ChatResponse;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ChatController {

    FxmlConfig fxmlConfig = new FxmlConfig();
    ErrorConfig errorConfig = new ErrorConfig();
    ChatListener chatListener;

    public ChatController(ChatListener chatListener) throws IOException {
        this.chatListener = chatListener;
    }

    public void loadChats(ChatEvent formEvent) throws IOException {

        Chat chat = formEvent.getTheChat();
        ChatMenu.getMessages().clear();
        int i = 0;
        for (Message message : chat.getMessages()) {

            User sender = message.getSender();

            if (sender.isActive() && !sender.isDeleted()) {

                MessageView messageView = new MessageView();
                messageView.setChatListener(chatListener);
                messageView.setMessage(message);
                messageView.setSender(sender);

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlConfig.getMessage()));
                loader.setController(messageView);
                ChatMenu.getMessages().add((Pane) loader.load());
                i++;
            }

//            if (i == 5) {
//                break;
//            }

        }

    }


    public void control(ChatResponse response) throws IOException {

        if (response.getOrder().equals("send")) {

            ChatMenu.setChat(response.getChat());
            chatListener.getChatMenu().afterSend();

        }


        if (response.getOrder().equals("forward")) {
            StaticController.getMyMainMenu().show();
        }

        if (response.getOrder().equals("leaveGroup")) {

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(errorConfig.getLefTheGroup());
                alert.showAndWait();
            });
            StaticController.getMyMainMenu().show();

        }

        if (response.getOrder().equals("sendScheduled")) {

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(errorConfig.getScheduled());
                alert.showAndWait();
            });
            chatListener.getChatMenu().afterSend();

        }


    }

}
