package ir.sharif.ap2021.client.Controller;


import ir.sharif.ap2021.client.Listener.MessageListener;
import ir.sharif.ap2021.shared.Model.Message;
import ir.sharif.ap2021.shared.Response.MessageResponse;

import java.io.IOException;


public class MessageController {

    MessageListener messageListener;


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
}
