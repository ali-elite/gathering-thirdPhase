package ir.sharif.ap2021.server.Controller.Helper;

import ir.sharif.ap2021.server.Controller.ClientHandler;
import ir.sharif.ap2021.server.Hibernate.Connector;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;
import ir.sharif.ap2021.shared.Event.MessageEvent;
import ir.sharif.ap2021.shared.Model.Message;
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

            Message message = event.getMessage();
            User myUser = connector.fetch(User.class,clientHandler.user.getId());

            if (!message.getSeenUsers().contains(myUser)) {
                message.getSeenUsers().add(myUser);
            }

            connector.save(message);

            MessageResponse messageResponse = new MessageResponse("seen");
            messageResponse.setMyMessage(message);

            return messageResponse;

        }


        if (event.getOrder().equals("edit")) {

            Message message = event.getMessage();

            message.setText(event.getEditedText());

            connector.save(message);

            MessageResponse messageResponse = new MessageResponse("edit");
            messageResponse.setMyMessage(message);

            return messageResponse;

        }


        if (event.getOrder().equals("delete")) {

            Message message = event.getMessage();

            message.setDeleted(true);

            connector.save(message);


            MessageResponse messageResponse = new MessageResponse("delete");
            messageResponse.setMyMessage(message);

            return messageResponse;
        }


        return null;
    }
}
