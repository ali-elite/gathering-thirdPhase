package ir.sharif.ap2021.client.Listener;


import ir.sharif.ap2021.client.Controller.Network.ClientController;
import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.shared.Event.Event;

public interface EventListener {

    public ClientController myClientController = StaticController.getClientController();
    void listen(Event event);

}
