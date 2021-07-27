package ir.sharif.ap2021.client.Listener;


import ir.sharif.ap2021.client.Controller.ThoughtController;
import ir.sharif.ap2021.client.View.ModelView.ThoughtView;
import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Response.ThoughtResponse;

import java.io.IOException;


public class ThoughtListener implements EventListener {


    private ThoughtView thoughtView;
    ThoughtController thoughtController = new ThoughtController();


    public ThoughtListener() throws IOException {
    }



    @Override
    public void listen(Event event) {
        myClientController.addEvent(event);
        myClientController.addEventListener(this);
    }

    public void handle(ThoughtResponse thoughtResponse) throws IOException {

        if (thoughtResponse.getOrder().equals("opinions")) {
            thoughtController.opinion(thoughtResponse);
        } else {
            thoughtController.change(thoughtResponse, thoughtView);
        }

    }

    public ThoughtView getThoughtView() {
        return thoughtView;
    }

    public void setThoughtView(ThoughtView thoughtView) {
        this.thoughtView = thoughtView;
    }

    public ThoughtController getThoughtController() {
        return thoughtController;
    }

    public void setThoughtController(ThoughtController thoughtController) {
        this.thoughtController = thoughtController;
    }
}
