package ir.sharif.ap2021.shared.Util;

import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Response.Response;

public class Letter {

    private String token;
    private Event event;
    private Response[] responses;

    public Letter(String token, Response[] responses) {
        this.token = token;
        this.responses = responses;
    }

    public Letter(String token, Event event) {
        this.token = token;
        this.event = event;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Response[] getResponses() {
        return responses;
    }

    public void setResponses(Response[] responses) {
        this.responses = responses;
    }
}
