package ir.sharif.ap2021.client.Listener;


import ir.sharif.ap2021.client.Controller.EditProfileController;
import ir.sharif.ap2021.client.View.Menu.EditProfile;
import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Response.EditProfileResponse;

import java.io.IOException;

public class EditProfileListener implements EventListener {


    EditProfileController editProfileController = new EditProfileController(this);
    private EditProfile editProfile;

    public EditProfileListener(EditProfile editProfile) throws IOException {
        this.editProfile = editProfile;
    }

    public void handle(EditProfileResponse response) throws IOException {
        editProfileController.edit(response);
    }


    @Override
    public void listen(Event event) {
        myClientController.addEvent(event);
        myClientController.addEventListener(this);
    }

    public EditProfile getEditProfile() {
        return editProfile;
    }
}






