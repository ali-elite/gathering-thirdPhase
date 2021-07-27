package ir.sharif.ap2021.client.Controller;

import ir.sharif.ap2021.client.Config.FxmlConfig;
import ir.sharif.ap2021.client.Listener.ChatListener;
import ir.sharif.ap2021.client.View.Menu.ChatMenu;
import ir.sharif.ap2021.client.View.ModelView.MessageView;
import ir.sharif.ap2021.shared.Event.ChatEvent;
import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Message;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Response.ChatResponse;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ChatController {

    FxmlConfig fxmlConfig = new FxmlConfig();
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
                messageView.setMessage(message);
                messageView.setSender(sender);

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlConfig.getMessage()));
                loader.setController(messageView);
                ChatMenu.getMessages().add((Pane) loader.load());
                i++;
            }

            if (i == 5) {
                break;
            }
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


    }

}
