package ir.sharif.ap2021.server.Controller.Helper;

import ir.sharif.ap2021.server.Controller.ClientHandler;
import ir.sharif.ap2021.server.Hibernate.Connector;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;
import ir.sharif.ap2021.shared.Event.MessageEvent;
import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Message;
import ir.sharif.ap2021.shared.Model.Thought;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Response.MessageResponse;
import ir.sharif.ap2021.shared.Response.Response;


public class MessageController {

    private MessageEvent event;
    private Connector connector;
    private ClientHandler clientHandler;

    public MessageController(MessageEvent event, Connector connector, ClientHandler clientHandler) {
        this.event = event;
        this.connector = connector;
        this.clientHandler = clientHandler;
    }

    public Response answer() throws DatabaseDisconnectException {

        if (event.getOrder().equals("seen")) {

            Message message = connector.fetch(Message.class, event.getMessage().getId());
            while (message == null) {
                message = connector.fetch(Message.class, event.getMessage().getId());
            }
            User myUser = connector.fetch(User.class, clientHandler.user.getId());

            if (!message.getSeenUsers().contains(myUser)) {
                message.getSeenUsers().add(myUser);
            }


            if (message.getSeenUsers().size() > 1) {
                if (!message.isCheck2()) {
                    message.setCheck2(true);
                }
                message.setCheck3(true);
            }
            if (message.getSeenUsers().size() == 1) {
                if (!message.getSeenUsers().get(0).equals(message.getSender())) {
                    if (!message.isCheck2()) {
                        message.setCheck2(true);
                    }
                    message.setCheck3(true);
                }
            }


            connector.save(message);

            MessageResponse messageResponse = new MessageResponse("seen");
            messageResponse.setMyMessage(message);

            return messageResponse;

        }


        if (event.getOrder().equals("edit")) {

            Message message = connector.fetch(Message.class, event.getMessage().getId());

            message.setText(event.getEditedText());

            connector.save(message);

            MessageResponse messageResponse = new MessageResponse("edit");
            messageResponse.setMyMessage(message);

            return messageResponse;

        }


        if (event.getOrder().equals("delete")) {

            Message message = connector.fetch(Message.class, event.getMessage().getId());
            message.setDeleted(true);

            connector.save(message);


            MessageResponse messageResponse = new MessageResponse("delete");
            messageResponse.setMyMessage(message);

            return messageResponse;
        }

        if (event.getOrder().equals("hyper")) {

            User myUser = connector.fetch(User.class, clientHandler.user.getId());
            Message message = connector.fetch(Message.class, event.getMessage().getId());


            if (message.getText().startsWith("@user")) {

                MessageResponse messageResponse = new MessageResponse("hyper");
                messageResponse.setMyMessage(message);
                messageResponse.setHyper("user");
                User hypedUser = connector.fetchUserWithUsername(message.getText().substring(6)).get(0);


                for (Integer i : myUser.getChats()) {
                    Chat c = connector.fetch(Chat.class, i);
                    if (c.getUsers().contains(hypedUser) && !c.isGroup()) {
                        messageResponse.setHypedChat(c);
                    }
                }

                messageResponse.setHypedUser(hypedUser);
                return messageResponse;

            } else if (message.getText().startsWith("@thought")) {

                MessageResponse messageResponse = new MessageResponse("hyper");
                messageResponse.setMyMessage(message);
                messageResponse.setHyper("thought");
                Thought hypedThought = connector.fetch(Thought.class, Integer.parseInt(message.getText().substring(9)));
                messageResponse.setHypedThought(hypedThought);
                return messageResponse;

            } else if (message.getText().startsWith("@gp")) {

                MessageResponse messageResponse = new MessageResponse("hyper");
                messageResponse.setMyMessage(message);
                messageResponse.setHyper("gp");
                Chat hypedChat = connector.fetch(Chat.class, Integer.parseInt(message.getText().substring(4)));

                if (!hypedChat.getUsers().contains(myUser)) {
                    hypedChat.getUsers().add(myUser);
                    connector.save(hypedChat);
                }

                messageResponse.setHypedChat(hypedChat);
                return messageResponse;


            }

//            else if(message.getText().startsWith("@bot")){
//            }


        }


        return null;
    }
}
