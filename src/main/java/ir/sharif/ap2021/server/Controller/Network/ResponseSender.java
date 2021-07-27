package ir.sharif.ap2021.server.Controller.Network;

import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Response.Response;


public interface ResponseSender {
    Event getEvent() throws CliectDisconnectException;

    void sendResponse(Response... responses) throws CliectDisconnectException;

    void close();
}
