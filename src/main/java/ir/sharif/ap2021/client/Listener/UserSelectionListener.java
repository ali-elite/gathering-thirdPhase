package ir.sharif.ap2021.client.Listener;

import ir.sharif.ap2021.client.Controller.UserSelectionController;
import ir.sharif.ap2021.client.View.Menu.Mainmenu;
import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Response.UserSelectionResponse;

import java.io.IOException;

public class UserSelectionListener implements EventListener {

    UserSelectionController userSelectionController = new UserSelectionController(this);
    Mainmenu mainMenu;

    public UserSelectionListener() throws IOException {}

    public Mainmenu getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(Mainmenu mainMenu) {
        this.mainMenu = mainMenu;
    }



    @Override
    public void listen(Event event) {

        myClientController.addEvent(event);
        myClientController.addEventListener(this);

    }


    public void handle(UserSelectionResponse userSelectionResponse) throws IOException {
        userSelectionController.set(userSelectionResponse);
    }


}
