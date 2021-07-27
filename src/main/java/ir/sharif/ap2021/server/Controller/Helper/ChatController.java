package ir.sharif.ap2021.server.Controller.Helper;

import ir.sharif.ap2021.server.Controller.ClientHandler;
import ir.sharif.ap2021.server.Hibernate.Connector;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;
import ir.sharif.ap2021.shared.Event.ChatEvent;
import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Message;
import ir.sharif.ap2021.shared.Response.ChatResponse;
import ir.sharif.ap2021.shared.Response.Response;


public class ChatController {

    private ChatEvent event;
    private Connector connector;
    private ClientHandler clientHandler;

    public ChatController(ChatEvent event, Connector connector, ClientHandler clientHandler) {
        this.event = event;
        this.connector = connector;
        this.clientHandler = clientHandler;
    }


    public Response answer() throws DatabaseDisconnectException {


        if (event.getOrder().equals("send")) {

            Chat chat = connector.fetch(Chat.class, event.getChatId());
            Message message = new Message(clientHandler.user, false, event.getPm());

//            if (formEvent.getChanged().equals("changed")) {
//                message.setImage("/MessageImages/" + message.getId() + ".png");
//                File old = new File(fxmlConfig.getMainConfig().getResourcesPath()+"/MessageImages/" + "311" + ".png");
//                File notOld = new File(fxmlConfig.getMainConfig().getResourcesPath()+"/MessageImages/" + message.getId() + ".png");
//
//                old.renameTo(notOld);
//            }
            chat.getMessages().add(message);


            connector.save(message);
            connector.save(chat);

            ChatResponse chatResponse = new ChatResponse("send");
            chatResponse.setChat(chat);

            return chatResponse;
        }

        if (event.getOrder().equals("forward")) {

            Chat theChat = connector.fetch(Chat.class, event.getChatId());

            Message theMessage = new Message(clientHandler.user,
                    true, event.getSomeText());
            connector.save(theMessage);

//            if (ChatForwardView.getThought().getImage() != null) {
//                theMessage.setImage(ChatForwardView.getThought().getImage());
//            }

            theChat.getMessages().add(theMessage);

            connector.save(theChat);


            ChatResponse chatResponse = new ChatResponse("forward");
            chatResponse.setChat(theChat);

            return chatResponse;

        }


        return null;
    }
}
