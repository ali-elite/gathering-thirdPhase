package ir.sharif.ap2021.client.Listener;

import ir.sharif.ap2021.client.Controller.MessageController;
import ir.sharif.ap2021.client.View.ModelView.MessageView;
import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Response.MessageResponse;

import java.io.IOException;

public class MessageListener implements EventListener {

    MessageController messageController = new MessageController(this);
    private MessageView messageView;

    public MessageListener(MessageView messageView) throws IOException {
        this.messageView = messageView;
    }


    public void handle(MessageResponse messageResponse) {

        if (messageResponse.getOrder().equals("seen")) {
            messageController.seen(messageResponse);
        }

        if (messageResponse.getOrder().equals("edit")) {
            messageController.edit(messageResponse);
        }

        if (messageResponse.getOrder().equals("delete")) {
            messageController.delete(messageResponse);
        }

    }


    @Override
    public void listen(Event event) {
        myClientController.addEvent(event);
        myClientController.addEventListener(this);
    }

    public MessageView getMessageView() {
        return messageView;
    }

    public void setMessageView(MessageView messageView) {
        this.messageView = messageView;
    }
}
