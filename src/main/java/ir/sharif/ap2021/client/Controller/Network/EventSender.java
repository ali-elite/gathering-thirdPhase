package ir.sharif.ap2021.client.Controller.Network;

import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Response.Response;

public interface EventSender {

    Response[] sendEvent(Event event);
    void close();
}
