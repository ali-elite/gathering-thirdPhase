package ir.sharif.ap2021.client.Listener;


import ir.sharif.ap2021.client.Controller.ShareThoughtController;
import ir.sharif.ap2021.client.View.Menu.NewThought;
import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Response.ShareThoughtResponse;

import java.io.IOException;


public class ShareThoughtListener implements EventListener {

    private final ShareThoughtController shareThoughtController = new ShareThoughtController(this);
    private final NewThought newThought;

    public ShareThoughtListener(NewThought newThought) throws IOException {
        this.newThought = newThought;
    }

    public void handle(ShareThoughtResponse response) throws IOException {
        shareThoughtController.share(response);
    }

    @Override
    public void listen(Event event) {
        myClientController.addEvent(event);
        myClientController.addEventListener(this);
    }

    public NewThought getNewThought() {
        return newThought;
    }
}
