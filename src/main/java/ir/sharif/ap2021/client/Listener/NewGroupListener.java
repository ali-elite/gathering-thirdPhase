package ir.sharif.ap2021.client.Listener;


import ir.sharif.ap2021.client.Controller.NewGroupController;
import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Response.NewGroupResponse;

import java.io.IOException;

public class NewGroupListener implements EventListener {

    NewGroupController newGroupController = new NewGroupController();

    public NewGroupListener() throws IOException {
    }

    public void handle(NewGroupResponse response) throws IOException {
        newGroupController.control(response);
    }

    @Override
    public void listen(Event event) {
        myClientController.addEvent(event);
        myClientController.addEventListener(this);
    }
}
