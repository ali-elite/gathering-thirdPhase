package ir.sharif.ap2021.server.Controller.Helper;

import ir.sharif.ap2021.server.Controller.ClientHandler;
import ir.sharif.ap2021.server.Hibernate.Connector;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;
import ir.sharif.ap2021.shared.Event.ChatEvent;
import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Message;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Response.ChatResponse;
import ir.sharif.ap2021.shared.Response.Response;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;


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

        User theUser = connector.fetch(User.class, clientHandler.user.getId());

        if (event.getOrder().equals("send")) {

            Chat chat = connector.fetch(Chat.class, event.getChatId());
            Message message = new Message(theUser, false, event.getPm());
            if (event.getImage() != null)
                message.setImage(event.getImage());

            chat.getMessages().add(message);


            connector.save(message);
            connector.save(chat);

            ChatResponse chatResponse = new ChatResponse("send");
            chatResponse.setChat(chat);

            return chatResponse;
        }

        if (event.getOrder().equals("forward")) {

            Chat theChat = connector.fetch(Chat.class, event.getChatId());

            Message theMessage = new Message(theUser,
                    true, event.getSomeText());

            if (event.getImage() != null)
                theMessage.setImage(event.getImage());

            connector.save(theMessage);

            theChat.getMessages().add(theMessage);

            connector.save(theChat);


            ChatResponse chatResponse = new ChatResponse("forward");
            chatResponse.setChat(theChat);

            return chatResponse;

        }

        if (event.getOrder().equals("leaveGroup")) {

            Chat chat = connector.fetch(Chat.class, event.getChatId());

            chat.getUsers().remove(theUser);
            theUser.getChats().remove((Integer) chat.getId());

            connector.save(chat);
            connector.save(theUser);


            return new ChatResponse("leaveGroup");

        }

        if (event.getOrder().equals("sendScheduled")) {

            Chat chat = connector.fetch(Chat.class, event.getChatId());
            Timer timer = new Timer();
            MessageTimer messageTimer = new MessageTimer(event.getLocalDateTime(),timer);
            timer.schedule(messageTimer,1000,2000);

            ChatResponse chatResponse = new ChatResponse("sendScheduled");
            chatResponse.setChat(chat);

            return chatResponse;
        }


        return null;
    }


    private class MessageTimer extends TimerTask {

        private LocalDateTime time;
        private Timer timer;

        public MessageTimer(LocalDateTime time, Timer timer) {
            this.time = time;
            this.timer = timer;
        }

        @Override
        public void run() {
            if (time.isAfter(LocalDateTime.now())) {

                User theUser = null;
                try {
                    theUser = connector.fetch(User.class, clientHandler.user.getId());
                } catch (DatabaseDisconnectException e) {
                    e.printStackTrace();
                }
                Chat chat = null;
                try {
                    chat = connector.fetch(Chat.class, event.getChatId());
                } catch (DatabaseDisconnectException e) {
                    e.printStackTrace();
                }
                Message message = new Message(theUser, false, event.getPm());
                if (event.getImage() != null)
                    message.setImage(event.getImage());

                assert chat != null;
                chat.getMessages().add(message);


                try {
                    connector.save(message);
                } catch (DatabaseDisconnectException e) {
                    e.printStackTrace();
                }
                try {
                    connector.save(chat);
                } catch (DatabaseDisconnectException e) {
                    e.printStackTrace();
                }

                timer.cancel();
            }

        }
    }

}



