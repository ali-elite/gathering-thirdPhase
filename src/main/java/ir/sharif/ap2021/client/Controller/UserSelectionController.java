package ir.sharif.ap2021.client.Controller;

import ir.sharif.ap2021.client.Listener.UserSelectionListener;
import ir.sharif.ap2021.client.View.Menu.UserSelect;
import ir.sharif.ap2021.client.View.ModelView.OutProfile;
import ir.sharif.ap2021.shared.Response.UserSelectionResponse;

import java.io.IOException;

public class UserSelectionController {

    UserSelectionListener userSelectionListener;

    public UserSelectionController(UserSelectionListener userSelectionListener) throws IOException {
        this.userSelectionListener = userSelectionListener;
    }

    public void set(UserSelectionResponse response) throws IOException {

        if (response.getOrder().equals("load")) {
            if (!response.isExist()) {
                userSelectionListener.getMainMenu().userSelectionNotExist();
            } else {
                OutProfile.setUser(response.getUser());
                OutProfile.setFrom(response.getType());
                OutProfile outProfile = new OutProfile();
                outProfile.show();
            }
        }


        if (response.getOrder().equals("follower")) {

            UserSelect.setType("follower");
            UserSelect.setUsers(response.getUsers());
            UserSelect userSelect = new UserSelect();
            userSelect.show();

        }


        if (response.getOrder().equals("following")) {

            UserSelect.setType("following");
            UserSelect.setUsers(response.getUsers());
            UserSelect userSelect = new UserSelect();
            userSelect.show();

        }


        if (response.getOrder().equals("normal")) {
            StaticController.getMyMainMenu().show();
        }

    }

}
