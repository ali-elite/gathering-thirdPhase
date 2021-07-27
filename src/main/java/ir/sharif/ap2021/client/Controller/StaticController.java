package ir.sharif.ap2021.client.Controller;

import ir.sharif.ap2021.client.Controller.Network.ClientController;
import ir.sharif.ap2021.client.View.Menu.Mainmenu;
import ir.sharif.ap2021.shared.Model.Thought;
import ir.sharif.ap2021.shared.Model.User;
import javafx.stage.Stage;

public class StaticController {

    private static User myUser;
    private static Stage myStage;
    private static Mainmenu myMainMenu;
    private static Thought lastThought;
    private static ClientController clientController;


    public static User getMyUser() {
        return myUser;
    }

    public static void setMyUser(User myUser) {
        StaticController.myUser = myUser;
    }

    public static Stage getMyStage() {
        return myStage;
    }

    public static void setMyStage(Stage myStage) {
        StaticController.myStage = myStage;
    }

    public static Mainmenu getMyMainMenu() {
        return myMainMenu;
    }

    public static void setMyMainMenu(Mainmenu myMainMenu) {
        StaticController.myMainMenu = myMainMenu;
    }

    public static Thought getLastThought() {
        return lastThought;
    }

    public static void setLastThought(Thought lastThought) {
        StaticController.lastThought = lastThought;
    }

    public static ClientController getClientController() {
        return clientController;
    }

    public static void setClientController(ClientController clientController) {
        StaticController.clientController = clientController;
    }
}
