package ir.sharif.ap2021.client.Listener;

import ir.sharif.ap2021.client.View.Menu.Signup;
import ir.sharif.ap2021.shared.Event.Event;

import java.io.IOException;

public class SignupListener implements EventListener {

    Signup signup;

    public SignupListener(Signup signup) {
        this.signup = signup;
    }

    @Override
    public void listen(Event event) {
        myClientController.addEvent(event);
        myClientController.addEventListener(this);
    }

    public void doSignup(boolean isEntered, String message) {
        try {
            signup.signup(isEntered,message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
