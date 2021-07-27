package ir.sharif.ap2021.client.Listener;

import ir.sharif.ap2021.client.View.Menu.Login;
import ir.sharif.ap2021.shared.Event.Event;

import java.io.IOException;

public class LoginListener implements EventListener {

    private Login login;
    public LoginListener(Login login) {
        this.login = login;
    }

    @Override
    public void listen(Event event) {
        myClientController.addEvent(event);
        myClientController.addEventListener(this);
    }

    public void doLogin(boolean isEntered, String message){
        try {
            login.login(isEntered,message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
