package ir.sharif.ap2021.client.Listener;


import ir.sharif.ap2021.client.Controller.OutProfileController;
import ir.sharif.ap2021.client.View.ModelView.OutProfile;
import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Response.OutProfileResponse;


import java.io.IOException;

public class OutProfileListener implements EventListener {

    private final OutProfileController outProfileController;
    private final OutProfile outProfile;

    public OutProfileListener(OutProfile outProfile) throws IOException {
        outProfileController = new OutProfileController(this);
        this.outProfile = outProfile;
    }


    @Override
    public void listen(Event event) {

        myClientController.addEvent(event);
        myClientController.addEventListener(this);

    }

    public void handle(OutProfileResponse outProfileResponse) throws IOException {
        outProfileController.control(outProfileResponse);
    }


    public OutProfile getOutProfile() {
        return outProfile;
    }
}
