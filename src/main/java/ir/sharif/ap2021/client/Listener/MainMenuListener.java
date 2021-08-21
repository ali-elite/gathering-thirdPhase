package ir.sharif.ap2021.client.Listener;


import ir.sharif.ap2021.client.Controller.MainMenuController;
import ir.sharif.ap2021.client.View.Menu.Mainmenu;
import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Event.MainMenuEvent;
import ir.sharif.ap2021.shared.Response.MainMenuResponse;

import java.io.IOException;

public class MainMenuListener implements EventListener {

    public MainMenuController mainMenuController = new MainMenuController();
    Mainmenu mainmenu;

    public MainMenuListener(Mainmenu mainmenu) throws IOException {
        this.mainmenu = mainmenu;
    }



    @Override
    public void listen(Event event) {

        myClientController.addEvent(event);
        myClientController.addEventListener(this);

    }


    public void handle(MainMenuResponse response) throws IOException {


        if (response.getOrder().equals("load")) {
            mainMenuController.load(mainmenu, response);
        }

        if (response.getOrder().equals("loadUser")) {
            mainMenuController.loadUser(mainmenu, response);
        }

        if (response.getOrder().equals("gatherThought")) {
            mainMenuController.gatherThought(mainmenu,response);
        }

        if (response.getOrder().equals("exploreThought")) {
            mainMenuController.exploreThought(mainmenu,response);
        }

        if (response.getOrder().equals("timeLineThought")) {
            mainMenuController.timeLineThought(mainmenu,response);
        }


        if (response.getOrder().equals("chats")) {
            mainMenuController.chats(mainmenu,response);
        }

        if (response.getOrder().equals("group")) {
            mainMenuController.group(mainmenu,response);
        }

        if (response.getOrder().equals("groupMessage")) {
            mainMenuController.groupMessage(response);
        }

        if (response.getOrder().equals("delete")) {
            mainMenuController.deleteUser(mainmenu);
        }

        if (response.getOrder().equals("logOut")) {
            mainMenuController.logOut(mainmenu);
        }

        if (response.getOrder().equals("forwards")) {
            mainMenuController.forwards(mainmenu,response);
        }

        if (response.getOrder().equals("unblock")) {
            mainMenuController.unblock(response);
        }

        if (response.getOrder().equals("blacklist")) {
            mainMenuController.blacklist(response);
        }

        if (response.getOrder().equals("changePassword")) {
            mainMenuController.changePassword(response);
        }

        if (response.getOrder().equals("lastSeen")) {
            mainMenuController.lastSeen(response);
        }

        if (response.getOrder().equals("changeActivity")) {
            mainMenuController.changeActivity(response);
        }

        if (response.getOrder().equals("changePrivacy")) {
            mainMenuController.changePrivacy(response);
        }


    }
}