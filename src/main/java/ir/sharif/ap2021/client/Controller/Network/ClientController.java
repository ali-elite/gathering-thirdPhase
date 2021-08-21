package ir.sharif.ap2021.client.Controller.Network;

import ir.sharif.ap2021.client.Listener.*;
import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Response.*;
import ir.sharif.ap2021.shared.Util.Loop;
import javafx.application.Platform;
import javafx.scene.control.Alert;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ClientController implements ResponseVisitor {

    private final EventSender eventSender;
    private final List<Event> events;
    private final List<EventListener> eventListeners;
    private final Loop loop;


    public ClientController(EventSender eventSender) {

        this.eventSender = eventSender;
        this.events = new LinkedList<>();
        this.eventListeners = new ArrayList<>();
        this.loop = new Loop(10, this::sendEvents);

    }

    public void start() {
        loop.start();
    }


    public void addEvent(Event event) {
        synchronized (events) {
            events.add(event);
        }
    }

    private void sendEvents() {
        List<Event> temp;
        synchronized (events) {
            temp = new ArrayList<>(events);
            events.clear();
        }
        for (Event event : temp) {
            Response[] responses = eventSender.sendEvent(event);
            for (Response response : responses) {
                response.visit(this);
            }
        }
    }

    public void addEventListener(EventListener eventListener) {
        synchronized (eventListeners) {
            eventListeners.add(eventListener);
        }
    }

    @Override
    public void exit() {

        synchronized (events) {
            events.clear();
            eventListeners.clear();
            loop.stop();
        }

        eventSender.close();
        Platform.exit();
        System.exit(0);
    }

    @Override
    public void login(boolean isEntered, String message) {

        List<EventListener> temp;
        synchronized (eventListeners) {
            temp = new ArrayList<>(eventListeners);
            eventListeners.clear();
        }

        for (EventListener eventListener : temp) {
            if (eventListener instanceof LoginListener) {
                ((LoginListener) eventListener).doLogin(isEntered, message);
                temp.remove(eventListener);
                eventListeners.addAll(temp);
                break;
            }
        }

    }

    @Override
    public void signup(boolean isEntered, String message) {
        List<EventListener> temp;
        synchronized (eventListeners) {
            temp = new ArrayList<>(eventListeners);
            eventListeners.clear();
        }

        for (EventListener eventListener : temp) {
            if (eventListener instanceof SignupListener) {
                ((SignupListener) eventListener).doSignup(isEntered, message);
                temp.remove(eventListener);
                eventListeners.addAll(temp);
                break;
            }
        }
    }

    @Override
    public void doMainMenu(MainMenuResponse mainMenuResponse) {
        List<EventListener> temp;
        synchronized (eventListeners) {
            temp = new ArrayList<>(eventListeners);
            eventListeners.clear();
        }

        for (EventListener eventListener : temp) {
            if (eventListener instanceof MainMenuListener) {
                try {
                    ((MainMenuListener) eventListener).handle(mainMenuResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                temp.remove(eventListener);
                eventListeners.addAll(temp);
                break;
            }
        }
    }


    @Override
    public void doUserSelect(UserSelectionResponse userSelectionResponse) {
        List<EventListener> temp;
        synchronized (eventListeners) {
            temp = new ArrayList<>(eventListeners);
            eventListeners.clear();
        }

        for (EventListener eventListener : temp) {
            if (eventListener instanceof UserSelectionListener) {
                try {
                    ((UserSelectionListener) eventListener).handle(userSelectionResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                temp.remove(eventListener);
                eventListeners.addAll(temp);
                break;
            }
        }

    }

    @Override
    public void doOutProfile(OutProfileResponse outProfileResponse) {

        List<EventListener> temp;
        synchronized (eventListeners) {
            temp = new ArrayList<>(eventListeners);
            eventListeners.clear();
        }

        for (EventListener eventListener : temp) {
            if (eventListener instanceof OutProfileListener) {
                try {
                    ((OutProfileListener) eventListener).handle(outProfileResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                temp.remove(eventListener);
                eventListeners.addAll(temp);
                break;
            }
        }


    }

    @Override
    public void doNotification(NotifResponse notifResponse) {

        List<EventListener> temp;
        synchronized (eventListeners) {
            temp = new ArrayList<>(eventListeners);
            eventListeners.clear();
        }

        for (EventListener eventListener : temp) {
            if (eventListener instanceof NotifListener) {
                try {
                    ((NotifListener) eventListener).handle(notifResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                temp.remove(eventListener);
                eventListeners.addAll(temp);
                break;
            }
        }

    }

    @Override
    public void editProfile(EditProfileResponse editProfileResponse) {

        List<EventListener> temp;
        synchronized (eventListeners) {
            temp = new ArrayList<>(eventListeners);
            eventListeners.clear();
        }

        for (EventListener eventListener : temp) {
            if (eventListener instanceof EditProfileListener) {
                try {
                    ((EditProfileListener) eventListener).handle(editProfileResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                temp.remove(eventListener);
                eventListeners.addAll(temp);
                break;
            }
        }
    }

    @Override
    public void shareThought(ShareThoughtResponse shareThoughtResponse) {

        List<EventListener> temp;
        synchronized (eventListeners) {
            temp = new ArrayList<>(eventListeners);
            eventListeners.clear();
        }

        for (EventListener eventListener : temp) {
            if (eventListener instanceof ShareThoughtListener) {
                try {
                    ((ShareThoughtListener) eventListener).handle(shareThoughtResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                temp.remove(eventListener);
                eventListeners.addAll(temp);
                break;
            }
        }
    }

    @Override
    public void doNewGroup(NewGroupResponse newGroupResponse) {
        List<EventListener> temp;
        synchronized (eventListeners) {
            temp = new ArrayList<>(eventListeners);
            eventListeners.clear();
        }

        for (EventListener eventListener : temp) {
            if (eventListener instanceof NewGroupListener) {
                try {
                    ((NewGroupListener) eventListener).handle(newGroupResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                temp.remove(eventListener);
                eventListeners.addAll(temp);
                break;
            }
        }

    }

    @Override
    public void doThought(ThoughtResponse thoughtResponse) {

        List<EventListener> temp;
        synchronized (eventListeners) {
            temp = new ArrayList<>(eventListeners);
            eventListeners.clear();
        }

        for (EventListener eventListener : temp) {
            if (eventListener instanceof ThoughtListener) {
                try {
                    ((ThoughtListener) eventListener).handle(thoughtResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                temp.remove(eventListener);
                eventListeners.addAll(temp);
                break;
            }
        }

    }

    @Override
    public void doMessage(MessageResponse messageResponse) {

        List<EventListener> temp;
        synchronized (eventListeners) {
            temp = new ArrayList<>(eventListeners);
            eventListeners.clear();
        }

        for (EventListener eventListener : temp) {
            if (eventListener instanceof MessageListener) {
                try {
                    ((MessageListener) eventListener).handle(messageResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                temp.remove(eventListener);
                eventListeners.addAll(temp);
                break;
            }
        }


    }

    @Override
    public void databaseFailed(DatabaseResponse databaseResponse) {

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Sorry Out Server connection to database is down. App automatically will close in 3 seconds");
            alert.showAndWait();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.exit(0);

        });
    }


    @Override
    public void doChat(ChatResponse chatResponse) {

        List<EventListener> temp;
        synchronized (eventListeners) {
            temp = new ArrayList<>(eventListeners);
            eventListeners.clear();
        }

        for (EventListener eventListener : temp) {
            if (eventListener instanceof ChatListener) {
                try {
                    ((ChatListener) eventListener).handle(chatResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                temp.remove(eventListener);
                eventListeners.addAll(temp);
                break;
            }
        }

    }

}
