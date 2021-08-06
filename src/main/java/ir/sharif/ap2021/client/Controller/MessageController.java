package ir.sharif.ap2021.client.Controller;


import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Listener.MessageListener;
import ir.sharif.ap2021.client.View.Menu.ChatMenu;
import ir.sharif.ap2021.client.View.ModelView.OutThought;
import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Message;
import ir.sharif.ap2021.shared.Response.MessageResponse;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;


public class MessageController {

    MessageListener messageListener;
    ErrorConfig errorConfig = new ErrorConfig();


    public MessageController(MessageListener messageListener) throws IOException {
        this.messageListener = messageListener;
    }

    public void seen(MessageResponse response) {

        messageListener.getMessageView().setMessage(response.getMyMessage());

    }


    public void edit(MessageResponse response) {

        messageListener.getMessageView().setMessage(response.getMyMessage());
        messageListener.getMessageView().initialize(null, null);

    }

    public void delete(MessageResponse response) {

        messageListener.getMessageView().setMessage(response.getMyMessage());
        messageListener.getMessageView().initialize(null, null);

    }


    public void hyper(MessageResponse messageResponse) throws IOException {


        if (messageResponse.getHyper().equals("user")) {

            if (messageResponse.getHypedChat() == null) {

                Platform.runLater(() -> {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(errorConfig.getNoHyperlink());
                    alert.showAndWait();

                });

            } else {


                ChatMenu.setChat(messageResponse.getHypedChat());
                Platform.runLater(() -> messageListener.getMessageView().getChatListener().getChatMenu().initialize(null, null));
            }

        } else if (messageResponse.getHyper().equals("thought")) {

            if (messageResponse.getHypedThought().getUser().isPrivate()
                    && !messageResponse.getHypedThought().getUser().getFollowers().contains(StaticController.getMyUser().getId())) {
                Platform.runLater(() -> {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(errorConfig.getNoHyperlink());
                    alert.showAndWait();

                });
            } else {

                OutThought outThought = new OutThought();
                OutThought.setThought(messageResponse.getHypedThought());
                OutThought.setOwnerUser(messageResponse.getHypedThought().getUser());
                OutThought.setMainUser(StaticController.getMyUser());
                outThought.show();

            }


        } else if (messageResponse.getHyper().equals("gp")) {


            if (messageResponse.getHypedChat() == null) {

                Platform.runLater(() -> {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(errorConfig.getNoHyperlink());
                    alert.showAndWait();

                });

            } else {

                ChatMenu.setChat(messageResponse.getHypedChat());
                ChatMenu chatMenu = new ChatMenu();
                Platform.runLater(() -> {

                    try {
                        chatMenu.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(errorConfig.getJoinGroup());
                    alert.showAndWait();

                });
            }


        }


//        else if(messageResponse.getHyper().equals("bot")){}

    }
}
