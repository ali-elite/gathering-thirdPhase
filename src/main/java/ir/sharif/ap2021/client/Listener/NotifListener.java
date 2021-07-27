package ir.sharif.ap2021.client.Listener;


import ir.sharif.ap2021.client.Controller.NotifController;
import ir.sharif.ap2021.client.View.ModelView.NotifView;
import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Model.Notification;
import ir.sharif.ap2021.shared.Response.NotifResponse;

import java.io.IOException;

public class NotifListener implements EventListener {

     private final NotifController notifController = new NotifController(this);
     private NotifView notifView;

    public NotifListener() throws IOException {
    }

    @Override
    public void listen(Event event) {

        myClientController.addEvent(event);
        myClientController.addEventListener(this);

    }

    public void handle(NotifResponse response) throws IOException {

        if (response.getOrder().equals("load")) {
            notifController.load(response);
        }

        if(response.getOrder().equals("accept")){
            notifController.accept(response);
        }

        if(response.getOrder().equals("reject")){
            notifController.reject(response);
        }

        if(response.getOrder().equals("remove")){
            notifController.remove(response);
        }

    }

    public void setNotifView(NotifView notification) {
        this.notifView = notification;
    }

    public NotifView getNotifView() {
        return notifView;
    }
}

